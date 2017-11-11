package com.mayreh.martha.render.component

import com.mayreh.martha.render.element.{EllipseElement, ScoreElementBase}

case class RestComponent(
  dots: Seq[EllipseElement],
  restElement: ScoreElementBase,
  x: Float
) extends ComponentBase {

  val elements: Seq[ScoreElementBase] = restElement +: dots
}
