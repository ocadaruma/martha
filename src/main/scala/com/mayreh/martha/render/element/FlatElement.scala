package com.mayreh.martha.render.element

import com.mayreh.martha.render.{Color, Rect, Size}

case class FlatElement(frame: Rect, color: Color = ScoreElementBase.defaultColor) extends ScoreElementBase {

  val element: scala.xml.Elem = {

    val Size(w, h) = frame.size

      <path
      d ={s"""M ${ w * -0.005984375 } ${ h * 0.001425 }
             |L ${ w * -0.005984375 } ${ h * 1.00188 }
             |C ${ w * 0.14423437 } ${ h * 0.951595 }, ${ w * 0.5745625 } ${ h * 0.876105 }, ${ w * 0.74609375 } ${ h * 0.824875 }
             |C ${ w * 0.917625 } ${ h * 0.77364 }, ${ w * 1.0158437 } ${ h * 0.68495 }, ${ w * 0.99775 } ${ h * 0.64328 }
             |C ${ w * 0.9796563 } ${ h * 0.601605 }, ${ w * 0.91353124 } ${ h * 0.561195 }, ${ w * 0.67990625 } ${ h * 0.55025 }
             |C ${ w * 0.44628125 } ${ h * 0.539305 }, ${ w * 0.059046876 } ${ h * 0.63771 }, ${ w * 0.0206875 } ${ h * 0.64328 }
             |C ${ w * 0.007421875 } ${ h * 0.6452 }, ${ w * 0.041203126 } ${ h * 0.828645 }, ${ w * 0.0449375 } ${ h * 0.84895 }
             |C ${ w * 0.061328124 } ${ h * 0.587975 }, ${ w * 0.510375 } ${ h * 0.614 }, ${ w * 0.5958281 } ${ h * 0.635535 }
             |C ${ w * 0.68128127 } ${ h * 0.657075 }, ${ w * 0.67990625 } ${ h * 0.68127 }, ${ w * 0.67990625 } ${ h * 0.71084 }
             |C ${ w * 0.67990625 } ${ h * 0.740415 }, ${ w * 0.6310469 } ${ h * 0.791005 }, ${ w * 0.4820625 } ${ h * 0.83643 }
             |C ${ w * 0.3330625 } ${ h * 0.88185 }, ${ w * 0.16515625 } ${ h * 0.90546 }, ${ w * 0.08853125 } ${ h * 0.93096 }
             |L ${ w * 0.09765625 } ${ h * 0.001425 }
             |L ${ w * -0.005984375 } ${ h * 0.001425 }""".stripMargin}
      stroke="none"
      fill={color.hexRGB}
      transform={s"translate(${frame.x}, ${frame.y})"}
      />
  }

  def withFrame(frame: Rect): ScoreElementBase = this.copy(frame = frame)
}
