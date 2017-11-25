package com.mayreh.martha.render.element

import com.mayreh.martha.render.{Color, Rect, Size}

case class SymbolTwoElement(frame: Rect, color: Color = ScoreElementBase.defaultColor) extends ScoreElementBase {

  val element: scala.xml.Elem = {
    val Size(w, h) = frame.size

      <path
      d={s"""M ${ w * 0.34715 } ${ h * 0.31269333 }
            |C ${ w * 0.33819 } ${ h * 0.38596666 }, ${ w * 0.25183 } ${ h * 0.41378 }, ${ w * 0.17709 } ${ h * 0.41348666 }
            |C ${ w * 0.10236 } ${ h * 0.41319335 }, ${ w * 0.01474 } ${ h * 0.37482667 }, ${ w * 0.00919 } ${ h * 0.31050667 }
            |C ${ w * 0.00364 } ${ h * 0.24618 }, ${ w * 0.04622 } ${ h * 0.14765333 }, ${ w * 0.12843 } ${ h * 0.094953336 }
            |C ${ w * 0.21064 } ${ h * 0.042253334 }, ${ w * 0.34963 } ${ h * 3.4E-4 }, ${ w * 0.49493 } ${ h * 3.4E-4 }
            |C ${ w * 0.64023 } ${ h * 3.4E-4 }, ${ w * 0.75881 } ${ h * 0.03792 }, ${ w * 0.84496 } ${ h * 0.07929333 }
            |C ${ w * 0.9311 } ${ h * 0.12066667 }, ${ w * 0.992 } ${ h * 0.1947 }, ${ w * 0.98659 } ${ h * 0.25668 }
            |C ${ w * 0.98118 } ${ h * 0.31865335 }, ${ w * 0.94399 } ${ h * 0.35588 }, ${ w * 0.85833 } ${ h * 0.41079333 }
            |C ${ w * 0.77267 } ${ h * 0.46570668 }, ${ w * 0.51871 } ${ h * 0.54609334 }, ${ w * 0.23025 } ${ h * 0.73715335 }
            |C ${ w * 0.52892 } ${ h * 0.7382467 }, ${ w * 0.60924 } ${ h * 0.8167267 }, ${ w * 0.71786 } ${ h * 0.81734 }
            |C ${ w * 0.75469 } ${ h * 0.81755334 }, ${ w * 0.8771 } ${ h * 0.78136665 }, ${ w * 0.91705 } ${ h * 0.7040067 }
            |L ${ w * 1.00253 } ${ h * 0.7179133 }
            |C ${ w * 0.9509 } ${ h * 0.76571333 }, ${ w * 0.86027 } ${ h * 0.9994467 }, ${ w * 0.69767 } ${ h * 1.0001067 }
            |C ${ w * 0.53507 } ${ h * 1.0007666 }, ${ w * 0.39596 } ${ h * 0.8607 }, ${ w * 0.27235 } ${ h * 0.8607 }
            |C ${ w * 0.14874 } ${ h * 0.8607 }, ${ w * 0.09022 } ${ h * 0.8921133 }, ${ w * 0.06766 } ${ h * 1.0001067 }
            |C ${ w * 0.01463 } ${ h * 0.98984665 }, ${ w * 0.00621 } ${ h * 0.98576665 }, ${ w * -5.7E-4 } ${ h * 0.98573333 }
            |C ${ w * -0.00681 } ${ h * 0.8456867 }, ${ w * 0.14824 } ${ h * 0.7111267 }, ${ w * 0.21716 } ${ h * 0.66694665 }
            |C ${ w * 0.28607 } ${ h * 0.6227667 }, ${ w * 0.60455 } ${ h * 0.38809332 }, ${ w * 0.67015 } ${ h * 0.31524667 }
            |C ${ w * 0.73576 } ${ h * 0.2424 }, ${ w * 0.7509 } ${ h * 0.19107333 }, ${ w * 0.73047 } ${ h * 0.14219333 }
            |C ${ w * 0.71004 } ${ h * 0.09331334 }, ${ w * 0.59863 } ${ h * 0.047593333 }, ${ w * 0.49493 } ${ h * 0.047593333 }
            |C ${ w * 0.39124 } ${ h * 0.047593333 }, ${ w * 0.25267 } ${ h * 0.082846664 }, ${ w * 0.24273 } ${ h * 0.14595333 }
            |C ${ w * 0.23279 } ${ h * 0.20906 }, ${ w * 0.35611 } ${ h * 0.23942 }, ${ w * 0.34715 } ${ h * 0.31269333 }""".stripMargin}
      stroke="none"
      fill={color.hexRGB}
      transform={s"translate(${frame.x}, ${frame.y})"}
      />
  }

  def withFrame(frame: Rect): ScoreElementBase = this.copy(frame = frame)
}
