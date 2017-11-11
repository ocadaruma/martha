package com.mayreh.martha.core

case class Pitch(
  pitchName: PitchName,
  accidental: Option[Accidental],
  octave: Octave) {

  val step: Int = octave.value * 7 + pitchName.offset
}

object Pitch {

  implicit val ordering: Ordering[Pitch] = (lhs: Pitch, rhs: Pitch) => {
    val left = lhs.octave.value * 7 + lhs.pitchName.offset + lhs.accidental.map(_.offset).getOrElse(0)
    val right = rhs.octave.value * 7 + rhs.pitchName.offset + rhs.accidental.map(_.offset).getOrElse(0)

    if (left == right) 0
    else if (left > right) 1
    else -1
  }
}
