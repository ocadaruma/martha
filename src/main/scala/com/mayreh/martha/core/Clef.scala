package com.mayreh.martha.core

sealed abstract class Clef

object Clef {
  case object Treble extends Clef
  case object Bass extends Clef
}
