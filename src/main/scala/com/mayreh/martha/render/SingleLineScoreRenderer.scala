package com.mayreh.martha.render

import com.mayreh.martha.core.{Octave, Pitch, PitchName, UnitDenominator}

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
}
