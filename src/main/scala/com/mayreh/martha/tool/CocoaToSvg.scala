package com.mayreh.martha.tool

import fastparse.all._

object CocoaToSvg {

  val whitespace = CharIn(" \t\n").rep

  val alpha = CharIn(('a' to 'z') ++ ('A' to 'Z'))

  val numeric = CharIn('0' to '9')

  val id = alpha ~ (alpha | numeric).rep

  val float = ("-".? ~ (numeric.rep(1) ~ "." ~ numeric.rep(1)) | numeric.rep(1)).!.map(_.toFloat)

  val expr = (( ("y + " | "x + ").!.? ~ ("w" | "h2" | "h").! ~ whitespace ~ "*" ~ whitespace).? ~ float).map { case (i, f) =>
    i match {
      case Some(j) =>
        j._1.getOrElse("") + "${ " + j._2 + s" * $f }"
      case _ =>
        f.toString
    }
  }

  val pathStart = "let" ~ whitespace ~ "path" ~ whitespace ~ "=" ~ whitespace ~ "UIBezierPath()" ~ whitespace
  val moveToPoint = ("path.moveToPoint(CGPointMake(" ~ whitespace ~ expr ~ whitespace ~ "," ~ whitespace ~ expr ~ whitespace ~ ")" ~ whitespace ~ ")")
    .map { case (x, y) =>
      s"M $x $y"
    }
  val addCurveToPoint = ("path.addCurveToPoint(CGPointMake(" ~ whitespace ~ expr ~ whitespace ~ "," ~ whitespace ~ expr ~ ")" ~ whitespace ~ "," ~ whitespace ~
    "controlPoint1:" ~ whitespace ~ "CGPointMake(" ~ whitespace ~ expr ~ whitespace ~ "," ~ whitespace ~ expr ~ ")" ~ whitespace ~ "," ~ whitespace ~
    "controlPoint2:" ~ whitespace ~ "CGPointMake(" ~ whitespace ~ expr ~ whitespace ~ "," ~ whitespace ~ expr ~ ")" ~ whitespace ~ ")" ~ whitespace)
    .map { case (x, y, c1x, c1y, c2x, c2y) =>
      s"C $c1x $c1y, $c2x $c2y, $x $y"
    }
  val addLineToPoint = ("path.addLineToPoint(CGPointMake(" ~ whitespace ~ expr ~ whitespace ~ "," ~ whitespace ~ expr ~ ")" ~ whitespace ~ ")" ~ whitespace)
    .map { case (x, y) =>
      s"L $x $y"
    }

  val bezier = (pathStart ~ whitespace ~ moveToPoint ~ whitespace ~ (addCurveToPoint | addLineToPoint).rep).map { case (m, cs) =>
    (m +: cs).mkString("\n")
  }

  def parse(input: String) = bezier.parse(input)
}
