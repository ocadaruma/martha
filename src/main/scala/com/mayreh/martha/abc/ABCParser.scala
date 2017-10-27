package com.mayreh.martha.abc

import com.mayreh.martha.core.Metadata._
import com.mayreh.martha.core._
import fastparse.core.Parsed.{Failure, Success}

import scala.collection.{mutable => mu}

class ABCParseError

class ABCParser {
  import fastparse.all._

  val nl = "\n".rep

  val anyChar = AnyChar.!.filter(_ != "\n")

  val alphanumeric = CharIn(('0' to '9') ++ ('a' to 'z') ++ ('A' to 'Z'))

  val whitespace = (" " | "\t").rep

  val number = CharIn('0' to '9').rep(1).!.map(_.toInt)

  val reference = ("X:" ~ whitespace ~ number ~ nl).map(Reference)

  val tuneTitle = ("T:" ~ whitespace ~ anyChar.rep(1).! ~ nl).map(TuneTitle)

  val composer = ("C:" ~ whitespace ~ anyChar.rep(1).! ~ nl).map(Composer)

  val meter = {
    val fraction = (number ~ "/" ~ number).map { case (num, denom) => Meter(num, denom) }
    val cHalf = P("C|").map(_ => Meter(2, 2))
    val c = P("C").map(_ => Meter(4, 4))
    "M:" ~ whitespace ~ (fraction | cHalf | c) ~ nl
  }

  val unitNoteLength = ("L:" ~ whitespace ~ "1/" ~ number ~ nl).map { num =>
    UnitNoteLength(UnitDenominator.valueOf(num).getOrElse(UnitDenominator.Quarter))
  }

  val tempo = ("Q:" ~ whitespace ~ number ~ "/" ~ number ~ "=" ~ number ~ nl).map { case (num, denom, bpm) =>
    Tempo(bpm, NoteLength(num, denom))
  }

  val key = {
    val octave = "octave=" ~ ("-" ~ CharIn('0' to '9').rep(1)).!.map(_.toInt)
    ("K:" ~ whitespace ~ (CharIn("ABCDEFG") ~ CharIn("#b").? ~ "m".?).! ~ whitespace ~ octave.? ~ nl).map { case (k, o) =>
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

  val voiceHeader = ("V:" ~ whitespace ~ alphanumeric.rep(1).! ~ whitespace ~ ("clef=" ~ ("treble" | "bass").!).? ~ nl).map { case (id, clefName) =>
    VoiceHeader(id, clefName match {
      case Some("bass") => Clef.Bass
      case _ => Clef.Treble
    })
  }

  val doubleBarLine = P("||").map[MusicalElement](_ => NoSound.DoubleBarLine)

  val repeatStart = P("|:").map[MusicalElement](_ => NoSound.DoubleBarLine)

  val repeatEnd = P(":|").map[MusicalElement](_ => NoSound.DoubleBarLine)

  val barLine = P("|").map[MusicalElement](_ => NoSound.BarLine)

  val slurStart = P("(").map[MusicalElement](_ => NoSound.SlurStart)

  val slurEnd = P(")").map[MusicalElement](_ => NoSound.SlurEnd)

  val space = P(" " | "\t").rep(1).map[MusicalElement](_ => NoSound.Space)

  val tie = P("-").map[MusicalElement](_ => NoSound.Tie)

  val lineBreak = P("\n").map[MusicalElement](_ => NoSound.LineBreak)

  val voiceId = P("[V:" ~ whitespace ~ alphanumeric.rep(1).! ~ "]").map(VoiceId)

  val pitch = {
    val accidental = ("^^" | "^" | "=" | "_" | "__").!.?.map {
      case Some("^^") => Some(Accidental.DoubleSharp)
      case Some("^") => Some(Accidental.Sharp)
      case Some("=") => Some(Accidental.Natural)
      case Some("_") => Some(Accidental.Flat)
      case Some("__") => Some(Accidental.DoubleFlat)
      case _ => None
    }

    val octave = CharIn("',").rep.!.map { o =>
      o.count(_ == ''') - o.count(_ == ',')
    }

    (accidental ~ CharIn("abcdefgABCDEFG").! ~ octave).map { case (a, p, o) =>
      val n = p match {
        case "C" | "c" => PitchName.C
        case "D" | "d" => PitchName.D
        case "E" | "e" => PitchName.E
        case "F" | "f" => PitchName.F
        case "G" | "g" => PitchName.G
        case "A" | "a" => PitchName.A
        case "B" | "b" => PitchName.B
      }
      Pitch(n, a, Octave(o + (if (p.toLowerCase == p) 1 else 0)))
    }
  }

  val noteLength = {
    (number.? ~ "/" ~ number).map { case (num, denom) =>
      NoteLength(num.getOrElse(1), denom)
    }
  } | {
    (number.? ~ "/".rep.!).map { case (num, div) =>
      var denom = 1
      (1 to div.count(_ == '/')).foreach(_ => denom *= 2)
      NoteLength(num.getOrElse(1), denom)
    }
  }

  val note = (pitch ~ noteLength).map { case (p, l) =>
    Note(l, p)
  }

  val rest = ("z" ~ noteLength).map(Rest)

  val multiMeasureRest = ("Z" ~ number.?).map { n => MultiMeasureRest(n.getOrElse(1)) }

  val chord = ("[" ~ pitch.rep(1) ~ "]" ~ noteLength).map { case (pitches, length) =>
    Chord(length, pitches)
  }

  val tuplet = ("(" ~ CharIn('2' to '9').!).map(_.toInt).flatMap { n =>
    (chord | note | rest).rep(n).map { elems =>
      Tuplet(notes = n, inTimeOf = None, elems)
    }
  }

  val comment = P("%") ~ AnyChar.rep

  val header = reference |
    tuneTitle |
    composer |
    meter |
    unitNoteLength |
    tempo |
    key |
    voiceHeader

  val element = tuplet |
    doubleBarLine |
    repeatStart |
    repeatEnd |
    barLine |
    slurStart |
    slurEnd |
    tie |
    lineBreak |
    space |
    voiceId |
    note |
    rest |
    multiMeasureRest |
    chord

  def buildTuneHeader(headers: Seq[Metadata]): TuneHeader = {
    val zero = TuneHeader(
      reference = None,
      tuneTitle = None,
      composer = None,
      meter = Metadata.Meter(4, 4),
      unitNoteLength = Metadata.UnitNoteLength(UnitDenominator.Quarter),
      tempo = Metadata.Tempo(120, NoteLength(1, 1)),
      key = Metadata.Key(KeySignature.Zero, octave = Octave(0)),
      voiceHeaders = Vector.empty
    )

    headers.foldLeft(zero) { case (acc, m) =>
      m match {
        case r: Metadata.Reference => acc.copy(reference = Some(r))
        case t: Metadata.TuneTitle => acc.copy(tuneTitle = Some(t))
        case c: Metadata.Composer => acc.copy(composer = Some(c))
        case m: Metadata.Meter => acc.copy(meter = m)
        case u: Metadata.UnitNoteLength => acc.copy(unitNoteLength = u)
        case t: Metadata.Tempo => acc.copy(tempo = t)
        case k: Metadata.Key => acc.copy(key = k)
        case v: Metadata.VoiceHeader => acc.copy(voiceHeaders = acc.voiceHeaders :+ v)
      }
    }
  }

  def buildTuneBody(elements: Seq[MusicalElement]): TuneBody = {

    val voiceIdElmentsMap = mu.LinkedHashMap.empty[String, mu.ListBuffer[MusicalElement]]
    var currentVoiceid = "__defaultVoiceId"
    voiceIdElmentsMap(currentVoiceid) = mu.ListBuffer.empty

    elements.foreach {
      case i: VoiceId =>
        currentVoiceid = i.id
      case e =>
        voiceIdElmentsMap.getOrElseUpdate(currentVoiceid, mu.ListBuffer.empty) += e
    }

    voiceIdElmentsMap -= "__defaultVoiceId"

    val voices = voiceIdElmentsMap.foldLeft(Vector.empty[Voice]) { case (acc, (id, elems)) =>
      acc :+ Voice(id, elems)
    }

    TuneBody(voices)
  }

  def parse(input: String): Either[ABCParseError, Tune] = {

    val headers = mu.ListBuffer.empty[Metadata]
    val elements = mu.ListBuffer.empty[MusicalElement]

    def parseLine(line: String) = {
      val start = comment.rep.parse(line).index

      header.parse(line, start) match {
        case Success(m, _) =>
          headers += m
        case Failure(_, i, _) =>
          element.rep.parse(line, i) match {
            case Success(elems, j) =>
              elements ++= elems
            case _ =>
          }
      }
    }

    input.linesWithSeparators.filter(_.trim.nonEmpty).foreach { line =>
      parseLine(line)
    }

    Right(Tune(buildTuneHeader(headers), buildTuneBody(elements)))
  }
}
