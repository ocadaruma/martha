package com.mayreh.martha.render.element

import com.mayreh.martha.render.{Color, Rect, Size}

case class BeamElement(
  frame: Rect,
  lineWidth: Float = 0,
  rightDown: Boolean = false,
  color: Color = ScoreElementBase.defaultColor) extends ScoreElementBase {

  val element: scala.xml.Elem = {
    val Size(w, h) = frame.size

    if (rightDown) {
        <path d={ s"M 0 0 L ${w} ${h - lineWidth} L ${w} ${h} L 0 ${lineWidth}" } stroke="none" fill={color.hexRGB} transform={s"translate(${frame.x}, ${frame.y})"} />
    } else {
        <path d={ s"M 0 ${h - lineWidth} L ${w} 0 L ${w} ${lineWidth} L 0 ${h}" } stroke="none" fill={color.hexRGB} transform={s"translate(${frame.x}, ${frame.y})"} />
    }
  }

  def withFrame(frame: Rect): ScoreElementBase = this.copy(frame = frame)
}
