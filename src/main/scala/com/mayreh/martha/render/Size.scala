package com.mayreh.martha.render

case class Size(width: Float, height: Float) {
  def *(scale: Float): Size = Size(width = width * scale, height = height * scale)
}
