package com.mayreh.martha.render.element

import com.mayreh.martha.render.{Rect, Size}

case class BeamElement(frame: Rect, lineWidth: Float = 0, rightDown: Boolean = false) extends ScoreElementBase {

  val element: scala.xml.Elem = {
    val Size(w, h) = frame.size

    if (rightDown) {
        <path d={ s"M 0 0 L ${w} ${h - lineWidth} L ${w} ${h} L 0 ${lineWidth}" } stroke="none" fill="black" transform={s"translate(${frame.x}, ${frame.y})"} />
    } else {
        <path d={ s"M 0 ${h - lineWidth} L ${w} 0 L ${w} ${lineWidth} L 0 ${h}" } stroke="none" fill="black" transform={s"translate(${frame.x}, ${frame.y})"} />
    }
  }
}
