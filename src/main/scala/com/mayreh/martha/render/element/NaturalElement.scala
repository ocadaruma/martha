package com.mayreh.martha.render.element

import com.mayreh.martha.render.{Rect, Size}

class NaturalElement(frame: Rect) {

  val element: scala.xml.Elem = {
    val Size(w, h) = frame.size

      <path
      d={s"""M ${ w * 0.01334 } ${ h * -1.95E-4 }
            |L ${ w * 0.00878 } ${ h * 0.79714 }
            |L ${ w * 0.88244 } ${ h * 0.72157 }
            |L ${ w * 0.88244 } ${ h * 0.654495 }
            |L ${ w * 0.11694 } ${ h * 0.72157 }
            |L ${ w * 0.11694 } ${ h * 0.33223 }
            |L ${ w * 0.88244 } ${ h * 0.261785 }
            |L ${ w * 0.88244 } ${ h * 1.00082 }
            |L ${ w * 1.00844 } ${ h * 1.00082 }
            |L ${ w * 1.00844 } ${ h * 0.177515 }
            |L ${ w * 0.11694 } ${ h * 0.261785 }
            |L ${ w * 0.11694 } ${ h * -1.95E-4 }
            |L ${ w * 0.01334 } ${ h * -1.95E-4 }""".stripMargin}
      stroke="transparent"
      fill="black"
      transform={s"translate(${frame.x}, ${frame.y})"}
      />
  }
}
