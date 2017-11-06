package com.mayreh.martha.render.element

import com.mayreh.martha.render.{Rect, Size}

case class SharpElement(frame: Rect) extends ScoreElementBase {

  val element: scala.xml.Elem = {
    val Size(w, h) = frame.size

    <g transform={s"translate(${frame.x}, ${frame.y})"}>
      <path d={ s"M ${w/4} 0 L ${w/4} ${h}" } fill="none" stroke="black" stroke-width={ s"${w/10}" } />
      <path d={ s"M ${w/4 * 3} 0 L ${w/4 * 3} ${h}" } fill="none" stroke="black" stroke-width={ s"${w/10}" } />
      <path d={ s"M 0 ${h/3} L ${w} ${h/6}" } fill="none" stroke="black" stroke-width={ s"${w/5}" } />
      <path d={ s"M ${w} ${h/3 * 2} L 0 ${h/6 * 5}" } fill="none" stroke="black" stroke-width={ s"${w/5}" } />
    </g>
  }
}
