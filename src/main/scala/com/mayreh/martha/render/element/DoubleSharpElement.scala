package com.mayreh.martha.render.element

import com.mayreh.martha.render.{Color, Rect, Size}

case class DoubleSharpElement(frame: Rect, color: Color = ScoreElementBase.defaultColor) extends ScoreElementBase {

  val element: scala.xml.Elem = {
    val Size(w, h) = frame.size
    val lineWidth = w / 5
    val edgeWidth = lineWidth / math.sqrt(2).toFloat

    <g transform={s"translate(${frame.x}, ${frame.y})"}>
      <rect x="0" y="0" width={ s"${w/3}" } height={ s"${h/3}" } fill={color.hexRGB} stroke="none" />
      <rect x={ s"${w/3 * 2}" } y="0" width={ s"${w/3}" } height={ s"${h/3}" } fill={color.hexRGB} stroke="none" />
      <rect x="0" y={ s"${h/3 * 2}" } width={ s"${w/3}" } height={ s"${h/3}" } fill={color.hexRGB} stroke="none" />
      <rect x={ s"${w/3 * 2}" } y={ s"${h/3 * 2}" } width={ s"${w/3}" } height={ s"${h/3}" } fill={color.hexRGB} stroke="none" />

      <path fill={color.hexRGB} stroke="none"
            d={ s"M 0 ${edgeWidth} L ${edgeWidth} 0 L ${w} ${h - edgeWidth} L ${w - edgeWidth} ${h} Z" } />
      <path fill={color.hexRGB} stroke="none"
            d={ s"M 0 ${h - edgeWidth} L ${w - edgeWidth} 0 L ${w} ${edgeWidth} L ${edgeWidth} ${h} Z" } />
    </g>
  }

  def withFrame(frame: Rect): ScoreElementBase = this.copy(frame = frame)
}
