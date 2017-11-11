package com.mayreh.martha.core

sealed abstract class KeySignature
sealed trait FlatFamily
sealed trait SharpFamily

object KeySignature {
  case object Sharp1 extends KeySignature with SharpFamily
  case object Sharp2 extends KeySignature with SharpFamily
  case object Sharp3 extends KeySignature with SharpFamily
  case object Sharp4 extends KeySignature with SharpFamily
  case object Sharp5 extends KeySignature with SharpFamily
  case object Sharp6 extends KeySignature with SharpFamily
  case object Sharp7 extends KeySignature with SharpFamily

  case object Zero extends KeySignature

  case object Flat1 extends KeySignature with FlatFamily
  case object Flat2 extends KeySignature with FlatFamily
  case object Flat3 extends KeySignature with FlatFamily
  case object Flat4 extends KeySignature with FlatFamily
  case object Flat5 extends KeySignature with FlatFamily
  case object Flat6 extends KeySignature with FlatFamily
  case object Flat7 extends KeySignature with FlatFamily
}
