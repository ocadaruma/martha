package com.mayreh.martha.render.element

import com.mayreh.martha.render.{Rect, Size}

class DoubleBarElement(frame: Rect) {

  val element: scala.xml.Elem = {
    val Size(width, height) = frame.size
    val w = width / 3

    <g transform={s"translate(${frame.x}, ${frame.y})"}>
      <rect x="0" y="0" width={s"${w}"} height={s"${height}"} stroke="none" fill="black" />
      <rect x={s"${w*2}"} y="0" width={s"${w}"} height={s"${height}"} stroke="none" fill="black" />
    </g>
  }
}
