package com.mayreh.martha.render.element

import com.mayreh.martha.render.Size

class RectElement(size: Size) {

  val element: scala.xml.Elem = {
    val Size(w, h) = size

    <rect x="0" y="0" width={s"${w}"} height={s"${h}"} stroke="none" fill="black" />
  }
}
