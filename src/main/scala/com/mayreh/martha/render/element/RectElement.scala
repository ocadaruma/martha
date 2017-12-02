package com.mayreh.martha.render.element

import com.mayreh.martha.render.{Color, Rect, Size}

case class RectElement(frame: Rect, color: Color = ScoreElementBase.defaultColor) extends ScoreElementBase {

  val element: scala.xml.Elem = {
    val Size(w, h) = frame.size

    <rect x="0" y="0" width={s"${w}"} height={s"${h}"} stroke="none" fill={color.hexRGB} transform={s"translate(${frame.x}, ${frame.y})"} />
  }

  def withFrame(frame: Rect): ScoreElementBase = this.copy(frame = frame)
}
