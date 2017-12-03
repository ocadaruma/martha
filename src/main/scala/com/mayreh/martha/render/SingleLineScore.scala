package com.mayreh.martha.render

import java.io.{File, FileOutputStream, PrintWriter}

import com.mayreh.martha.core.Metadata.VoiceHeader
import com.mayreh.martha.core.NoSound._
import com.mayreh.martha.core._
import com.mayreh.martha.render.component.NoteComponent
import com.mayreh.martha.render.element.{DoubleBarElement, RectElement, ScoreElementBase}
import com.mayreh.martha.render.util.calcDenominator

class SingleLineScore(
  frame: Rect,
  layout: SingleLineScoreLayout = SingleLineScoreLayout.default) {

  import scala.collection.mutable

  private[this] val staffTop = (frame.height - layout.staffHeight) / 2
  private[this] val staffInterval = layout.staffHeight / (staffNum - 1)

//  private[this] val nodeBuffer = new scala.xml.NodeBuffer
  private[this] val elementBuffer = scala.collection.mutable.ListBuffer.empty[ScoreElementBase]

  // TODO: remove mutability
  private[this] var _voiceStart: Float = 0
  def voiceStart: Float = _voiceStart
  def elements: Seq[ScoreElementBase] = elementBuffer.toList

  def render(): scala.xml.Elem = {
    val width = frame.width
    val top = staffTop
    val w = layout.staffLineWidth / 2

    val paths = (0 until staffNum).map { i =>
      val offset = staffInterval * i

      s"M 0 ${top + offset - w} L ${width} ${top + offset - w} L ${width} ${top + offset + w} L 0 ${top + offset + w} Z"
    }.mkString(" ")


    <svg xmlns="http://www.w3.org/2000/svg" width={ frame.width.toString } height={ frame.height.toString } viewBox={ s"${frame.x} ${frame.y} ${frame.width} ${frame.height}" }>

      <!-- draw staff -->
      <path fill="black" stroke="none" d={ paths } />

      { elementBuffer.map(_.element).toList }
    </svg>
  }

  def dumpNodes(dir: File): Unit = {
    elementBuffer.zipWithIndex.foreach { case (elem, idx) =>
      val writer = new PrintWriter(new FileOutputStream(new File(dir, f"element_${idx}%05d.svg"), false))
      val svg =
        <svg xmlns="http://www.w3.org/2000/svg"
             width={ elem.frame.width.toString }
             height={ elem.frame.height.toString }
             viewBox={ s"0 0 ${elem.frame.width} ${elem.frame.height}" }>
          { elem.withFrame(elem.frame.copy(origin = Point(0, 0))).element }
        </svg>
      try {
        writer.println(svg.mkString)
      } finally {
        writer.close()
      }
    }
  }

  def loadVoice(tuneHeader: TuneHeader, voice: Voice, initialOffset: Float = 0): Unit = {
    elementBuffer.clear()

    val renderer = new SingleLineScoreRenderer(tuneHeader.unitNoteLength.denominator, layout, frame)

    val noteComponentMap = mutable.Map.empty[Float, NoteComponent]
    val tieLocations = mutable.ListBuffer.empty[Float]

    val elementsInBeam = mutable.ListBuffer.empty[(Float, BeamMember)]

    var xOffset = initialOffset
    var beamContinues = false

    // render clef
    val clef = renderer.mkClef(xOffset, Clef.Treble)
    elementBuffer += clef
    xOffset += clef.frame.width

    // render key signature
    val keySignature = renderer.mkKeySignature(xOffset, tuneHeader.key)
    keySignature.foreach(elementBuffer += _)
    xOffset = keySignature.map(_.frame.maxX).max

    // render meter
    val meter = renderer.mkMeter(xOffset, tuneHeader.meter)
    elementBuffer += meter
    xOffset += meter.frame.width

    // TODO: remove mutability
    _voiceStart = xOffset

    // render voice
    for (element <- voice.elements) {
      beamContinues = false

      element match {
        case BarLine(_) =>
          elementBuffer += RectElement(Rect(
            xOffset - layout.barMarginRight,
            staffTop,
            layout.stemWidth,
            layout.staffHeight))
        case DoubleBarLine(_) =>
          elementBuffer += DoubleBarElement(Rect(
            xOffset - layout.barMarginRight,
            staffTop,
            layout.stemWidth * 3,
            layout.staffHeight))
        case Space =>
        case LineBreak =>
        case RepeatEnd =>
        case RepeatStart =>
        case Tie =>
          tieLocations += xOffset
        case SlurEnd =>
        case SlurStart =>
        case EOF =>

        case note: Note =>
          calcDenominator(note.length.absoluteLength(renderer.unitDenominator)) match {
            case UnitDenominator.Whole | UnitDenominator.Half | UnitDenominator.Quarter =>
              val component = renderer.mkNoteComponent(xOffset, note)
              elementBuffer ++= component.elements
              noteComponentMap(component.x) = component
            case _ =>
              elementsInBeam += ((xOffset, note))
              beamContinues = true
          }

          xOffset += renderer.renderedWidthForNoteLength(note.length)
        case chord: Chord =>
          calcDenominator(chord.length.absoluteLength(renderer.unitDenominator)) match {
            case UnitDenominator.Whole | UnitDenominator.Half | UnitDenominator.Quarter =>
              val component = renderer.mkNoteComponent(xOffset, chord)
              elementBuffer ++= component.elements
              noteComponentMap(component.x) = component
            case _ =>
              elementsInBeam += ((xOffset, chord))
              beamContinues = true
          }

          xOffset += renderer.renderedWidthForNoteLength(chord.length)
        case tuplet: Tuplet =>
          val component = renderer.mkTupletComponent(tuplet, xOffset)
          elementBuffer ++= component.elements
          component.toNoteComponents.foreach(c => noteComponentMap(c.x) = c)

          xOffset += tuplet.elements.map(e => renderer.renderedWidthForNoteLength(e.length) * tuplet.ratio).sum
        case rest: Rest =>
          val r = renderer.mkRestComponent(xOffset, rest)
          elementBuffer ++= r.elements
          xOffset += renderer.renderedWidthForNoteLength(rest.length)
        case rest: MultiMeasureRest =>
          xOffset += renderer.renderedWidthForNoteLength(NoteLength(rest.numMeasures, 1))
        case _ =>
      }

      if (!beamContinues && elementsInBeam.nonEmpty) {
        for (beam <- renderer.mkBeamComponent(elementsInBeam.toList)) {
          elementBuffer ++= beam.elements
          for (component <- beam.toNoteComponents) { noteComponentMap(component.x) = component }
        }
        elementsInBeam.clear()
      }
    }

    if (elementsInBeam.nonEmpty) {
      for (beam <- renderer.mkBeamComponent(elementsInBeam)) {
        elementBuffer ++= beam.elements
        for (component <- beam.toNoteComponents) { noteComponentMap(component.x) = component }
      }
    }

    val noteComponentLocations = noteComponentMap.keys.toList.sorted
    for (x <- tieLocations) {
      val i = noteComponentLocations.indexOf(x)
      if (i >= 0) {
        val start = noteComponentLocations.get(i - 1).flatMap(noteComponentMap.get)
        val end = noteComponentMap.get(x)

        renderer.mkTie(start, end).foreach(elementBuffer += _)
      }
    }
  }
}
