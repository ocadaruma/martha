package com.mayreh.martha.abc

import com.mayreh.martha.core.Metadata._
import com.mayreh.martha.core.NoSound._
import com.mayreh.martha.core._
import org.scalatest.FlatSpec

class ABCParserTest extends FlatSpec {

  val parser = new ABCParser

  it should "parse song correctly" in {

    val song = scala.io.Source.fromInputStream(getClass.getClassLoader.getResourceAsStream("song.txt")).mkString

    val result = parser.parse(song)

    val tune = result.right.get

    val header = tune.tuneHeader

    assert(header.reference == Some(Reference(1)))
    assert(header.tuneTitle == Some(TuneTitle("Zocharti Loch")))
    assert(header.composer == Some(Composer("Louis Lewandowski (1821-1894)")))
    assert(header.meter == Meter(4, 4))
    assert(header.tempo == Tempo(76, NoteLength(1, 4)))

    assert(header.voiceHeaders(0) == VoiceHeader("T1", Clef.Treble))
    assert(header.voiceHeaders(1) == VoiceHeader("B1", Clef.Bass))

    assert(header.key == Key(KeySignature.Flat2, Octave(-1)))
    assert(header.unitNoteLength == UnitNoteLength(UnitDenominator.Quarter))

    val voice1 = tune.tuneBody.voices(0)
    val expectedVoice1 = Voice(
      "T1",
      Seq(
        Space,
        SlurStart,
        Note(NoteLength(2, 1), Pitch(PitchName.B, None, Octave(0)), Nil),
        Note(NoteLength(2, 1), Pitch(PitchName.C, None, Octave(1)), Nil),
        Space,
        Note(NoteLength(2, 1), Pitch(PitchName.D, None, Octave(1)), Nil),
        Note(NoteLength(2, 1), Pitch(PitchName.G, None, Octave(1)), Nil),
        SlurEnd,
        Space,
        BarLine(Nil),
        Space,
        Note(NoteLength(6, 1), Pitch(PitchName.F, None, Octave(1)), Nil),
        Note(NoteLength(2, 1), Pitch(PitchName.E, None, Octave(1)), Nil),
        Space,
        BarLine(Nil),
        LineBreak,
        Space,
        SlurStart,
        Note(NoteLength(2, 1), Pitch(PitchName.B, None, Octave(0)), Nil),
        Note(NoteLength(2, 1), Pitch(PitchName.C, None, Octave(1)), Nil),
        Space,
        Note(NoteLength(2, 1), Pitch(PitchName.D, None, Octave(1)), Nil),
        Note(NoteLength(2, 1), Pitch(PitchName.G, None, Octave(1)), Nil),
        SlurEnd,
        Space,
        BarLine(Nil),
        Space,
        Note(NoteLength(8, 1), Pitch(PitchName.F, None, Octave(1)), Nil),
        Space,
        BarLine(Nil),
        LineBreak,
      )
    )

    assert(voice1 == expectedVoice1)

    val voice2 = tune.tuneBody.voices(1)
    val expectedVoice2 = Voice(
      "B1",
      Seq(
        Space,
        Rest(NoteLength(8, 1), Nil),
        Space,
        BarLine(Nil),
        Space,
        Rest(NoteLength(2, 1), Nil),
        Note(NoteLength(2, 1), Pitch(PitchName.F, None, Octave(1)), Nil),
        Space,
        Note(NoteLength(2, 1), Pitch(PitchName.G, None, Octave(1)), Nil),
        Note(NoteLength(2, 1), Pitch(PitchName.A, None, Octave(1)), Nil),
        Space,
        BarLine(Nil),
        LineBreak,
        Space,
        SlurStart,
        Note(NoteLength(2, 1), Pitch(PitchName.D, None, Octave(1)), Nil),
        Note(NoteLength(2, 1), Pitch(PitchName.F, None, Octave(1)), Nil),
        Space,
        Note(NoteLength(2, 1), Pitch(PitchName.B, None, Octave(1)), Nil),
        Note(NoteLength(2, 1), Pitch(PitchName.E, None, Octave(2)), Nil),
        SlurEnd,
        Space,
        BarLine(Nil),
        Space,
        Note(NoteLength(8, 1), Pitch(PitchName.D, None, Octave(2)), Nil),
        Space,
        BarLine(Nil),
        LineBreak,
      )
    )

    assert(voice2 == expectedVoice2)
  }

  it should "parse annotation" in {

    val song = scala.io.Source.fromInputStream(getClass.getClassLoader.getResourceAsStream("annotation.txt")).mkString

    val result = parser.parse(song)

    val tune = result.right.get

    val header = tune.tuneHeader

    val voice = tune.tuneBody.voices(0)

    val expectedElements = Seq(
      Note(NoteLength(4, 1), Pitch(PitchName.A, None, Octave(0)), Seq(
        Annotation(AnnotationPlacement.Left, "left"),
        Annotation(AnnotationPlacement.Right, "right"),
        Annotation(AnnotationPlacement.Above, "above"),
        Annotation(AnnotationPlacement.Below, "below"),
        Annotation(AnnotationPlacement.Hidden, "hidden")
      )),
      Space,
      BarLine(Nil),
      LineBreak,
      Chord(NoteLength(2, 1),
        Seq(Pitch(PitchName.A, None, Octave(0)), Pitch(PitchName.C, None, Octave(1)), Pitch(PitchName.E, None, Octave(1))),
        Seq(Annotation(AnnotationPlacement.Hidden, "chord"))
      ),
      Space,
      Rest(NoteLength(2, 1),
        Seq(Annotation(AnnotationPlacement.Hidden, "rest"))
      ),
      Space,
      BarLine(Seq(Annotation(AnnotationPlacement.Hidden, "bar"))),
      LineBreak,
      Rest(NoteLength(4, 1), Nil),
      Space,
      DoubleBarLine(Seq(Annotation(AnnotationPlacement.Hidden, "double_bar"))),
      LineBreak
    )

    assert(voice.elements == expectedElements)
  }
}
