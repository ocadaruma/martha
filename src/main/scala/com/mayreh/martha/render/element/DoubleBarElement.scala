package com.mayreh.martha.render.element

import com.mayreh.martha.render.{Color, Rect, Size}

case class DoubleBarElement(frame: Rect, color: Color = ScoreElementBase.defaultColor) extends ScoreElementBase {

  val element: scala.xml.Elem = {
    val Size(width, height) = frame.size
    val w = width / 3

      <path
      stroke="none"
      fill={color.hexRGB}
      d={ s"""M 0 0 L 0 ${height} L ${w} ${height} L ${w} 0 z
             |M ${w*2} 0 L ${w*2} ${height} L ${width} ${height} L ${width} 0 z""".stripMargin }
      transform={s"translate(${frame.x}, ${frame.y})"}
      />
  }

  def withFrame(frame: Rect): ScoreElementBase = this.copy(frame = frame)
}
