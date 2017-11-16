package com.mayreh.martha.tool

object Unity {

  val dpi: Int = 90
  val bu: Int = 100 // centimeter

  def pixel2unity(p: Float): Float = {
    val buPerPixel = 2.54f / dpi / bu
    buPerPixel * p
  }
}
