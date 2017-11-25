package com.mayreh.martha.render.element

import com.mayreh.martha.render.{Color, Rect, Size}

case class FlatElement(frame: Rect, color: Color = ScoreElementBase.defaultColor) extends ScoreElementBase {

  val element: scala.xml.Elem = {

    val Size(width, height) = frame.size
    val w = width / 120
    val h = height / 330

      <path
      d ={ s"""M ${ w * 0.71875 } ${ h * 0.46875 }
              |L ${ w * 0.71875 } ${ h * 330.625 }
              |C ${ w * 17.307499 } ${ h * 314.03095 } ${ w * 68.9475 } ${ h * 289.12465 } ${ w * 89.53125 } ${ h * 272.21875 }
              |C ${ w * 110.115 } ${ h * 255.3112 } ${ w * 121.88999 } ${ h * 226.03235 } ${ w * 119.71875 } ${ h * 212.28125 }
              |C ${ w * 117.54751 } ${ h * 198.5285 } ${ w * 109.62875 } ${ h * 185.2056 } ${ w * 81.59375 } ${ h * 181.59375 }
              |C ${ w * 59.600129 } ${ h * 178.76023 } ${ w * 26.538322 } ${ h * 197.94292 } ${ w * 10.96875 } ${ h * 207.3125 }
              |L ${ w * 11.71875 } ${ h * 0.46875 }
              |L ${ w * 0.71875 } ${ h * 0.46875 } z
              |M ${ w * 54.625 } ${ h * 205.53125 }
              |C ${ w * 62.285912 } ${ h * 205.62437 } ${ w * 68.615958 } ${ h * 207.72003 } ${ w * 71.5 } ${ h * 209.71875 }
              |C ${ w * 81.75438 } ${ h * 216.82695 } ${ w * 81.59375 } ${ h * 224.8044 } ${ w * 81.59375 } ${ h * 234.5625 }
              |C ${ w * 81.59375 } ${ h * 244.32225 } ${ w * 75.721878 } ${ h * 261.041 } ${ w * 57.84375 } ${ h * 276.03125 }
              |C ${ w * 39.96375 } ${ h * 291.01985 } ${ w * 19.82 } ${ h * 298.80375 } ${ w * 10.625 } ${ h * 307.21875 }
              |L ${ w * 10.84375 } ${ h * 242.625 }
              |C ${ w * 20.266398 } ${ h * 212.64859 } ${ w * 39.897506 } ${ h * 205.35223 } ${ w * 54.625 } ${ h * 205.53125 } z""".stripMargin}
      stroke="none"
      fill={color.hexRGB}
      transform={s"translate(${frame.x}, ${frame.y})"}
      />
  }

  def withFrame(frame: Rect): ScoreElementBase = this.copy(frame = frame)
}
