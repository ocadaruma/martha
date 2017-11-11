package com.mayreh.martha.render.component

import com.mayreh.martha.core.Pitch
import com.mayreh.martha.render.element.{EllipseElement, RectElement, ScoreElementBase}

case class PitchWithElement(pitch: Pitch, noteHead: ScoreElementBase)

case class NoteComponent(
  dots: Seq[EllipseElement],
  pitches: Seq[PitchWithElement],
  accidentals: Seq[ScoreElementBase],
  fillingStaff: Seq[ScoreElementBase],
  stem: Option[RectElement],
  tail: Option[ScoreElementBase],
  inverted: Boolean,
  x: Float
) extends ComponentBase {

  val elements: Seq[ScoreElementBase] =
    pitches.map(_.noteHead) ++ accidentals ++ fillingStaff ++ dots ++ stem ++ tail
}
