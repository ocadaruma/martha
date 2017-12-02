package com.mayreh.martha.render.element

import com.mayreh.martha.render.math.LinearExpr
import com.mayreh.martha.render.{Color, Point, Rect, Size}

case class BracketElement(
  frame: Rect,
  notes: Int = 3,
  lineWidth: Float = 1,
  beamHeight: Float = 5,
  fontSize: Float = 16,
  rightDown: Boolean = false,
  inverted: Boolean = false,
  color: Color = ScoreElementBase.defaultColor) extends ScoreElementBase {

  val element: scala.xml.Elem = {
    val Size(w, h) = frame.size

    val textRectSize = Size(fontSize, fontSize)
    val textRectOrigin = Point(
      w/2 - textRectSize.width/2,
      if (inverted) Seq(h/2 - fontSize/2 + fontSize, h - fontSize).min else Seq(h/2 - fontSize/2 - fontSize, 0).max)
    val textRect = Rect(textRectOrigin, textRectSize)

    def beamPath(beamStart: Point, beamEnd: Point): String = {
      val expr = LinearExpr(beamStart, beamEnd)
      if (expr.intersects(textRect)) {
        s"L ${beamStart.x} ${beamStart.y} L ${textRect.topLeft.x} ${expr(textRect.topLeft.x)} M ${textRect.topRight.x} ${expr(textRect.topRight.x)} L ${beamEnd.x} ${beamEnd.y}"
      } else {
        s"L ${beamStart.x} ${beamStart.y} L ${beamEnd.x} ${beamEnd.y}"
      }
    }

    val bracket =
      if (inverted) {
        if (h * 0.8 < fontSize) {
            <path
            fill="none"
            stroke-width={ s"${lineWidth}" }
            stroke={color.hexRGB}
            d={ s"M ${lineWidth} ${lineWidth} ${beamPath(Point(lineWidth, lineWidth + beamHeight), Point(w - lineWidth, lineWidth + beamHeight))} L ${w - lineWidth} ${lineWidth}" }
            />
        } else if (rightDown) {
            <path
            fill="none"
            stroke-width={ s"${lineWidth}" }
            stroke={color.hexRGB}
            d={ s"M ${lineWidth} ${lineWidth} ${beamPath(Point(lineWidth, lineWidth + beamHeight), Point(w - lineWidth, h - lineWidth))} L ${w - lineWidth} ${h - beamHeight - lineWidth}" }
            />
        } else {
            <path
            fill="none"
            stroke-width={ s"${lineWidth}" }
            stroke={color.hexRGB}
            d={ s"M ${lineWidth} ${h - beamHeight - lineWidth} ${beamPath(Point(lineWidth, h - lineWidth), Point(w - lineWidth, beamHeight + lineWidth))} L ${w - lineWidth} ${lineWidth}" }
            />
        }
      } else {
        if (h * 0.8 < fontSize) {
            <path
            fill="none"
            stroke-width={ s"${lineWidth}" }
            stroke={color.hexRGB}
            d={ s"M ${lineWidth} ${h} ${beamPath(Point(lineWidth, h - beamHeight - lineWidth), Point(w - lineWidth, h - beamHeight - lineWidth))} L ${w - lineWidth} ${h}" }
            />
        } else if (rightDown) {
            <path
            fill="none"
            stroke-width={ s"${lineWidth}" }
            stroke={color.hexRGB}
            d={ s"M ${lineWidth} ${beamHeight + lineWidth} ${beamPath(Point(lineWidth, lineWidth), Point(w - lineWidth, h - beamHeight - lineWidth))} L ${w - lineWidth} ${h}" }
            />
        } else {
            <path
            fill="none"
            stroke-width={ s"${lineWidth}" }
            stroke={color.hexRGB}
            d={ s"M ${lineWidth} ${h} ${beamPath(Point(lineWidth, h - beamHeight - lineWidth), Point(w - lineWidth, lineWidth))} L ${w - lineWidth} ${beamHeight + lineWidth}" }
            />
        }
      }

    val text =
      <text x={s"${textRectOrigin.x + textRectSize.width/2}"}
            y={s"${textRectOrigin.y + textRectSize.height}"}
            font-family="Baskerville-BoldItalic"
            font-size={ s"${fontSize}" }
            text-anchor="middle"
            stroke="none"
            fill={color.hexRGB}>
        {s"${notes}"}
      </text>

    val g =
      <g transform={s"translate(${frame.x}, ${frame.y})"}>
        {bracket}
        {text}
      </g>

    g
  }

  def withFrame(frame: Rect): ScoreElementBase = this.copy(frame = frame)
}
