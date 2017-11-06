package com.mayreh.martha.core

case class Pitch(
  pitchName: PitchName,
  accidental: Option[Accidental],
  octave: Octave) {

  val step: Int = octave.value * 7 + pitchName.offset
}
