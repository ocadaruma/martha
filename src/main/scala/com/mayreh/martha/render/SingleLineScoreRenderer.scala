package com.mayreh.martha.render

import com.mayreh.martha.core._
import com.mayreh.martha.render.element.{EllipseElement, RectElement}

/**
 * Represents whether left or right stem should be rendered.
 *   |       O
 *   |  or  |
 *  O       |
 */
sealed abstract class NoteHeadColumn {
  def invert: NoteHeadColumn = this match {
    case NoteHeadColumn.Left => NoteHeadColumn.Right
    case NoteHeadColumn.Right => NoteHeadColumn.Left
  }
}
object NoteHeadColumn {
  case object Left extends NoteHeadColumn
  case object Right extends NoteHeadColumn
}

class SingleLineScoreRenderer(
  unitDenominator: UnitDenominator,
  layout: SingleLineScoreLayout,
  bounds: Rect,
) {

  private val staffTop: Float = (bounds.height - layout.staffHeight) / 2

  /**
   * -----------
   * -----------
   * -----o-----
   * -----------
   * -----------
   */
  private val BOn3rdStave: Pitch = Pitch(PitchName.B, None, Octave(0))

  /**    -o-
   * -----------
   * -----------
   * -----------
   * -----------
   * -----------
   */
  private val AAbove1stStave: Pitch = Pitch(PitchName.A, None, Octave(1))

  /**
   * -----------
   * -----------
   * -----------
   * -----------
   * -----------
   *     -o-
   */
  private val MiddleC: Pitch = Pitch(PitchName.C, None, Octave(0))

  private def noteHeadTop(pitch: Pitch): Float = {
    val noteInterval = layout.staffInterval / 2
    val c0 = staffTop + noteInterval * 9

    c0 - (7 * pitch.octave.value + pitch.pitchName.offset) * noteInterval
  }

  private def mkDotFrame(pitch: Pitch, x: Float): Rect = {
    val step = 7 * pitch.octave.value + pitch.pitchName.offset
    val noteInterval = layout.staffInterval / 2
    val y = staffTop + noteInterval * 9 - (step + (step + 1) % 2) * noteInterval
    val d = layout.staffLineWidth * 5

    Rect(x + layout.dotMarginLeft, y + noteInterval - d / 2, d, d)
  }

  private def mkDots(pitch: Pitch, x: Float, length: NoteLength, u: UnitDenominator): Seq[EllipseElement] = {
    val result = scala.collection.mutable.ListBuffer.empty[EllipseElement]
    val length1 = length.absoluteLength(u) - u.denominator
    val denom1 = u.denominator.toFloat / 2

    if (length1 >= denom1) {
      val dot1 = EllipseElement(mkDotFrame(pitch, x))
      result += dot1

      val length2 = length1 - denom1
      val denom2 = denom1 / 2
      if (length2 >= denom2) {
        result += EllipseElement(dot1.frame.withX(dot1.frame.x + dot1.frame.width + layout.staffLineWidth))
      }
    }

    result.toList
  }

  private def mkDotsForRest(x: Float, length: NoteLength, u: UnitDenominator): Seq[EllipseElement] =
    mkDots(Pitch(PitchName.C, None, Octave(1)), x, length, u)

  private def mkNoteHeadFrame(pitch: Pitch, x: Float): Rect = {
    val width = layout.noteHeadSize.width
    Rect(x, noteHeadTop(pitch), width, layout.noteHeadSize.height)
  }

  private def mkFillingStaff(x: Float, pitch: Pitch): Seq[RectElement] = {
    val step = pitch.step
    val upperBound = AAbove1stStave.step
    val lowerBound = MiddleC.step
    val frame = mkNoteHeadFrame(pitch, x)

    val staff = scala.collection.mutable.ListBuffer.empty[RectElement]

    val staveX = frame.x - (layout.fillingStaffLineXLength - frame.width) / 2
    if (step >= upperBound) {
      (1 to (1 + (step - upperBound).abs / 2)).foreach { i =>
        val y = staffTop - (i * layout.staffInterval)
        staff += RectElement(Rect(
          x, y, layout.fillingStaffLineXLength, layout.fillingStaffLineWidth))
      }
    } else if (step <= lowerBound) {
      (1 to (1 + (step - lowerBound).abs / 2)).foreach { i =>
        val y = staffTop + layout.staffHeight - layout.staffLineWidth + (i * layout.staffInterval)
        staff += RectElement(Rect(
          x, y, layout.fillingStaffLineXLength, layout.fillingStaffLineWidth))
      }
    }

    staff.toList
  }

  private def mkAccidental(pitch: Pitch, x: Float): Option[]
}
