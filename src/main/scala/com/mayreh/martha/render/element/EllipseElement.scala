package com.mayreh.martha.render.element

import com.mayreh.martha.render.{Rect, Size}

class EllipseElement(frame: Rect) {

  val element: scala.xml.Elem = {
    val Size(w, h) = frame.size

      <ellipse cx={s"${w/2}"} cy={s"${h/2}"} rx={s"${w/2}"} ry={s"${h/2}"} stroke="none" fill="black" transform={s"translate(${frame.x}, ${frame.y})"} />
  }
}
