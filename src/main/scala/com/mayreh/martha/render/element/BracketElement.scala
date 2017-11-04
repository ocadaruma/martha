package com.mayreh.martha.render.element

import com.mayreh.martha.render.{Point, Size}

/**
 * TODO: Clip text-rect.
 */
class BracketElement(
  size: Size,
  notes: Int = 3,
  lineWidth: Float = 1,
  beamHeight: Float = 5,
  fontSize: Float = 16,
  rightDown: Boolean = false,
  inverted: Boolean = false) {

  val element: scala.xml.Elem = {
    val Size(w, h) = size

    val textRectSize = Size(fontSize, fontSize)
    val textRectOrigin = Point(
      w/2 - textRectSize.width/2,
      if (inverted) Seq(h/2 - fontSize/2 + fontSize, h - fontSize).min else Seq(h/2 - fontSize/2 - fontSize, 0).max)



    val bracket =
      if (inverted) {
        if (h * 0.8 < fontSize) {
            <path
            d={
            s"M ${lineWidth} ${lineWidth} L ${lineWidth} ${lineWidth + beamHeight} L ${w - lineWidth} ${lineWidth + beamHeight} L ${w - lineWidth} ${lineWidth}".stripMargin }
            fill="none"
            stroke-width={ s"${lineWidth}" }
            stroke="black"
            />
        } else if (rightDown) {
            <path
            d={ s"M ${lineWidth} ${lineWidth} L ${lineWidth} ${lineWidth + beamHeight} L ${w - lineWidth} ${h - lineWidth} L ${w - lineWidth} ${h - beamHeight - lineWidth}" }
            fill="none"
            stroke-width={ s"${lineWidth}" }
            stroke="black"
            />
        } else {
            <path
            d={ s"M ${lineWidth} ${h - beamHeight - lineWidth} L ${lineWidth} ${h - lineWidth} L ${w - lineWidth} ${beamHeight + lineWidth} L ${w - lineWidth} ${lineWidth}" }
            fill="none"
            stroke-width={ s"${lineWidth}" }
            stroke="black"
            />
        }
      } else {
        if (h * 0.8 < fontSize) {
            <path
            d={ s"M ${lineWidth} ${h} L ${lineWidth} ${h - beamHeight - lineWidth} L ${w - lineWidth} ${h - beamHeight - lineWidth} L ${w - lineWidth} ${h}" }
            fill="none"
            stroke-width={ s"${lineWidth}" }
            stroke="black"
            />
        } else if (rightDown) {
            <path
            d={ s"M ${lineWidth} ${beamHeight + lineWidth} L ${lineWidth} ${lineWidth} L ${w - lineWidth} ${h - beamHeight - lineWidth} L ${w - lineWidth} ${h}" }
            fill="none"
            stroke-width={ s"${lineWidth}" }
            stroke="black"
            />
        } else {
            <path
            d={ s"M ${lineWidth} ${h} L ${lineWidth} ${h - beamHeight - lineWidth} L ${w - lineWidth} ${lineWidth} L ${w - lineWidth} ${beamHeight + lineWidth}" }
            fill="none"
            stroke-width={ s"${lineWidth}" }
            stroke="black"
            />
        }
      }

    val text =
      <text x={s"${textRectOrigin.x + textRectSize.width/2}"} y={s"${textRectOrigin.y + textRectSize.height}"} font-family="Baskerville-BoldItalic" font-size={ s"${fontSize}" } text-anchor="middle">
        {s"${notes}"}
      </text>

    val g =
      <g>
        {bracket}
        {text}
      </g>

    g
  }
}
