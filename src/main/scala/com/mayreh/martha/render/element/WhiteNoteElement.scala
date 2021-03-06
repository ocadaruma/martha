package com.mayreh.martha.render.element

import com.mayreh.martha.render.{Color, Rect, Size}

case class WhiteNoteElement(frame: Rect, color: Color = ScoreElementBase.defaultColor) extends ScoreElementBase {

  val element: scala.xml.Elem = {
    val Size(w, h) = frame.size

      <path
      d={s"""M ${ w * 0.19589 } ${ h * 0.20055556 }
            |C ${ w * -0.02521 } ${ h * 0.4212 }, ${ w * -0.06887 } ${ h * 0.7327333 }, ${ w * 0.09838 } ${ h * 0.8963778 }
            |C ${ w * 0.26562 } ${ h * 1.0600222 }, ${ w * 0.58043 } ${ h * 1.0138222 }, ${ w * 0.80152 } ${ h * 0.7931778 }
            |C ${ w * 1.02262 } ${ h * 0.5725333 }, ${ w * 1.06627 } ${ h * 0.26101112 }, ${ w * 0.89903 } ${ h * 0.09735555 }
            |C ${ w * 0.73179 } ${ h * -0.06628889 }, ${ w * 0.41698 } ${ h * -0.020088889 }, ${ w * 0.19589 } ${ h * 0.20055556 }
            |M ${ w * 0.1922 } ${ h * 0.52434444 }
            |C ${ w * 0.05276 } ${ h * 0.7376889 }, ${ w * 0.07919 } ${ h * 0.89974445 }, ${ w * 0.25123 } ${ h * 0.8863 }
            |C ${ w * 0.42327 } ${ h * 0.87284446 }, ${ w * 0.67577 } ${ h * 0.689 }, ${ w * 0.81521 } ${ h * 0.47565556 }
            |C ${ w * 0.95465 } ${ h * 0.2623111 }, ${ w * 0.92822 } ${ h * 0.10025556 }, ${ w * 0.75618 } ${ h * 0.11371111 }
            |C ${ w * 0.58414 } ${ h * 0.12715556 }, ${ w * 0.33164 } ${ h * 0.311 }, ${ w * 0.1922 } ${ h * 0.52434444 }""".stripMargin}
      stroke="none"
      fill={color.hexRGB}
      fill-rule="evenodd"
      transform={s"translate(${frame.x}, ${frame.y})"}
      />
  }

  def withFrame(frame: Rect): ScoreElementBase = this.copy(frame = frame)
}
