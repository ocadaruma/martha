package com.mayreh.martha.render

/**
 * Represents 2D rectangle.
 */
case class Rect(origin: Point, size: Size) {
  val x: Float = origin.x
  val y: Float = origin.y
  val width: Float = size.width
  val height: Float = size.height

  val topRight: Point = origin.copy(x = origin.x + width)
  val bottomRight: Point = origin.copy(x = origin.x + width, y = origin.y + height)
  val topLeft: Point = origin
  val bottomLeft: Point = origin.copy(y = origin.y + height)

  val maxX: Float = this.x + this.width
  val maxY: Float = this.y + this.height

  val minX: Float = this.x
  val minY: Float = this.y

  val midX: Float = this.x + (width / 2)
  val midY: Float = this.y + (height / 2)

  def withX(x: Float): Rect = this.copy(origin = this.origin.copy(x = x))
  def withY(y: Float): Rect = this.copy(origin = this.origin.copy(y = y))

  def offsetBy(dx: Float, dy: Float): Rect = this.copy(
    origin = this.origin.copy(this.x + dx, this.y + dy))
}

object Rect {

  val zero: Rect = Rect(0, 0, 0, 0)

  def apply(x: Float, y: Float, width: Float, height: Float): Rect =
    Rect(Point(x, y), Size(width, height))
}
