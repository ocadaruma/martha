package com.mayreh.martha.render

case class SingleLineScoreLayout(
  staffHeight: Float,
  staffLineWidth: Float,
  stemWidth: Float,
  widthPerUnitNoteLength: Float,
  barMarginRight: Float,
  minStemHeight: Float,
  maxBeamSlope: Float,
  dotMarginLeft: Float,
  fillingStaffLineWidth: Float,
  fillingStaffLineXLength: Float,
  noteHeadSize: Size,
  beamLineWidth: Float,
  tupletFontSize: Float,
  clefWidth: Float,
  meterSymbolWidth: Float,
) {
  def staffInterval: Float = staffHeight / (staffNum - 1)
}

object SingleLineScoreLayout {

  val default: SingleLineScoreLayout = SingleLineScoreLayout(
    staffHeight = 60,
    staffLineWidth = 1,
    stemWidth = 2,
    widthPerUnitNoteLength = 40,
    barMarginRight = 10,
    minStemHeight = 30,
    maxBeamSlope = 0.2f,
    dotMarginLeft = 3,
    fillingStaffLineWidth = 1,
    fillingStaffLineXLength = 28.8f,
    noteHeadSize = Size(18, 15),
    beamLineWidth = 5,
    tupletFontSize = 20,
    clefWidth = 36,
    meterSymbolWidth = 25,
  )
}
