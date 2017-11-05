package com.mayreh.martha.render.element

import com.mayreh.martha.render.{Rect, Size}

class BlackNoteElement(frame: Rect) {

  val element: scala.xml.Elem = {
    val Size(w, h) = frame.size

      <path
      d={s"""M ${ w * 0.19589 } ${ h * 0.20055556 }
            |C ${ w * -0.02521 } ${ h * 0.4212 }, ${ w * -0.06887 } ${ h * 0.7327333 }, ${ w * 0.09838 } ${ h * 0.8963778 }
            |C ${ w * 0.26562 } ${ h * 1.0600222 }, ${ w * 0.58043 } ${ h * 1.0138222 }, ${ w * 0.80152 } ${ h * 0.7931778 }
            |C ${ w * 1.02262 } ${ h * 0.5725333 }, ${ w * 1.06627 } ${ h * 0.26101112 }, ${ w * 0.89903 } ${ h * 0.09735555 }
            |C ${ w * 0.73179 } ${ h * -0.06628889 }, ${ w * 0.41698 } ${ h * -0.020088889 }, ${ w * 0.19589 } ${ h * 0.20055556 }""".stripMargin}
      stroke="transparent"
      fill="black"
      transform={s"translate(${frame.x}, ${frame.y})"}
      />
  }
}
