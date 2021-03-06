package com.mayreh.martha.render.element

import com.mayreh.martha.render.{Color, Rect, Size}

case class SymbolFourElement(frame: Rect, color: Color = ScoreElementBase.defaultColor) extends ScoreElementBase {

  val element: scala.xml.Elem = {
    val Size(w, h) = frame.size

      <path
      d={s"""M ${ w * 0.3742 } ${ h * 3.6E-4 }
            |C ${ w * 0.3633 } ${ h * 0.11050667 }, ${ w * 0.3053 } ${ h * 0.22973333 }, ${ w * 0.26739 } ${ h * 0.2962 }
            |C ${ w * 0.21154 } ${ h * 0.39414 }, ${ w * 0.11517 } ${ h * 0.56398666 }, ${ w * 0.00256 } ${ h * 0.6867933 }
            |L ${ w * 0.00373 } ${ h * 0.73252 }
            |L ${ w * 0.50448 } ${ h * 0.73391336 }
            |C ${ w * 0.50307 } ${ h * 0.82428664 }, ${ w * 0.51082 } ${ h * 0.9184667 }, ${ w * 0.48951 } ${ h * 0.94221336 }
            |C ${ w * 0.46821 } ${ h * 0.96596664 }, ${ w * 0.40563 } ${ h * 0.9702067 }, ${ w * 0.30974 } ${ h * 0.9703533 }
            |L ${ w * 0.31155 } ${ h * 1.0013866 }
            |L ${ w * 0.99613 } ${ h * 1.00068 }
            |L ${ w * 0.99636 } ${ h * 0.9703533 }
            |C ${ w * 0.91733 } ${ h * 0.96871334 }, ${ w * 0.84905 } ${ h * 0.97148 }, ${ w * 0.82122 } ${ h * 0.94046 }
            |C ${ w * 0.79339 } ${ h * 0.90944 }, ${ w * 0.80072 } ${ h * 0.76336664 }, ${ w * 0.80002 } ${ h * 0.7333 }
            |L ${ w * 0.99613 } ${ h * 0.7334933 }
            |L ${ w * 0.99664 } ${ h * 0.6856667 }
            |L ${ w * 0.80002 } ${ h * 0.68442667 }
            |L ${ w * 0.80002 } ${ h * 0.20690666 }
            |L ${ w * 0.50448 } ${ h * 0.43389332 }
            |L ${ w * 0.50448 } ${ h * 0.68538 }
            |L ${ w * 0.09287 } ${ h * 0.6856667 }
            |L ${ w * 0.82218 } ${ h * -0.0013666666 }
            |L ${ w * 0.3742 } ${ h * 3.6E-4 }""".stripMargin}
      stroke="none"
      fill={color.hexRGB}
      transform={s"translate(${frame.x}, ${frame.y})"}
      />
  }

  def withFrame(frame: Rect): ScoreElementBase = this.copy(frame = frame)
}
