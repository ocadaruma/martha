package com.mayreh.martha.render.component

import com.mayreh.martha.render.element.ScoreElementBase

trait ComponentBase {
  def elements: Seq[ScoreElementBase]
}
