package com.mayreh.martha.core

sealed abstract class AnnotationPlacement
object AnnotationPlacement {
  case object Above extends AnnotationPlacement
  case object Below extends AnnotationPlacement
  case object Left extends AnnotationPlacement
  case object Right extends AnnotationPlacement
  case object Hidden extends AnnotationPlacement
}

case class Annotation(placement: AnnotationPlacement, value: String)
