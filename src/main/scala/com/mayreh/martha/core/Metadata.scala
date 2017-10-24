package com.mayreh.martha.core

sealed abstract class Metadata

object Metadata {
  case class Reference(number: Int) extends Metadata
  case class TuneTitle(title: String) extends Metadata
  case class Composer(name: String) extends Metadata
  case class Meter(numerator: Int, denominator: Int) extends Metadata
  case class UnitNoteLength(denominator: UnitDenominator) extends Metadata
  case class Tempo(bpm: Int, inLength: NoteLength) extends Metadata
  case class Key(keySignature: KeySignature, octave: Octave) extends Metadata
  case class VoiceHeader(id: String, clef: Clef) extends Metadata
}
