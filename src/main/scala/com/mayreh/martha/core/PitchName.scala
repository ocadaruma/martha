package com.mayreh.martha.core

sealed abstract class PitchName(val offset: Int)

object PitchName {
  case object C extends PitchName(0)
  case object D extends PitchName(1)
  case object E extends PitchName(2)
  case object F extends PitchName(3)
  case object G extends PitchName(4)
  case object A extends PitchName(5)
  case object B extends PitchName(6)
}
