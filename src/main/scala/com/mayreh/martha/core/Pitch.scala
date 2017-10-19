package com.mayreh.martha.core

case class Pitch(
  pitchName: PitchName,
  accidental: Option[Accidental],
  octave: Octave)
