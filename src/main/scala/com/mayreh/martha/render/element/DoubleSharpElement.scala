package com.mayreh.martha.render.element

import com.mayreh.martha.render.{Rect, Size}

case class DoubleSharpElement(frame: Rect) extends ScoreElementBase {

  val element: scala.xml.Elem = {
    val Size(w, h) = frame.size
    val lineWidth = w / 5

    <g transform={s"translate(${frame.x}, ${frame.y})"}>
      <rect x="0" y="0" width={ s"${w/3}" } height={ s"${h/3}" } fill="black" stroke="none" />
      <rect x={ s"${w/3 * 2}" } y="0" width={ s"${w/3}" } height={ s"${h/3}" } fill="black" stroke="none" />
      <rect x="0" y={ s"${h/3 * 2}" } width={ s"${w/3}" } height={ s"${h/3}" } fill="black" stroke="none" />
      <rect x={ s"${w/3 * 2}" } y={ s"${h/3 * 2}" } width={ s"${w/3}" } height={ s"${h/3}" } fill="black" stroke="none" />

      <path d={ s"M ${w/6} ${h/6} L ${w/6 * 5} ${h/6 * 5}" } fill="none" stroke="black" stroke-width={ s"${lineWidth}" } />
      <path d={ s"M ${w/6 * 5} ${h/6} L ${w/6} ${h/6 * 5}" } fill="none" stroke="black" stroke-width={ s"${lineWidth}" } />
    </g>
  }
}
