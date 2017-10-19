package com.mayreh.martha.core

sealed abstract class UnitDenominator(val denominator: Int)

object UnitDenominator {
  case object Whole extends UnitDenominator(1)
  case object Half extends UnitDenominator(2)
  case object Quarter extends UnitDenominator(4)
  case object Eighth extends UnitDenominator(8)
  case object Sixteenth extends UnitDenominator(16)
  case object ThirtySecond extends UnitDenominator(32)
  case object SixtyFourth extends UnitDenominator(64)
}
