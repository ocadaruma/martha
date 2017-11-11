package com.mayreh.martha.render

import com.mayreh.martha.core.UnitDenominator

package object util {
  def calcDenominator(absoluteLength: Float): UnitDenominator = absoluteLength match {
    case l if l >= 1 => UnitDenominator.Whole
    case l if l >= 0.5 => UnitDenominator.Half
    case l if l >= 0.25 => UnitDenominator.Quarter
    case l if l >= 0.125 => UnitDenominator.Eighth
    case l if l >= 0.0625 => UnitDenominator.Sixteenth
    case l if l >= 0.03125 => UnitDenominator.ThirtySecond
    case _ => UnitDenominator.SixtyFourth
  }
}
