package com.mayreh.martha.render

import com.mayreh.martha.core.Metadata.{Key, Meter}
import com.mayreh.martha.core._
import com.mayreh.martha.render.math.LinearExpr
import com.mayreh.martha.render.component._
import com.mayreh.martha.render.element._
import com.mayreh.martha.render.util._

import scala.collection.{mutable => mu}

/**
 * Represents whether left or right stem should be rendered.
 *   |       O
 *   |  or  |
 *  O       |
 */
sealed abstract class NoteHeadColumn {
  def invert: NoteHeadColumn = this match {
    case NoteHeadColumn.Left => NoteHeadColumn.Right
    case NoteHeadColumn.Right => NoteHeadColumn.Left
  }
}
object NoteHeadColumn {
  case object Left extends NoteHeadColumn
  case object Right extends NoteHeadColumn
}

class SingleLineScoreRenderer(
  val unitDenominator: UnitDenominator,
  val layout: SingleLineScoreLayout,
  val bounds: Rect,
) {

  private val staffTop: Float = (bounds.height - layout.staffHeight) / 2

  /**
   * -----------
   * -----------
   * -----o-----
   * -----------
   * -----------
   */
  private val BOn3rdStave: Pitch = Pitch(PitchName.B, None, Octave(0))

  /**    -o-
   * -----------
   * -----------
   * -----------
   * -----------
   * -----------
   */
  private val AAbove1stStave: Pitch = Pitch(PitchName.A, None, Octave(1))

  /**
   * -----------
   * -----------
   * -----------
   * -----------
   * -----------
   *     -o-
   */
  private val MiddleC: Pitch = Pitch(PitchName.C, None, Octave(0))

  private def noteHeadTop(pitch: Pitch): Float = {
    val noteInterval = layout.staffInterval / 2
    val c0 = staffTop + noteInterval * 9

    c0 - (7 * pitch.octave.value + pitch.pitchName.offset) * noteInterval
  }

  private def mkDotFrame(pitch: Pitch, x: Float): Rect = {
    val step = 7 * pitch.octave.value + pitch.pitchName.offset
    val noteInterval = layout.staffInterval / 2
    val y = staffTop + noteInterval * 9 - (step + (step + 1) % 2) * noteInterval
    val d = layout.staffLineWidth * 5

    Rect(x + layout.dotMarginLeft, y + noteInterval - d / 2, d, d)
  }

  private def mkDots(pitch: Pitch, x: Float, length: NoteLength, u: UnitDenominator): Seq[EllipseElement] = {
    val result = scala.collection.mutable.ListBuffer.empty[EllipseElement]
    val length1 = length.absoluteLength(unitDenominator) - u.value
    val denom1 = u.value / 2

    if (length1 >= denom1) {
      val dot1 = EllipseElement(mkDotFrame(pitch, x))
      result += dot1

      val length2 = length1 - denom1
      val denom2 = denom1 / 2
      if (length2 >= denom2) {
        result += EllipseElement(dot1.frame.withX(dot1.frame.x + dot1.frame.width + layout.staffLineWidth))
      }
    }

    result.toList
  }

  private def mkDotsForRest(x: Float, length: NoteLength, u: UnitDenominator): Seq[EllipseElement] =
    mkDots(Pitch(PitchName.C, None, Octave(1)), x, length, u)

  private def mkNoteHeadFrame(pitch: Pitch, x: Float): Rect = {
    val width = layout.noteHeadSize.width
    Rect(x, noteHeadTop(pitch), width, layout.noteHeadSize.height)
  }

  private def mkFillingStaff(x: Float, pitch: Pitch): Seq[RectElement] = {
    val step = pitch.step
    val upperBound = AAbove1stStave.step
    val lowerBound = MiddleC.step
    val frame = mkNoteHeadFrame(pitch, x)

    val staff = scala.collection.mutable.ListBuffer.empty[RectElement]

    val staveX = frame.x - (layout.fillingStaffLineXLength - frame.width) / 2
    if (step >= upperBound) {
      (1 to (1 + (step - upperBound).abs / 2)).foreach { i =>
        val y = staffTop - (i * layout.staffInterval)
        staff += RectElement(Rect(
          staveX, y, layout.fillingStaffLineXLength, layout.fillingStaffLineWidth))
      }
    } else if (step <= lowerBound) {
      (1 to (1 + (step - lowerBound).abs / 2)).foreach { i =>
        val y = staffTop + layout.staffHeight - layout.staffLineWidth + (i * layout.staffInterval)
        staff += RectElement(Rect(
          staveX, y, layout.fillingStaffLineXLength, layout.fillingStaffLineWidth))
      }
    }

    staff.toList
  }

  def mkClef(x: Float, clef: Clef): ScoreElementBase = {
    clef match {
      case Clef.Treble =>
        val height = layout.staffHeight * 1.6f
        val y = (staffTop + layout.staffInterval * 3) - TrebleClefElement.yRatioAtG * height
        TrebleClefElement(Rect(x, y, layout.clefWidth, height))
      case Clef.Bass =>
        throw new RuntimeException(s"currently not supported: ${clef}")
    }
  }

  def mkMeter(x: Float, meter: Meter): ScoreElementBase = {
    SymbolCElement(Rect(
      x + (layout.unitScale * 6), // margin
      staffTop + layout.staffInterval,
      layout.meterSymbolWidth,
      layout.staffInterval * 2
    ))
  }

  def mkKeySignature(x: Float, key: Key): Seq[ScoreElementBase] = {
    val flats = Seq(
      Pitch(PitchName.B, Some(Accidental.Flat), Octave(0)),
      Pitch(PitchName.E, Some(Accidental.Flat), Octave(1)),
      Pitch(PitchName.A, Some(Accidental.Flat), Octave(0)),
      Pitch(PitchName.D, Some(Accidental.Flat), Octave(1)),
      Pitch(PitchName.G, Some(Accidental.Flat), Octave(0)),
      Pitch(PitchName.C, Some(Accidental.Flat), Octave(1)),
      Pitch(PitchName.F, Some(Accidental.Flat), Octave(0))
    )

    val sharps = Seq(
      Pitch(PitchName.F, Some(Accidental.Sharp), Octave(1)),
      Pitch(PitchName.C, Some(Accidental.Sharp), Octave(1)),
      Pitch(PitchName.G, Some(Accidental.Sharp), Octave(1)),
      Pitch(PitchName.D, Some(Accidental.Sharp), Octave(1)),
      Pitch(PitchName.A, Some(Accidental.Sharp), Octave(0)),
      Pitch(PitchName.E, Some(Accidental.Sharp), Octave(1)),
      Pitch(PitchName.B, Some(Accidental.Sharp), Octave(0)),
    )

    val num = key.keySignature match {
      case KeySignature.Flat1 | KeySignature.Sharp1 => 1
      case KeySignature.Flat2 | KeySignature.Sharp2 => 1
      case KeySignature.Flat3 | KeySignature.Sharp3 => 1
      case KeySignature.Flat4 | KeySignature.Sharp4 => 1
      case KeySignature.Flat5 | KeySignature.Sharp5 => 1
      case KeySignature.Flat6 | KeySignature.Sharp6 => 1
      case KeySignature.Flat7 | KeySignature.Sharp7 => 1
      case _ => 0
    }

    val pitches = key.keySignature match {
      case _: SharpFamily => sharps.take(num)
      case _ => flats.take(num)
    }

    val result = mu.ListBuffer.empty[ScoreElementBase]
    var localX = x + layout.staffInterval + (layout.unitScale * 4) // margin
    for (p <- pitches) {
      result += mkAccidental(p, localX).get
      localX += layout.staffInterval
    }

    result.toList
  }

  def mkTie(start: Option[NoteComponent], end: Option[NoteComponent]): Option[SlurElement] = {
    (start, end) match {
      case (Some(s), Some(e)) =>
        val noteHeadFramesAtStart = s.pitches.map(_.noteHead.frame)
        val noteHeadFramesAtEnd = e.pitches.map(_.noteHead.frame)

        val inverted = s.inverted

        val startX = noteHeadFramesAtStart.map(_.maxX).max - layout.noteHeadSize.width / 2
        val startY = if (inverted) noteHeadFramesAtStart.map(_.minY).min else noteHeadFramesAtStart.map(_.maxY).max
        val endX = noteHeadFramesAtEnd.map(_.minX).min + layout.noteHeadSize.width / 2
        val endY = if (inverted) noteHeadFramesAtEnd.map(_.minY).min else noteHeadFramesAtEnd.map(_.maxY).max

        Some(
          SlurElement(
            Rect(
              noteHeadFramesAtStart.map(_.minX).min,
              0,
              noteHeadFramesAtEnd.map(_.maxX).max - noteHeadFramesAtStart.map(_.minX).min,
              bounds.height),
            LocalPoint(Point(startX, startY)),
            LocalPoint(Point(endX, endY)),
            inverted))
      case _ =>
        None
    }
  }

  private def mkAccidental(pitch: Pitch, x: Float): Option[ScoreElementBase] = {
    val noteHeadFrame = mkNoteHeadFrame(pitch, x)

    pitch.accidental match {
      case Some(Accidental.DoubleFlat) =>
        Some(DoubleFlatElement(
          Rect(
            noteHeadFrame.x - layout.staffInterval * 1.5f,
            noteHeadFrame.y - layout.staffInterval * 1.2f,
            layout.staffInterval * 1.2f,
            layout.staffInterval * 1.2f + noteHeadFrame.height)))
      case Some(Accidental.Flat) =>
        Some(FlatElement(
          Rect(
            noteHeadFrame.x - layout.staffInterval,
            noteHeadFrame.y - layout.staffInterval * 1.2f,
            layout.staffInterval * 0.8f,
            layout.staffInterval * 1.2f + noteHeadFrame.height)))
      case Some(Accidental.Natural) =>
        Some(NaturalElement(
          Rect(
            noteHeadFrame.x - layout.staffInterval,
            noteHeadFrame.y - layout.staffInterval * 0.5f,
            layout.staffInterval * 0.6f,
            layout.staffInterval * 2)))
      case Some(Accidental.Sharp) =>
        Some(SharpElement(
          Rect(
            noteHeadFrame.x - layout.staffInterval,
            noteHeadFrame.y - layout.staffInterval * 0.5f,
            layout.staffInterval * 0.8f,
            layout.staffInterval * 2)))
      case Some(Accidental.DoubleSharp) =>
        Some(DoubleSharpElement(
          Rect(
            noteHeadFrame.x - layout.staffInterval * 1.2f,
            noteHeadFrame.y,
            layout.staffInterval,
            layout.staffInterval)))
      case _ =>
        None
    }
  }

  private def mkTail(noteLength: NoteLength, stemFrame: Rect, inverted: Boolean): ScoreElementBase = {
    val tailFrame = if (inverted) {
      Rect(
        stemFrame.x,
        stemFrame.maxY - layout.staffInterval * 3,
        layout.noteHeadSize.width,
        layout.staffInterval * 3)
    } else {
      Rect(
        stemFrame.maxX,
        stemFrame.y,
        layout.noteHeadSize.width,
        layout.staffInterval * 3)
    }

    calcDenominator(noteLength.absoluteLength(unitDenominator)) match {
      case UnitDenominator.Eighth =>
        TailEighthElement(tailFrame, inverted)
      case UnitDenominator.Sixteenth =>
        TailSixteenthElement(tailFrame, inverted)
      case _ =>
        throw new RuntimeException("currently not supported")
    }
  }

  private def mkComponentsInTuplet(x: Float, tuplet: Tuplet): Seq[TupletComponentMemberWithX] = {
    val ratio = tuplet.time.toFloat / tuplet.notes
    val components = mu.ListBuffer.empty[TupletComponentMemberWithX]

    var offset = x
    for (e <- tuplet.elements) {
      e match {
        case note: Note =>
          components += TupletComponentMemberWithX(offset, TupletComponentMember.Note(mkNoteComponent(offset, note)))
          offset += renderedWidthForNoteLength(note.length) * ratio
        case chord: Chord =>
          components += TupletComponentMemberWithX(offset, TupletComponentMember.Note(mkNoteComponent(offset, chord)))
          offset += renderedWidthForNoteLength(chord.length) * ratio
        case rest: Rest =>
          components += TupletComponentMemberWithX(offset, TupletComponentMember.Rest(mkRestComponent(offset, rest)))
          offset += renderedWidthForNoteLength(rest.length) * ratio
      }
    }

    components.toList
  }

  private def shouldInvert(chord: Chord): Boolean = chord.pitches.map(_.step).sum / chord.pitches.size >= BOn3rdStave.step

  def mkNoteComponent(x: Float, chord: Chord): NoteComponent =
    mkNoteComponent(x, chord, overrideInverted = None, autoStem = true, overrideStem = None, overrideTail = None)

  def mkNoteComponent(x: Float, note: Note): NoteComponent =
    mkNoteComponent(x, note, overrideInverted = None, autoStem = true, overrideStem = None, overrideTail = None)

  private def mkNoteComponent(
    x: Float,
    note: Note,
    overrideInverted: Option[Boolean],
    autoStem: Boolean,
    overrideStem: Option[RectElement],
    overrideTail: Option[ScoreElementBase]): NoteComponent = {

    mkNoteComponent(x, Chord(note.length, Seq(note.pitch)), overrideInverted, autoStem, overrideStem, overrideTail)
  }

  private def mkStem(noteHeads: Seq[ScoreElementBase], inverted: Boolean, sparse: Boolean): RectElement = {
    val noteHeadFrames = noteHeads.map(_.frame)
    val bottomFrame = noteHeadFrames.maxBy(_.y)
    val topFrame = noteHeadFrames.minBy(_.y)
    val upperBound = staffTop - layout.staffInterval
    val lowerBound = staffTop + layout.staffHeight + layout.staffInterval

    val stemHeight = layout.staffInterval * 3
    val x = if (sparse && inverted) topFrame.x else noteHeadFrames.map(_.maxX).min

    if (inverted) {
      val stemBottom = Seq(upperBound + stemHeight, bottomFrame.maxY + stemHeight).max
      val y = topFrame.y + topFrame.height * 0.6f
      RectElement(Rect(x, y, layout.stemWidth, stemBottom - y))
    } else {
      val y = Seq(lowerBound - stemHeight, topFrame.y - stemHeight).min
      RectElement(Rect(x - layout.stemWidth, y, layout.stemWidth, topFrame.y - y + bottomFrame.height * 0.4f))
    }
  }

  def mkRestComponent(x: Float, rest: Rest): RestComponent = {

    val length = rest.length.absoluteLength(unitDenominator)

    var restElement: ScoreElementBase = null
    var dots: Seq[EllipseElement] = Nil

    calcDenominator(length) match {
      case UnitDenominator.Whole =>
        val width = layout.staffInterval * 1.5f
        restElement = RectElement(Rect(
          x + renderedWidthForNoteLength(rest.length) / 2 - (width / 2),
          staffTop + layout.staffInterval,
          width,
          layout.staffInterval * 0.6f))
      case UnitDenominator.Half =>
        val width = layout.staffInterval * 1.5f
        val height = layout.staffInterval * 0.6f
        restElement = RectElement(Rect(
          x + renderedWidthForNoteLength(rest.length) / 2 - (width / 2),
          staffTop + layout.staffInterval * 2 - height,
          width,
          height))
        dots ++= mkDotsForRest(restElement.frame.maxX, rest.length, UnitDenominator.Half)
      case UnitDenominator.Quarter =>
        restElement = QuarterRestElement(Rect(
          x,
          staffTop + layout.staffInterval / 2,
          layout.staffInterval * 1.1f,
          layout.staffInterval * 2.5f))
        dots ++= mkDotsForRest(restElement.frame.maxX, rest.length, UnitDenominator.Quarter)
      case UnitDenominator.Eighth =>
        restElement = EighthRestElement(Rect(
          x,
          staffTop + layout.staffInterval + layout.staffLineWidth,
          layout.staffInterval * 1.3f,
          layout.staffInterval * 2))
        dots ++= mkDotsForRest(restElement.frame.maxX, rest.length, UnitDenominator.Eighth)
      case UnitDenominator.Sixteenth =>
        restElement = SixteenthRestElement(Rect(
          x,
          staffTop + layout.staffInterval + layout.staffLineWidth,
          layout.staffInterval * 1.3f,
          layout.staffInterval * 3))
        dots ++= mkDotsForRest(restElement.frame.maxX, rest.length, UnitDenominator.Sixteenth)
      case _ =>
        throw new RuntimeException("currently not supported")
    }

    RestComponent(dots, restElement, x)
  }

  private def mkNoteComponent(
    x: Float,
    chord: Chord,
    overrideInverted: Option[Boolean],
    autoStem: Boolean,
    overrideStem: Option[RectElement],
    overrideTail: Option[ScoreElementBase]): NoteComponent = {

    val inverted = overrideInverted.getOrElse(shouldInvert(chord))
    val sortedPitches = if (inverted) chord.pitches.sortBy(_.step).reverse else chord.pitches.sortBy(_.step)
    val sparse = sortedPitches.sparse

    val noteHeadFrames = mu.ListBuffer.empty[(Pitch, Rect, NoteHeadColumn)]
    var previousPitch: Option[Pitch] = None

    if (sparse) {
      for (pitch <- sortedPitches) {
        noteHeadFrames += ((pitch, mkNoteHeadFrame(pitch, x), NoteHeadColumn.Left))
      }
    } else {
      var column: NoteHeadColumn = if (inverted) NoteHeadColumn.Right else NoteHeadColumn.Left

      for (pitch <- sortedPitches) {
        previousPitch.foreach { p =>
          if ((pitch.step - p.step).abs < 2) { column = column.invert }
        }

        noteHeadFrames += ((
          pitch,
          mkNoteHeadFrame(pitch, x).offsetBy(if (column == NoteHeadColumn.Right) layout.noteHeadSize.width else 0, 0),
          column))

        previousPitch = Some(pitch)
      }
    }

    val dots = mu.ListBuffer.empty[EllipseElement]
    val noteHeads = mu.ListBuffer.empty[PitchWithElement]
    val accidentals = mu.ListBuffer.empty[ScoreElementBase]
    val fillingStaff = mu.ListBuffer.empty[ScoreElementBase]

    val length = chord.length.absoluteLength(unitDenominator)
    val denominator = calcDenominator(length)

    var stem: Option[RectElement] = None
    var tail: Option[ScoreElementBase] = None

    for ((pitch, frame, column) <- noteHeadFrames) {
      val dotsAddable = denominator != UnitDenominator.Whole && (sparse || column == NoteHeadColumn.Right)

      noteHeads += PitchWithElement(pitch, denominator match {
        case UnitDenominator.Whole => WholeNoteElement(frame)
        case UnitDenominator.Half => WhiteNoteElement(frame)
        case _ => BlackNoteElement(frame)
      })

      dots ++= (if (dotsAddable) mkDots(pitch, frame.maxX, chord.length, denominator) else Nil)
      fillingStaff ++= mkFillingStaff(frame.minX, pitch)
      accidentals ++= mkAccidental(pitch, x)
    }

    if (autoStem) {
      if (denominator.renderStem) {
        val s = mkStem(noteHeads.map(_.noteHead).toList, inverted, sparse)
        stem = Some(s)

        if (denominator.renderTail) {
          tail = Some(mkTail(chord.length, s.frame, inverted))
        }
      }
    } else {
      stem = overrideStem
      tail = overrideTail
    }

    NoteComponent(dots, noteHeads, accidentals, fillingStaff, stem, tail, inverted, x)
  }

  def renderedWidthForNoteLength(noteLength: NoteLength): Float = {
    layout.widthPerUnitNoteLength * noteLength.numerator / noteLength.denominator
  }

  def mkBracketElement(numNotes: Int, envelope: Seq[Point], point: Point, inverted: Boolean): BracketElement = {
    val firstPoint = envelope.head
    val lastPoint = envelope.last

    val a = (lastPoint.y - firstPoint.y) / (lastPoint.x - firstPoint.x)
    val slope = if (a.abs < layout.maxBeamSlope) { a } else { layout.maxBeamSlope * a.signum }
    val f = LinearExpr(slope, point)

    val (x1, y1) = (firstPoint.x, f(firstPoint.x))
    val (x2, y2) = (lastPoint.x, f(lastPoint.x))

    val height = Seq(y2 - y1, layout.tupletFontSize).max
    BracketElement(Rect(
      x1,
      if (inverted) y1 else y1 - height,
      x2 - x1,
      height
    ), inverted = inverted, notes = numNotes, fontSize = layout.tupletFontSize, rightDown = y2 > y1)
  }

  def mkBeamComponent(elementsInBeam: Seq[(Float, BeamMember)], groupedBy: Int = 4): Seq[BeamComponent] = {
    val result = mu.ListBuffer.empty[BeamComponent]

    for (pairs <- elementsInBeam.grouped(groupedBy)) {
      for (group <- pairs.spanBy { case (_, element) => element.length }) {
        if (group.size == 1) {
          val (x, element) = group.head

          val noteComponent = element match {
            case note: Note =>
              Some(mkNoteComponent(x, note))
            case chord: Chord =>
              Some(mkNoteComponent(x, chord))
            case _ =>
              None
          }

          noteComponent.foreach { component =>
            result += BeamComponent(Seq(component), Nil, component.inverted)
          }
        } else {
          val (offsetAtHighest, highestElement) = group.maxBy { case (_, element) => element.maxPitch.step }
          val (offsetAtLowest, lowestElement) = group.minBy { case (_, element) => element.minPitch.step }

          val highestFrame = mkNoteHeadFrame(highestElement.maxPitch, offsetAtHighest)
          val lowestFrame = mkNoteHeadFrame(lowestElement.maxPitch, offsetAtLowest)

          val ((firstX, first), (lastX, last)) = (group.head, group.last)
          val lowestFrameInFirst = mkNoteHeadFrame(first.minPitch, firstX)
          val highestFrameInFirst = mkNoteHeadFrame(first.maxPitch, firstX)
          val lowestFrameInLast = mkNoteHeadFrame(last.minPitch, lastX)
          val highestFrameInLast = mkNoteHeadFrame(last.maxPitch, lastX)

          val upperA = (highestFrameInLast.y - highestFrameInFirst.y) / (highestFrameInLast.x - highestFrameInFirst.x)
          val lowerA = (lowestFrameInLast.y - lowestFrameInFirst.y) / (lowestFrameInLast.x - lowestFrameInFirst.x)

          val upperSlope = if (upperA.abs < layout.maxBeamSlope) { upperA } else { layout.maxBeamSlope * upperA.signum }
          val lowerSlope = if (lowerA.abs < layout.maxBeamSlope) { lowerA } else { layout.maxBeamSlope * lowerA.signum }

          val upperF = LinearExpr(upperSlope,
            Point(highestFrame.maxX, Seq(highestFrame.y - layout.minStemHeight, staffTop + layout.staffHeight - layout.minStemHeight).min))
          val lowerF = LinearExpr(lowerSlope,
            Point(
              if (lowestElement.sortedPitches.sparse) lowestFrame.x else lowestFrame.maxX,
              Seq(lowestFrame.maxY + layout.minStemHeight, staffTop - layout.staffInterval + layout.minStemHeight).max
            ))

          val staffYCenter = staffTop + layout.staffInterval * 2
          val upperDiff = group.map { case (x, _) => (staffYCenter - upperF(x + layout.noteHeadSize.width)).abs }.sum
          val lowerDiff = group.map { case (x, element) => (staffYCenter - lowerF(if (element.sortedPitches.sparse) x else { x + layout.noteHeadSize.width })).abs }.sum

          var beam = BeamElement(Rect.zero, layout.beamLineWidth)

          if (upperDiff < lowerDiff) {
            val inverted = false

            val noteComponents = mu.ListBuffer.empty[NoteComponent]
            val beams = mu.ListBuffer.empty[BeamElement]

            for ((xOffset, element) <- group) {
              val x = xOffset + layout.noteHeadSize.width - layout.stemWidth
              val y = upperF(x)

              element match {
                case note: Note =>
                  val stem = RectElement(Rect(
                    x, y, layout.stemWidth, noteHeadTop(note.pitch) - y + layout.noteHeadSize.height * 0.4f
                  ))
                  noteComponents += mkNoteComponent(xOffset, note, Some(inverted), autoStem = false, Some(stem), None)
                case chord: Chord =>
                  val stem = RectElement(Rect(
                    x, y, layout.stemWidth, noteHeadTop(chord.minPitch) - y + layout.noteHeadSize.height * 0.4f
                  ))
                  noteComponents += mkNoteComponent(xOffset, chord, Some(inverted), autoStem = false, Some(stem), None)
                case _ =>
              }
            }

            val x1 = firstX + layout.noteHeadSize.width - layout.stemWidth
            val y1 = upperF(x1)
            val x2 = lastX + layout.noteHeadSize.width
            val y2 = upperF(x2)

            beam = beam.copy(rightDown = y1 < y2, frame = Rect(x1, Seq(y1, y2).min, x2 - x1, Seq((y1 - y2).abs, layout.beamLineWidth).max))
            beams += beam

            if (calcDenominator(first.length.absoluteLength(unitDenominator)) == UnitDenominator.Sixteenth) {
              val beam2 = BeamElement(
                frame = beam.frame.withY(beam.frame.y + beam.lineWidth * 1.5f),
                lineWidth = beam.lineWidth,
                rightDown = beam.rightDown)
              beams += beam2
            }
            result += BeamComponent(noteComponents, beams, inverted)
          } else {
            val inverted = true

            val noteComponents = mu.ListBuffer.empty[NoteComponent]
            val beams = mu.ListBuffer.empty[BeamElement]

            for ((xOffset, element) <- group) {
              val x = element match {
                case c: Chord if c.pitches.sparse => xOffset
                case c if !c.isInstanceOf[Chord] => xOffset
                case _ => xOffset + layout.noteHeadSize.width
              }
              val bottomY = lowerF(x)

              element match {
                case note: Note =>
                  val top = noteHeadTop(note.pitch)
                  val y = top + layout.noteHeadSize.height * 0.6f
                  val stem = RectElement(Rect(
                    x, y, layout.stemWidth, bottomY - y))
                  noteComponents += mkNoteComponent(xOffset, note, Some(inverted), autoStem = false, Some(stem), None)
                case chord: Chord =>
                  val top = noteHeadTop(chord.maxPitch)
                  val y = top + layout.noteHeadSize.height * 0.6f
                  val stem = RectElement(Rect(
                    x, y, layout.stemWidth, bottomY - y))
                  noteComponents += mkNoteComponent(xOffset, chord, Some(inverted), autoStem = false, Some(stem), None)
                case _ =>
              }
            }

            val x1 = firstX + (if (first.sortedPitches.sparse) 0 else layout.noteHeadSize.width)
            val y1 = lowerF(x1)
            val x2 = lastX + (if (last.sortedPitches.sparse) 0 else layout.noteHeadSize.width) + layout.stemWidth
            val y2 = lowerF(x2)

            beam = beam.copy(rightDown = y1 < y2, frame = Rect(x1, Seq(y1, y2).min, x2 - x1, Seq((y1 - y2).abs, layout.beamLineWidth).max))
            beams += beam

            if (calcDenominator(first.length.absoluteLength(unitDenominator)) == UnitDenominator.Sixteenth) {
              val beam2 = BeamElement(
                frame = beam.frame.withY(beam.frame.y - beam.lineWidth * 1.5f),
                lineWidth = beam.lineWidth,
                rightDown = beam.rightDown)
              beams += beam2
            }

            result += BeamComponent(noteComponents, beams, inverted)
          }
        }
      }
    }

    result.toList
  }

  def mkTupletComponent(tuplet: Tuplet, x: Float): TupletComponent = {

    val length = tuplet.elements.head.length.absoluteLength(unitDenominator)
    val denominator = calcDenominator(length)

    val components = mu.ListBuffer.empty[TupletComponentMember]
    var bracket: BracketElement = null
    var inverted: Boolean = false

    denominator match {
      case UnitDenominator.Whole =>
        throw new RuntimeException("currently not supported")
      case UnitDenominator.Half | UnitDenominator.Quarter =>
        val componentsWithX = mkComponentsInTuplet(x, tuplet)
        components ++= componentsWithX.map(_.member)

        val (invertedCount, notInvertedCount) = components.collect { case TupletComponentMember.Note(n) => n }.partition(_.inverted)
        inverted = invertedCount.size > notInvertedCount.size

        if (inverted) {
          val upperBound = staffTop + layout.staffHeight
          val envelope = componentsWithX.map { case TupletComponentMemberWithX(_, member) =>
            member match {
              case TupletComponentMember.Note(n) =>
                val frames = n.pitches.map(_.noteHead.frame) ++ n.stem.map(_.frame)
                Point(n.stem.get.frame.x, Seq(frames.map(_.maxY).max, upperBound).max)
              case TupletComponentMember.Rest(r) =>
                Point(r.restElement.frame.midX, upperBound)
            }
          }

          val pointAtLowest = envelope.maxBy(_.y)
          bracket = mkBracketElement(tuplet.notes, envelope, pointAtLowest, inverted)
        } else {
          val lowerBound = staffTop
          val envelope = componentsWithX.map { case TupletComponentMemberWithX(_, member) =>
            member match {
              case TupletComponentMember.Note(n) =>
                val frames = n.pitches.map(_.noteHead.frame) ++ n.stem.map(_.frame)
                Point(n.stem.get.frame.x, Seq(frames.map(_.minY).min, lowerBound).min)
              case TupletComponentMember.Rest(r) =>
                Point(r.restElement.frame.midX, lowerBound)
            }
          }

          val pointAtHighest = envelope.minBy(_.y)
          bracket = mkBracketElement(tuplet.notes, envelope, pointAtHighest, inverted)
        }
      case _ =>
        val ratio = tuplet.ratio
        var beamComponents = mu.ListBuffer.empty[(Float, Either[BeamComponent, RestComponent])]
        val elements = mu.ListBuffer.empty[(Float, BeamMember)]

        var offset = x

        for (e <- tuplet.elements) {
          var beamContinues = false

          e match {
            case note: Note =>
              elements += ((offset, note))
              beamContinues = true
            case chord: Chord =>
              elements += ((offset, chord))
              beamContinues = true
            case rest: Rest =>
              beamComponents += ((offset, Right(mkRestComponent(offset, rest))))
              beamContinues = false
            case _ =>
          }

          offset = renderedWidthForNoteLength(e.length) * ratio
          if (!beamContinues) {
            for (b <- mkBeamComponent(elements, tuplet.notes)) {
              beamComponents += ((elements.head._1, Left(b)))
            }
            elements.clear()
          }
        }

        if (elements.nonEmpty) {
          for (b <- mkBeamComponent(elements, tuplet.notes)) {
            beamComponents += ((elements.head._1, Left(b)))
          }
        }
        beamComponents = beamComponents.sortBy(_._1)

        for ((_, component) <- beamComponents) {
          component match {
            case Left(b) => components += TupletComponentMember.Beam(b)
            case Right(r) => components += TupletComponentMember.Rest(r)
          }
        }

        val (numInverted, numNotInverted) = beamComponents.collect { case (_, Left(n)) => n }.partition(_.inverted)
        inverted = numInverted.size > numNotInverted.size

        if (inverted) {
          val upperBound = staffTop + layout.staffHeight
          val envelope = beamComponents.flatMap { case (_, c) =>
            c match {
              case Left(b) =>
                b.noteComponents.map { nc =>
                  val frames = nc.pitches.map(_.noteHead.frame) ++ nc.stem.map(_.frame)
                  Point(nc.stem.get.frame.x, Seq(frames.map(_.maxY).max, upperBound).max)
                }
              case Right(r) =>
                Seq(Point(r.restElement.frame.midX, upperBound))
            }
          }

          val pointAtLowest = envelope.maxBy(_.y)
          bracket = mkBracketElement(tuplet.notes, envelope, pointAtLowest, inverted)
        } else {
          val lowerBound = staffTop
          val envelope = beamComponents.flatMap { case (_, c) =>
            c match {
              case Left(b) =>
                b.noteComponents.map { nc =>
                  val frames = nc.pitches.map(_.noteHead.frame) ++ nc.stem.map(_.frame)
                  Point(nc.stem.get.frame.x, Seq(frames.map(_.minY).min, lowerBound).min)
                }
              case Right(r) =>
                Seq(Point(r.restElement.frame.midX, lowerBound))
            }
          }

          val pointAtHighest = envelope.minBy(_.y)
          bracket = mkBracketElement(tuplet.notes, envelope, pointAtHighest, inverted)
        }
    }

    TupletComponent(components.toList, bracket, inverted)
  }
}
