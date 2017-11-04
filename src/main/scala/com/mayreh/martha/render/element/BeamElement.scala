package com.mayreh.martha.render.element

import com.mayreh.martha.render.Size

class BeamElement(size: Size, lineWidth: Float = 0, rightDown: Boolean = false) {

  val element: scala.xml.Elem = {
    val Size(w, h) = size

    if (rightDown) {
        <path d={ s"M 0 0 L ${w} ${h - lineWidth} L ${w} ${h} L 0 ${lineWidth}" } stroke="none" fill="black" />
    } else {
        <path d={ s"M 0 ${h - lineWidth} L ${w} 0 L ${w} ${lineWidth} L 0 ${h}" } stroke="none" fill="black" />
    }
  }
}
