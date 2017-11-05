package com.mayreh.martha.render.element

import com.mayreh.martha.render.{Rect, Size}

class RectElement(frame: Rect) {

  val element: scala.xml.Elem = {
    val Size(w, h) = frame.size

    <rect x="0" y="0" width={s"${w}"} height={s"${h}"} stroke="none" fill="black" transform={s"translate(${frame.x}, ${frame.y})"} />
  }
}
