package com.mayreh.martha.core

sealed abstract class KeySignature

object KeySignature {
  case object Sharp1 extends KeySignature
  case object Sharp2 extends KeySignature
  case object Sharp3 extends KeySignature
  case object Sharp4 extends KeySignature
  case object Sharp5 extends KeySignature
  case object Sharp6 extends KeySignature
  case object Sharp7 extends KeySignature

  case object Zero extends KeySignature

  case object Flat1 extends KeySignature
  case object Flat2 extends KeySignature
  case object Flat3 extends KeySignature
  case object Flat4 extends KeySignature
  case object Flat5 extends KeySignature
  case object Flat6 extends KeySignature
  case object Flat7 extends KeySignature
}
