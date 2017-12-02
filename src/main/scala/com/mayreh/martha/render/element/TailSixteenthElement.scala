package com.mayreh.martha.render.element

import com.mayreh.martha.render.{Color, Rect, Size}

case class TailSixteenthElement(frame: Rect, inverted: Boolean = false, color: Color = ScoreElementBase.defaultColor) extends ScoreElementBase {

  val element: scala.xml.Elem = {
    val Size(width, height) = frame.size

    val w = width / 180
    val h = height / 450

      <path
      stroke="none"
      fill={color.hexRGB}
      transform={if (inverted) s"translate(${frame.x}, ${frame.y + height}) scale(1, -1)" else s"translate(${frame.x}, ${frame.y}) scale(1, 1)"}
      d={ s"""M ${ w * 0.1875 } ${ h * 0.53125 }
             |L ${ w * 0.4375 } ${ h * 105.03125 }
             |C ${ w * 94.1939 } ${ h * 135.17524 } ${ w * 148.619 } ${ h * 170.92998 } ${ w * 159.5 } ${ h * 194.9375 }
             |C ${ w * 169.47208 } ${ h * 216.93959 } ${ w * 158.66223 } ${ h * 236.3821 } ${ w * 142.9375 } ${ h * 255.75 }
             |C ${ w * 95.509789 } ${ h * 205.68459 } ${ w * 17.405446 } ${ h * 154.47365 } ${ w * 0.0625 } ${ h * 105.53125 }
             |L ${ w * 0 } ${ h * 231.21875 }
             |C ${ w * 153.4914 } ${ h * 262.27025 } ${ w * 157.6622 } ${ h * 306.76301 } ${ w * 164 } ${ h * 344.875 }
             |C ${ w * 170.3378 } ${ h * 382.98852 } ${ w * 143.60375 } ${ h * 425.61776 } ${ w * 111.96875 } ${ h * 449.96875 }
             |L ${ w * 122.125 } ${ h * 449.9375 }
             |C ${ w * 184.0108 } ${ h * 407.98702 } ${ w * 183.99585 } ${ h * 325.71924 } ${ w * 176.53125 } ${ h * 304.65625 }
             |C ${ w * 171.41541 } ${ h * 290.2203 } ${ w * 160.99966 } ${ h * 275.58684 } ${ w * 147.65625 } ${ h * 260.84375 }
             |L ${ w * 161 } ${ h * 245.5 }
             |C ${ w * 161 } ${ h * 245.5 } ${ w * 194.5993 } ${ h * 207.60751 } ${ w * 169.9375 } ${ h * 176.8125 }
             |C ${ w * 145.2757 } ${ h * 146.01749 } ${ w * 96.05415 } ${ h * 126.13725 } ${ w * 49.96875 } ${ h * 80.90625 }
             |C ${ w * 3.88515 } ${ h * 35.676752 } ${ w * 0.1875 } ${ h * 0.53125 } ${ w * 0.1875 } ${ h * 0.53125 } z""".stripMargin }
      />
  }

  def withFrame(frame: Rect): ScoreElementBase = this.copy(frame = frame)
}
