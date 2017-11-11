package com.mayreh.martha

import com.mayreh.martha.core._

package object render {

  implicit class RichSeq[A](val self: Seq[A]) extends AnyVal {

    /**
     * Denotes whether all pitches are separated or not.
     */
    def sparse(implicit ev: A =:= Pitch): Boolean = {
      self.sorted.sliding(2).forall {
        case Seq(l, r) => (l.step - r.step).abs > 1
        case _ => true
      }
    }

    def spanBy[B](f: A => B): Seq[Seq[A]] = {
      import scala.collection.mutable.ListBuffer

      var result = ListBuffer.empty[Seq[A]]

      var acc = ListBuffer.empty[A]
      for (e <- self) {
        acc.headOption.fold {
          acc += e
        } { head =>
          if (f(e) == f(head)) {
            acc += e
          } else {
            result += acc.toList
            acc = ListBuffer(e)
          }
        }
      }

      if (acc.nonEmpty) { result += acc.toList }

      result.toList
    }
  }

  implicit class RichUnitDenominator(val self: UnitDenominator) extends AnyVal {

    /**
     * Denotes whether the note should be rendered with stem or not.
     */
    def renderStem: Boolean = self != UnitDenominator.Whole

    /**
     * Denotes whether the note should be rendered with tail or not.
     */
    def renderTail: Boolean = self match {
      case UnitDenominator.Whole | UnitDenominator.Half | UnitDenominator.Quarter => false
      case _ => true
    }
  }

  implicit class RichBeamMember(val self: BeamMember) extends AnyVal {

    def maxPitch: Pitch = {
      self match {
        case note: Note => note.pitch
        case chord: Chord => chord.pitches.maxBy(_.step)
        case _ => throw new RuntimeException(s"unsupported member type: ${self}")
      }
    }

    def minPitch: Pitch = {
      self match {
        case note: Note => note.pitch
        case chord: Chord => chord.pitches.minBy(_.step)
        case _ => throw new RuntimeException(s"unsupported member type: ${self}")
      }
    }

    def sortedPitches: Seq[Pitch] = {
      self match {
        case note: Note => Seq(note.pitch)
        case chord: Chord => chord.pitches.sortBy(_.step)
        case _ => Nil
      }
    }
  }
}
