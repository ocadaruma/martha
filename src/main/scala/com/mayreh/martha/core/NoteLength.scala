package com.mayreh.martha.core

case class NoteLength(numerator: Int, denominator: Int) {
  def absoluteLength(unitDenominator: UnitDenominator): Float =
    numerator.toFloat / denominator / unitDenominator.denominator
}
