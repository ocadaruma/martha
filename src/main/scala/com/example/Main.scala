package com.example

import java.io.{FileOutputStream, PrintWriter}

import com.mayreh.martha.abc.ABCParser
import com.mayreh.martha.render.{Rect, SingleLineScore}

object Main {

  def main(args: Array[String]): Unit = {

    val Array(abcFile, outSvg) = args

    val parser = new ABCParser
    val fileContent = io.Source.fromFile(abcFile).mkString

    val tune = parser.parse(fileContent).right.get

    val score = new SingleLineScore(Rect(0, 0, 10240, 250))
    score.loadVoice(tune.tuneHeader, tune.tuneHeader.voiceHeaders(0), tune.tuneBody.voices(0))

    val svg = score.render()

    val writer = new PrintWriter(new FileOutputStream(outSvg, false))
    try {
      writer.println(svg.mkString)
    } finally {
      writer.close()
    }
  }
}
