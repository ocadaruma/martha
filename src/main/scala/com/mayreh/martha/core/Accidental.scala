package com.mayreh.martha.core

sealed abstract class Accidental(val offset: Int)

object Accidental {
  case object DoubleFlat extends Accidental(-2)
  case object Flat extends Accidental(-1)
  case object Natural extends Accidental(0)
  case object Sharp extends Accidental(1)
  case object DoubleSharp extends Accidental(2)
}
