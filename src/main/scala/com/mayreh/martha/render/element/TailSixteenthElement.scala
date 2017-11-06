package com.mayreh.martha.render.element

import com.mayreh.martha.render.{Rect, Size}

case class TailSixteenthElement(frame: Rect, inverted: Boolean = false) extends ScoreElementBase {

  val element: scala.xml.Elem = {
    val Size(w, height) = frame.size
    val h = height * 230 / 300

    val y = height * 70.0 / 300

    val path2 =
        <path
        d={s"""M ${ w * 3.8E-4 } ${ y + h * 0.0015391305 }
              |C ${ w * 0.12842 } ${ y + h * 0.19006087 }, ${ w * 0.85273 } ${ y + h * 0.39019564 }, ${ w * 0.98078 } ${ y + h * 0.5787174 }
              |C ${ w * 1.02225 } ${ y + h * 0.63976955 }, ${ w * 1.02225 } ${ y + h * 0.8782435 }, ${ w * 0.67844 } ${ y + h * 0.9998391 }
              |L ${ w * 0.622 } ${ y + h * 0.9999348 }
              |C ${ w * 0.79775 } ${ y + h * 0.92935216 }, ${ w * 0.94627 } ${ y + h * 0.80572176 }, ${ w * 0.91106 } ${ y + h * 0.6952478 }
              |C ${ w * 0.87585 } ${ y + h * 0.58477825 }, ${ w * 0.85273 } ${ y + h * 0.45585653 }, 0.0 ${ y + h * 0.36585218 }""".stripMargin}
        />

    val h2 = height * 180 / 300

    val path =
        <path
        d={s"""M ${ w * -0.00103 } ${ h2 * 0.0019666667 }
              |C ${ w * -0.00103 } ${ h2 * 0.0019666667 }, ${ w * 0.02165 } ${ h2 * 0.13217779 }, ${ w * 0.27767 } ${ h2 * 0.29969445 }
              |C ${ w * 0.5337 } ${ h2 * 0.46721667 }, ${ w * 0.80715 } ${ h2 * 0.5408555 }, ${ w * 0.94416 } ${ h2 * 0.6549111 }
              |C ${ w * 1.08117 } ${ h2 * 0.7689667 }, ${ w * 0.8944 } ${ h2 * 0.90931666 }, ${ w * 0.8944 } ${ h2 * 0.90931666 }
              |L ${ w * 0.8097 } ${ h2 * 0.97421664 }
              |C ${ w * 0.79886 } ${ h2 * 0.96208334 }, ${ w * 0.66976 } ${ h2 * 1.0451889 }, ${ w * 0.76977 } ${ h2 * 0.96690553 }
              |C ${ w * 0.86977 } ${ h2 * 0.88862777 }, ${ w * 0.94653 } ${ h2 * 0.8108778 }, ${ w * 0.88608 } ${ h2 * 0.72196114 }
              |C ${ w * 0.82563 } ${ h2 * 0.6330444 }, ${ w * 0.52323 } ${ h2 * 0.5006222 }, ${ w * -0.0025 } ${ h2 * 0.38897777 }
              |L ${ w * -0.00103 } ${ h2 * 0.0019666667 }""".stripMargin}
        />

    val g =
      <g stroke="transparent"
         fill="black"
         transform={if (inverted) s"translate(${frame.x}, ${frame.y + height}) scale(1, -1)" else s"translate(${frame.x}, ${frame.y}) scale(1, 1)"}>
        {path2}
        {path}
      </g>
    g
  }
}
