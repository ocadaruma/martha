package com.mayreh.martha.render.element

import com.mayreh.martha.render.{Color, Rect, Size}

case class QuarterRestElement(frame: Rect, color: Color = ScoreElementBase.defaultColor) extends ScoreElementBase {

  val element: scala.xml.Elem = {
    val Size(w, h) = frame.size

      <path
      d={s"""M ${ w * 0.12912 } ${ h * -4.0E-4 }
            |L ${ w * 0.89674 } ${ h * 0.30782 }
            |C ${ w * 0.64187 } ${ h * 0.40385333 }, ${ w * 0.55253 } ${ h * 0.44053 }, ${ w * 0.57376 } ${ h * 0.5365 }
            |C ${ w * 0.59498 } ${ h * 0.6324667 }, ${ w * 0.79484 } ${ h * 0.73582 }, ${ w * 0.99992 } ${ h * 0.80235 }
            |L ${ w * 0.9632 } ${ h * 0.81421334 }
            |C ${ w * 0.73806 } ${ h * 0.79371333 }, ${ w * 0.45794 } ${ h * 0.76783 }, ${ w * 0.38633 } ${ h * 0.80817 }
            |C ${ w * 0.31473 } ${ h * 0.8485133 }, ${ w * 0.2852 } ${ h * 0.90817666 }, ${ w * 0.51789 } ${ h * 0.99227333 }
            |L ${ w * 0.48587 } ${ h * 1.00113 }
            |C ${ w * 0.21686 } ${ h * 0.9222933 }, ${ w * -0.08527 } ${ h * 0.85313666 }, ${ w * 0.01755 } ${ h * 0.75733334 }
            |C ${ w * 0.12038 } ${ h * 0.66153 }, ${ w * 0.47478 } ${ h * 0.68988335 }, ${ w * 0.70579 } ${ h * 0.71477336 }
            |L ${ w * 0.05793 } ${ h * 0.49602 }
            |C ${ w * 0.24928 } ${ h * 0.41585332 }, ${ w * 0.42521 } ${ h * 0.31671 }, ${ w * 0.42826 } ${ h * 0.24957334 }
            |C ${ w * 0.43065 } ${ h * 0.19700667 }, ${ w * 0.31473 } ${ h * 0.11691 }, ${ w * 0.0797 } ${ h * 0.017193334 }
            |L ${ w * 0.12912 } ${ h * -4.0E-4 }""".stripMargin}
      stroke="none"
      fill={color.hexRGB}
      transform={s"translate(${frame.x}, ${frame.y})"}
      />
  }

  def withFrame(frame: Rect): ScoreElementBase = this.copy(frame = frame)
}
