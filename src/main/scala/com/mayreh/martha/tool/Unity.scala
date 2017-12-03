package com.mayreh.martha.tool

object Unity {

  val dpi: Int = 90
  val bu: Int = 100 // centimeter
  val buPerPixel: Float = 2.54f / dpi / bu

  def pixel2unity(p: Float): Float = buPerPixel * p

  def unity2pixel(u: Float): Float = u / buPerPixel
}
