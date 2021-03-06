package com.mayreh.martha.render.element

import com.mayreh.martha.render.{Color, Rect}

import scala.xml.Elem

trait ScoreElementBase {

  def frame: Rect

  def element: Elem

  def color: Color

  def withFrame(frame: Rect): ScoreElementBase
}

object ScoreElementBase {

  final val defaultColor: Color = Color("aaa")
}
