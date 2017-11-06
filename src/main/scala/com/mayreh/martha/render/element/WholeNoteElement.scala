package com.mayreh.martha.render.element

import com.mayreh.martha.render.{Rect, Size}

case class WholeNoteElement(frame: Rect) extends ScoreElementBase {

  val element: scala.xml.Elem = {
    val Size(w, h) = frame.size

      <path
      d={s"""M  0 ${ h / 2 } A ${ w / 2 } ${ h / 2 }, 0, 0, 0, ${w} ${ h / 2 }
            |M  ${w} ${ h / 2 } A ${ w / 2 } ${ h / 2 }, 0, 0, 0, 0 ${ h / 2 }
            |M ${ w * 0.71433 } ${ h * 0.32087 }
            |C ${ w * 0.5733 } ${ h * 0.10984 }, ${ w * 0.36767 } ${ h * 0.0194857 }, ${ w * 0.25504 } ${ h * 0.11904 }
            |C ${ w * 0.14241 } ${ h * 0.2186 }, ${ w * 0.16542 } ${ h * 0.47 }, ${ w * 0.30644 } ${ h * 0.681414 }
            |C ${ w * 0.44746 } ${ h * 0.892457 }, ${ w * 0.65309 } ${ h * 0.98281 }, ${ w * 0.76573 } ${ h * 0.883257 }
            |C ${ w * 0.87836 } ${ h * 0.7836857 }, ${ w * 0.85535 } ${ h * 0.5319 }, ${ w * 0.71433 } ${ h * 0.32087 }""".stripMargin}
      stroke="transparent"
      fill="black"
      fill-rule="evenodd"
      transform={s"translate(${frame.x}, ${frame.y})"}
      />
  }
}
