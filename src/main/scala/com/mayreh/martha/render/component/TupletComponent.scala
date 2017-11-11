package com.mayreh.martha.render.component

import com.mayreh.martha.render.element.{BracketElement, ScoreElementBase}

sealed abstract class TupletComponentMember {
  type T <: ComponentBase
  def component: T
}
object TupletComponentMember {
  case class Note(component: NoteComponent) extends TupletComponentMember { type T = NoteComponent }
  case class Rest(component: RestComponent) extends TupletComponentMember { type T = RestComponent }
  case class Beam(component: BeamComponent) extends TupletComponentMember { type T = BeamComponent }
}

case class TupletComponentMemberWithX(x: Float, member: TupletComponentMember)

case class TupletComponent(
  components: Seq[TupletComponentMember],
  bracket: BracketElement,
  inverted: Boolean
) extends ComponentBase {

  val elements: Seq[ScoreElementBase] = components.flatMap(_.component.elements) :+ bracket
}
