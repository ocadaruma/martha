package com.mayreh.martha.core

sealed abstract class PitchName

object PitchName {
  case object C extends PitchName
  case object D extends PitchName
  case object E extends PitchName
  case object F extends PitchName
  case object G extends PitchName
  case object A extends PitchName
  case object B extends PitchName
}
