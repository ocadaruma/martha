package com.mayreh.martha.render.element

import com.mayreh.martha.render.{Color, Rect, Size}

case class EllipseElement(frame: Rect, color: Color = ScoreElementBase.defaultColor) extends ScoreElementBase {

  val element: scala.xml.Elem = {
    val Size(w, h) = frame.size

      <ellipse cx={s"${w/2}"} cy={s"${h/2}"} rx={s"${w/2}"} ry={s"${h/2}"} stroke="none" fill={color.hexRGB} transform={s"translate(${frame.x}, ${frame.y})"} />
  }
}
