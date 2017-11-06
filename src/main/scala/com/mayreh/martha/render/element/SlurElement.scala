package com.mayreh.martha.render.element

import com.mayreh.martha.render.LocalPoint

case class SlurElement(
  start: LocalPoint,
  end: LocalPoint,
  inverted: Boolean = false,
  thickness: Float = 2) extends ScoreElementBase {

  val element: scala.xml.Elem = {

    val x1 = start.value.x
    val x2 = end.value.x

    val y1 = start.value.y
    val y2 = end.value.y

    val dx = x2 - x1
    val dy = y2 - y1

    val distance = math.sqrt(dx * dx + dy * dy)
    val ux = dx / distance
    val uy = dy / distance

    val flatten = distance / 3.5
    val curve = (if (inverted) -1 else 1) * Seq(25, Seq(4, flatten).max).min

    val controlx1 = x1 + flatten * ux - curve * uy
    val controly1 = y1 + flatten * uy + curve * ux
    val controlx2 = x2 - flatten * ux - curve * uy
    val controly2 = y2 - flatten * uy + curve * ux

      <path
      d={s"""M ${x1} ${y1}
            |C ${controlx1} ${controly1}, ${controlx2} ${controly2}, ${x2} ${y2}
            |C ${controlx2 - thickness * uy} ${controly2 + thickness * ux}, ${controlx1 - thickness * uy} ${controly1 + thickness * ux}, ${x1} ${y1}
            |Z""".stripMargin}
      stroke="none"
      fill="black"
      />
  }
}
