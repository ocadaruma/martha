package com.mayreh.martha.core

sealed abstract class TuneHeader

case class Reference(number: Int) extends TuneHeader

case class TuneTitle(title: String) extends TuneHeader

case class Composer(name: String) extends TuneHeader

case class Meter(numerator: Int, denominator: Int) extends TuneHeader

case class UnitNoteLength(denominator: UnitDenominator) extends TuneHeader

case class Tempo(bpm: Int, inLength: NoteLength) extends TuneHeader

case class Key(keySignature: KeySignature, octave: Octave) extends TuneHeader

case class VoiceHeader(id: String, clef: Clef) extends TuneHeader
