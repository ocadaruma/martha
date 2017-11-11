package com.mayreh.martha.render.element

import com.mayreh.martha.render.Rect

import scala.xml.Elem

trait ScoreElementBase {

  def frame: Rect

  def element: Elem
}
