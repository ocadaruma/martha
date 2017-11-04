package com.mayreh.martha.render.element

import com.mayreh.martha.render.Size

class FlagEighthElement(size: Size, inverted: Boolean = false) {

  val element: scala.xml.Elem = {
    val Size(w, h) = size

      <path
      d={s"""M ${w * 0.00157} ${h * 2.7E-4}
            |C ${w * 0.12962} ${h * 0.18935667}, ${w * 0.85393} ${h * 0.39008668}, ${w * 0.98198} ${h * 0.57917666}
            |C ${w * 1.02345} ${h * 0.64041}, ${w * 1.02345} ${h * 0.8796}, ${w * 0.67964} ${h * 1.00156}
            |L ${w * 0.6232} ${h * 1.0016567}
            |C ${w * 0.79895} ${h * 0.9308633}, ${w * 0.94747} ${h * 0.80686}, ${w * 0.91226} ${h * 0.69606}
            |C ${w * 0.87704} ${h * 0.5852567}, ${w * 0.85393} ${h * 0.45594665}, ${w * 0.0012} ${h * 0.36567333}""".stripMargin}
      stroke="transparent"
      fill="black"
      transform={if (inverted) s"translate(0, $h) scale(1, -1)" else "translate(0, 0) scale(1, 1)"}
      />
  }
}
