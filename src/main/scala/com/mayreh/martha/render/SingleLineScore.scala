package com.mayreh.martha.render

import com.mayreh.martha.core.Metadata.VoiceHeader
import com.mayreh.martha.core.NoSound._
import com.mayreh.martha.core._
import com.mayreh.martha.render.component.NoteComponent
import com.mayreh.martha.render.element.{DoubleBarElement, RectElement}
import com.mayreh.martha.render.util.calcDenominator

class SingleLineScore(
  frame: Rect,
  layout: SingleLineScoreLayout = SingleLineScoreLayout.default) {

  import scala.collection.mutable

  private[this] val staffTop = (frame.height - layout.staffHeight) / 2
  private[this] val staffInterval = layout.staffHeight / (staffNum - 1)

  private[this] val nodeBuffer = new scala.xml.NodeBuffer

  def render(): scala.xml.Elem = {
    drawStaff()

    <svg xmlns="http://www.w3.org/2000/svg" width={ frame.width.toString } height={ frame.height.toString } viewBox={ s"${frame.x} ${frame.y} ${frame.width} ${frame.height}" }>
      { nodeBuffer.toList }
    </svg>
  }

  def loadVoice(tuneHeader: TuneHeader, voiceHeader: VoiceHeader, voice: Voice, initialOffset: Float = 0): Unit = {
    nodeBuffer.clear()

    val renderer = new SingleLineScoreRenderer(tuneHeader.unitNoteLength.denominator, layout, frame)

    val noteComponentMap = mutable.Map.empty[Float, NoteComponent]
    val tieLocations = mutable.ListBuffer.empty[Float]

    val elementsInBeam = mutable.ListBuffer.empty[(Float, BeamMember)]

    var xOffset = initialOffset
    var beamContinues = false

    // render clef
    val clef = renderer.mkClef(xOffset, voiceHeader.clef)
    nodeBuffer += clef.element
    xOffset += clef.frame.width

    // render key signature
    val keySignature = renderer.mkKeySignature(xOffset, tuneHeader.key)
    keySignature.foreach { k => nodeBuffer += k.element }
    xOffset = keySignature.map(_.frame.maxX).max

    // render meter
    val meter = renderer.mkMeter(xOffset, tuneHeader.meter)
    nodeBuffer += meter.element
    xOffset += meter.frame.width

    // render voice
    for (element <- voice.elements) {
      beamContinues = false

      element match {
        case BarLine =>
          nodeBuffer += RectElement(Rect(
            xOffset - layout.barMarginRight,
            staffTop,
            layout.stemWidth,
            layout.staffHeight)).element
        case DoubleBarLine =>
          nodeBuffer += DoubleBarElement(Rect(
            xOffset - layout.barMarginRight,
            staffTop,
            layout.stemWidth * 3,
            layout.staffHeight)).element
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
              nodeBuffer ++= component.elements.map(_.element)
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
              nodeBuffer ++= component.elements.map(_.element)
              noteComponentMap(component.x) = component
            case _ =>
              elementsInBeam += ((xOffset, chord))
              beamContinues = true
          }

          xOffset += renderer.renderedWidthForNoteLength(chord.length)

        case rest: Rest =>
          val r = renderer.mkRestComponent(xOffset, rest)
          nodeBuffer ++= r.elements.map(_.element)
          xOffset += renderer.renderedWidthForNoteLength(rest.length)
        case rest: MultiMeasureRest =>
          xOffset += renderer.renderedWidthForNoteLength(NoteLength(rest.numMeasures, 1))
        case _ =>
      }

      if (!beamContinues && elementsInBeam.nonEmpty) {
        for (beam <- renderer.mkBeamComponent(elementsInBeam.toList)) {
          nodeBuffer ++= beam.elements.map(_.element)
          for (component <- beam.toNoteComponents) { noteComponentMap(component.x) = component }
        }
        elementsInBeam.clear()
      }
    }

    if (elementsInBeam.nonEmpty) {
      for (beam <- renderer.mkBeamComponent(elementsInBeam)) {
        nodeBuffer ++= beam.elements.map(_.element)
        for (component <- beam.toNoteComponents) { noteComponentMap(component.x) = component }
      }
    }

    val noteComponentLocations = noteComponentMap.keys.toList.sorted
    for (x <- tieLocations) {
      val i = noteComponentLocations.indexOf(x)
      if (i >= 0) {
        val start = noteComponentLocations.get(i - 1).flatMap(noteComponentMap.get)
        val end = noteComponentMap.get(x)

        renderer.mkTie(start, end).foreach { nodeBuffer += _.element }
      }
    }
  }

  private def drawStaff(): Unit = {
    val width = frame.width
    val top = staffTop

    val paths = (0 until staffNum).map { i =>
      val offset = staffInterval * i

      s"M 0 ${top + offset} L ${width} ${top + offset}"
    }.mkString(" ")

    nodeBuffer += <path d={ paths } fill="none" stroke="black" stroke-width={ layout.staffLineWidth.toString } />
  }
}
