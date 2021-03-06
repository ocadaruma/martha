package com.mayreh.martha.render.element

import com.mayreh.martha.render.{Color, Rect, Size}

case class EighthRestElement(frame: Rect, color: Color = ScoreElementBase.defaultColor) extends ScoreElementBase {

  val element: scala.xml.Elem = {
    val Size(w, h) = frame.size

      <path
      d={s"""M ${ w * 4.1E-4 } ${ h * 0.15148 }
            |C ${ w * 4.1E-4 } ${ h * 0.100253336 }, ${ w * 0.03507 } ${ h * 0.0665 }, ${ w * 0.06429 } ${ h * 0.047353335 }
            |C ${ w * 0.0935 } ${ h * 0.0282 }, ${ w * 0.15036 } ${ h * -0.00218 }, ${ w * 0.23874 } ${ h * -0.00218 }
            |C ${ w * 0.32711 } ${ h * -0.00218 }, ${ w * 0.37081 } ${ h * 0.022073334 }, ${ w * 0.4115 } ${ h * 0.048746668 }
            |C ${ w * 0.45218 } ${ h * 0.07541333 }, ${ w * 0.46265 } ${ h * 0.13194 }, ${ w * 0.467 } ${ h * 0.18278667 }
            |L ${ w * 0.91665 } ${ h * -3.9333332E-4 }
            |L ${ w * 1.00367 } ${ h * 0.02578 }
            |L ${ w * 0.50715 } ${ h * 0.99453336 }
            |L ${ w * 0.39106 } ${ h * 0.9599867 }
            |C ${ w * 0.55166 } ${ h * 0.7738733 }, ${ w * 0.80765 } ${ h * 0.30632666 }, ${ w * 0.78799 } ${ h * 0.2012 }
            |C ${ w * 0.76833 } ${ h * 0.09607334 }, ${ w * 0.36529 } ${ h * 0.30407333 }, ${ w * 0.24391 } ${ h * 0.30407333 }
            |C ${ w * 0.12252 } ${ h * 0.30407333 }, ${ w * 0.08204 } ${ h * 0.26653334 }, ${ w * 0.05588 } ${ h * 0.24938667 }
            |C ${ w * 0.02972 } ${ h * 0.23224 }, ${ w * 4.1E-4 } ${ h * 0.20271334 }, ${ w * 4.1E-4 } ${ h * 0.15148 }""".stripMargin}
      stroke="none"
      fill={color.hexRGB}
      transform={s"translate(${frame.x}, ${frame.y})"}
      />
  }

  def withFrame(frame: Rect): ScoreElementBase = this.copy(frame = frame)
}
