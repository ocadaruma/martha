package com.mayreh.martha.render.element

import com.mayreh.martha.render.Size

class DoubleBarElement(size: Size) {

  val element: scala.xml.Elem = {
    val Size(width, height) = size
    val w = width / 3

    <g>
      <rect x="0" y="0" width={s"${w}"} height={s"${height}"} stroke="none" fill="black" />
      <rect x={s"${w*2}"} y="0" width={s"${w}"} height={s"${height}"} stroke="none" fill="black" />
    </g>
  }
}
