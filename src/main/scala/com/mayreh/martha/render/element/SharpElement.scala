package com.mayreh.martha.render.element

import com.mayreh.martha.render.{Color, Rect, Size}

case class SharpElement(frame: Rect, color: Color = ScoreElementBase.defaultColor) extends ScoreElementBase {

  val element: scala.xml.Elem = {
    val Size(w, h) = frame.size

    val vw = w/10 // vertical line width
    val hw = w/5 // horizontal line width

    <g transform={s"translate(${frame.x}, ${frame.y})"}>
      <path fill={color.hexRGB} stroke="none"
            d={ s"M ${w/4 - vw/2} 0 L ${w/4 + vw/2} 0 L ${w/4 + vw/2} ${h} L ${w/4 - vw/2} ${h} Z" } />

      <path fill={color.hexRGB} stroke="none"
            d={ s"M ${w/4*3 - vw/2} 0 L ${w/4*3 + vw/2} 0 L ${w/4*3 + vw/2} ${h} L ${w/4*3 - vw/2} ${h} Z" } />

      <path fill={color.hexRGB} stroke="none"
            d={ s"M 0 ${h/3 - hw/2} L ${w} ${h/6 - hw/2} L ${w} ${h/6 + hw/2} L 0 ${h/3 + hw/2} Z" } />

      <path fill={color.hexRGB} stroke="none"
            d={ s"M 0 ${h/6*5 - hw/2} L ${w} ${h/3*2 - hw/2} L ${w} ${h/3*2 + hw/2} 0 ${h/6*5 + hw/2} Z" } />
    </g>
  }
}
