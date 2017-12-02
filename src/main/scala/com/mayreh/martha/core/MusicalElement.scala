package com.mayreh.martha.core

sealed trait MusicalElement
sealed trait HasLength { def length: NoteLength }
sealed trait TupletMember extends MusicalElement with HasLength
sealed trait BeamMember extends MusicalElement with HasLength

object NoSound {
  case class BarLine(annotations: Seq[Annotation]) extends MusicalElement
  case class DoubleBarLine(annotations: Seq[Annotation]) extends MusicalElement
  case object SlurStart extends MusicalElement
  case object SlurEnd extends MusicalElement
  case object RepeatStart extends MusicalElement
  case object RepeatEnd extends MusicalElement
  case object Tie extends MusicalElement
  case object Space extends MusicalElement
  case object LineBreak extends MusicalElement
  case object EOF extends MusicalElement
}

case class Note(length: NoteLength, pitch: Pitch, annotations: Seq[Annotation]) extends TupletMember with BeamMember

case class Rest(length: NoteLength, annotations: Seq[Annotation]) extends TupletMember with BeamMember

case class MultiMeasureRest(numMeasures: Int) extends MusicalElement

case class Chord(length: NoteLength, pitches: Seq[Pitch], annotations: Seq[Annotation]) extends TupletMember with BeamMember

case class VoiceId(id: String) extends MusicalElement

case class Tuplet(notes: Int, inTimeOf: Option[Int], elements: Seq[TupletMember]) extends MusicalElement {
  val time: Int = inTimeOf.getOrElse {
    notes match {
      case 2 | 4 | 8 => 3
      case 3 | 6 => 2
      case _ => 3
    }
  }

  val ratio: Float = time.toFloat / notes
}
