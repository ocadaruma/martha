package com.mayreh.martha.render.component

import com.mayreh.martha.render.element.ScoreElementBase

trait ComponentBase {
  def elements: Seq[ScoreElementBase]

  def toNoteComponents: Seq[NoteComponent] = {
    this match {
      case n: NoteComponent =>
        Seq(n)
      case b: BeamComponent =>
        b.noteComponents
      case t: TupletComponent =>
        t.components.flatMap {
          case TupletComponentMember.Note(n) => Seq(n)
          case TupletComponentMember.Rest(r) => Nil
          case TupletComponentMember.Beam(b) => b.noteComponents
          case _ => Nil
        }
    }
  }
}
