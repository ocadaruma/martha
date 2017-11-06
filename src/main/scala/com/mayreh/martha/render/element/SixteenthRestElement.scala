package com.mayreh.martha.render.element

import com.mayreh.martha.render.{Rect, Size}

case class SixteenthRestElement(frame: Rect) extends ScoreElementBase {

  val element: scala.xml.Elem = {
    val Size(w, h) = frame.size

      <path
      d={s"""M ${ w * 0.14828 } ${ h * 0.107425 }
            |C ${ w * 0.14828 } ${ h * 0.068835 }, ${ w * 0.18289 } ${ h * 0.04353 }, ${ w * 0.21616 } ${ h * 0.02835 }
            |C ${ w * 0.24944 } ${ h * 0.01317 }, ${ w * 0.29961 } ${ h * -0.003135 }, ${ w * 0.38091 } ${ h * -0.00201 }
            |C ${ w * 0.46222 } ${ h * -8.85E-4 }, ${ w * 0.52309 } ${ h * 0.01233 }, ${ w * 0.56918 } ${ h * 0.04283 }
            |C ${ w * 0.61527 } ${ h * 0.07333 }, ${ w * 0.60061 } ${ h * 0.116965 }, ${ w * 0.57486 } ${ h * 0.15206 }
            |C ${ w * 0.61303 } ${ h * 0.149235 }, ${ w * 0.68897 } ${ h * 0.12785 }, ${ w * 0.7166 } ${ h * 0.11991 }
            |C ${ w * 0.74423 } ${ h * 0.111975 }, ${ w * 0.78347 } ${ h * 0.096415 }, ${ w * 0.80149 } ${ h * 0.08906 }
            |C ${ w * 0.85129 } ${ h * 0.068725 }, ${ w * 0.86899 } ${ h * 0.049715 }, ${ w * 0.91264 } ${ h * 0.01247 }
            |L ${ w * 1.00184 } ${ h * 0.026585 }
            |C ${ w * 0.94676 } ${ h * 0.10237 }, ${ w * 0.83661 } ${ h * 0.253945 }, ${ w * 0.78153 } ${ h * 0.32973 }
            |C ${ w * 0.66005 } ${ h * 0.49689 }, ${ w * 0.41708 } ${ h * 0.831215 }, ${ w * 0.29559 } ${ h * 0.99838 }
            |L ${ w * 0.18503 } ${ h * 0.97408 }
            |L ${ w * 0.57133 } ${ h * 0.516035 }
            |C ${ w * 0.48183 } ${ h * 0.56322 }, ${ w * 0.34513 } ${ h * 0.600325 }, ${ w * 0.22712 } ${ h * 0.600395 }
            |C ${ w * 0.1091 } ${ h * 0.600465 }, ${ w * -0.00296 } ${ h * 0.541825 }, ${ w * -0.00296 } ${ h * 0.5072 }
            |C ${ w * -0.00296 } ${ h * 0.47257 }, ${ w * 0.01199 } ${ h * 0.44503 }, ${ w * 0.06017 } ${ h * 0.42293 }
            |C ${ w * 0.10834 } ${ h * 0.40083 }, ${ w * 0.16958 } ${ h * 0.390115 }, ${ w * 0.22712 } ${ h * 0.390115 }
            |C ${ w * 0.28465 } ${ h * 0.390115 }, ${ w * 0.33111 } ${ h * 0.393905 }, ${ w * 0.38826 } ${ h * 0.42174 }
            |C ${ w * 0.4454 } ${ h * 0.449575 }, ${ w * 0.43495 } ${ h * 0.492785 }, ${ w * 0.41557 } ${ h * 0.518375 }
            |C ${ w * 0.52461 } ${ h * 0.48267 }, ${ w * 0.59916 } ${ h * 0.41644 }, ${ w * 0.66495 } ${ h * 0.3465 }
            |C ${ w * 0.73075 } ${ h * 0.276555 }, ${ w * 0.80909 } ${ h * 0.192415 }, ${ w * 0.85563 } ${ h * 0.12623 }
            |C ${ w * 0.72062 } ${ h * 0.16417 }, ${ w * 0.54149 } ${ h * 0.21166 }, ${ w * 0.37675 } ${ h * 0.21166 }
            |C ${ w * 0.21202 } ${ h * 0.21166 }, ${ w * 0.14828 } ${ h * 0.146015 }, ${ w * 0.14828 } ${ h * 0.107425 }""".stripMargin}
      stroke="transparent"
      fill="black"
      transform={s"translate(${frame.x}, ${frame.y})"}
      />
  }
}
