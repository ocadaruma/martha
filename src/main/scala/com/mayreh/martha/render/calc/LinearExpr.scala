package com.mayreh.martha.render.calc

import com.mayreh.martha.render.{Point, Rect}

/**
 * Represents linear expression: ax + by + c = 0
 */
case class LinearExpr(a: Float, b: Float, c: Float) {

  val grad: Float = -a / b
  val intercept: Float = -c / b

  def intersectionPoint(other: LinearExpr): Option[Point] = {
    if (this.b * other.a - this.a * other.b == 0) {
      None
    } else {
      Some(Point(
        (this.c * other.b - this.b * other.c) / (this.b * other.a - this.a * other.b),
        (this.a * other.c - this.c * other.a) / (this.b * other.a - this.a * other.b)
      ))
    }
  }

  def intersects(rect: Rect): Boolean = {

    val left = LinearExpr(rect.topLeft, rect.bottomLeft)
    val top = LinearExpr(rect.topLeft, rect.topRight)
    val right = LinearExpr(rect.topRight, rect.bottomRight)
    val bottom = LinearExpr(rect.bottomLeft, rect.bottomRight)

    this.intersectionPoint(left).fold(false) { p =>
      rect.topLeft.y <= p.y && p.y <= rect.bottomLeft.y
    } || this.intersectionPoint(top).fold(false) { p =>
      rect.topLeft.x <= p.x && p.x <= rect.topRight.x
    } || this.intersectionPoint(right).fold(false) { p =>
      rect.topRight.y <= p.y && p.y <= rect.bottomRight.y
    } || this.intersectionPoint(bottom).fold(false) { p =>
      rect.bottomLeft.x <= p.x && p.x <= rect.bottomRight.x
    }
  }

  def apply(x: Float): Float = this.grad * x + intercept
}

object LinearExpr {

  def apply(p1: Point, p2: Point): LinearExpr = {
    LinearExpr(
      p1.y - p2.y,
      p2.x - p1.x,
      (p1.x * p2.y) - (p2.x * p1.y))
  }
}
