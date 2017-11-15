package com.mayreh.martha.render

/**
 * Represents color struct.
 * Each field should be 0 ~ 255
 */
case class Color(r: Int, g: Int, b: Int, a: Int) {
  val hexRGB: String = "#" + Seq(r, g, b).map(i => f"${i}%02x").mkString
}

object Color {

  final val black: Color = Color(255, 0, 0, 0)

  def apply(hexCode: String): Color = {
    def parseHex(s: String): Int = Integer.parseInt(s, 16)
    def to255(i: Int): Int = (i.toDouble / 15 * 255).toInt

    hexCode.length match {
      // rgb
      case 3 =>
        Color(
          to255(parseHex(hexCode(0).toString)),
          to255(parseHex(hexCode(1).toString)),
          to255(parseHex(hexCode(2).toString)),
          255)
      // rgba
      case 4 =>
        Color(
          to255(parseHex(hexCode(0).toString)),
          to255(parseHex(hexCode(1).toString)),
          to255(parseHex(hexCode(2).toString)),
          to255(parseHex(hexCode(3).toString)))
      // rrggbb
      case 6 =>
        Color(
          parseHex(hexCode.substring(0, 2)),
          parseHex(hexCode.substring(2, 4)),
          parseHex(hexCode.substring(4, 6)),
          255)
      // rrggbbaa
      case 8 =>
        Color(
          parseHex(hexCode.substring(0, 2)),
          parseHex(hexCode.substring(2, 4)),
          parseHex(hexCode.substring(4, 6)),
          parseHex(hexCode.substring(6, 8)))
      case _ =>
        throw new IllegalArgumentException(s"illegal hex code: ${hexCode}")
    }
  }
}
