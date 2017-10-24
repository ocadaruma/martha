package com.mayreh.martha.abc

import com.mayreh.martha.core.Metadata._
import com.mayreh.martha.core._

class ABCParseError

class ABCParser {
  import fastparse.all._

  val whitespace = (" " | "\t").rep

  val number = CharIn('0' to '9').rep(1).!.map(_.toInt)

  val reference = ("X:" ~ whitespace ~ number).map(Reference)

  val tuneTitle = ("T:" ~ whitespace ~ AnyChar.!).map(TuneTitle)

  val composer = ("C:" ~ whitespace ~ AnyChar.!).map(Composer)

  val meter = {
    val fraction = (number ~ "/" ~ number).map { case (num, denom) => Meter(num, denom) }
    val cHalf = P("C|").map(_ => Meter(2, 2))
    val c = P("C").map(_ => Meter(4, 4))
    "M:" ~ whitespace ~ fraction | cHalf | c
  }

  val unitNoteLength = ("L:" ~ whitespace ~ "1/" ~ number).map { num =>
    UnitNoteLength(UnitDenominator.valueOf(num).getOrElse(UnitDenominator.Quarter))
  }

  val tempo = ("Q:" ~ whitespace ~ number ~ "/" ~ number ~ "=" ~ number).map { case (num, denom, bpm) =>
    Tempo(bpm, NoteLength(num, denom))
  }

  val key = {
    val octave = "octave=" ~ ("-" ~ CharIn('0' to '9').rep(1)).!.map(_.toInt)
    ("K:" ~ whitespace ~ (CharIn("ABCDEFG") ~ CharIn("#b").? ~ "m".?).! ~ whitespace ~ octave.?).map { case (k, o) =>
      val keySignature = k match {
        case "C" | "Am" => KeySignature.Zero
        case "G" | "Em" => KeySignature.Sharp1
        case "D" | "Bm" => KeySignature.Sharp2
        case "A" | "F#m" => KeySignature.Sharp3
        case "E" | "C#m" => KeySignature.Sharp4
        case "B" | "G#m" => KeySignature.Sharp5
        case "F#" | "D#m" => KeySignature.Sharp6
        case "C#" | "A#m" => KeySignature.Sharp7
        case "F" | "Dm" => KeySignature.Flat1
        case "Bb" | "Gm" => KeySignature.Flat2
        case "Eb" | "Cm" => KeySignature.Flat3
        case "Ab" | "Fm" => KeySignature.Flat4
        case "Db" | "Bbm" => KeySignature.Flat5
        case "Gb" | "Ebm" => KeySignature.Flat6
        case "Cb" | "Abm" => KeySignature.Flat7
        case _ => KeySignature.Zero
      }

      Key(keySignature, Octave(o.getOrElse(0)))
    }
  }

  def parse(input: String): Either[ABCParseError, TuneHeader] = {

//    val x = P("").!
//    val reference = (P("") ~ P("").! ~ P("").! ~ P("")).map { s =>  }

//    CharIn('0' to '9')
  }
}
