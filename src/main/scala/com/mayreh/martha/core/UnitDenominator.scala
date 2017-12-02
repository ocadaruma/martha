package com.mayreh.martha.core

sealed abstract class UnitDenominator(val denominator: Int) {

  def value: Float = 1f / denominator
}

object UnitDenominator {
  case object Whole extends UnitDenominator(1)
  case object Half extends UnitDenominator(2)
  case object Quarter extends UnitDenominator(4)
  case object Eighth extends UnitDenominator(8)
  case object Sixteenth extends UnitDenominator(16)
  case object ThirtySecond extends UnitDenominator(32)
  case object SixtyFourth extends UnitDenominator(64)

  val values = Seq(Whole, Half, Quarter, Eighth, Sixteenth, ThirtySecond, SixtyFourth)

  def valueOf(denominator: Int): Option[UnitDenominator] = values.find(_.denominator == denominator)
}
