package com.mayreh.martha.render

case class Rect(origin: Point, size: Size) {
  val x: Float = origin.x
  val y: Float = origin.y
  val width: Float = size.width
  val height: Float = size.height

  val topRight: Point = origin.copy(x = origin.x + width)
  val bottomRight: Point = origin.copy(x = origin.x + width, y = origin.y + height)
  val topLeft: Point = origin
  val bottomLeft: Point = origin.copy(y = origin.y + height)
}

object Rect {

  def apply(x: Float, y: Float, width: Float, height: Float): Rect =
    Rect(Point(x, y), Size(width, height))
}
