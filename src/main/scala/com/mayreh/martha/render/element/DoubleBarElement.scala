package com.mayreh.martha.render.element

import com.mayreh.martha.render.{Color, Rect, Size}

case class DoubleBarElement(frame: Rect, color: Color = ScoreElementBase.defaultColor) extends ScoreElementBase {

  val element: scala.xml.Elem = {
    val Size(width, height) = frame.size
    val w = width / 3

    <g transform={s"translate(${frame.x}, ${frame.y})"}>
      <rect x="0" y="0" width={s"${w}"} height={s"${height}"} stroke="none" fill={color.hexRGB} />
      <rect x={s"${w*2}"} y="0" width={s"${w}"} height={s"${height}"} stroke="none" fill={color.hexRGB} />
    </g>
  }

  def withFrame(frame: Rect): ScoreElementBase = this.copy(frame = frame)
}
