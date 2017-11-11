package com.mayreh.martha.render.component

import com.mayreh.martha.render.element.{BeamElement, ScoreElementBase}

case class BeamComponent(
  noteComponents: Seq[NoteComponent],
  beams: Seq[BeamElement],
  inverted: Boolean
) extends ComponentBase {

  val elements: Seq[ScoreElementBase] = noteComponents.flatMap(_.elements) ++ beams
}
