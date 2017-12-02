package com.mayreh.martha.tool.unity

import java.io.{File, FileOutputStream, PrintWriter}

import com.mayreh.martha.abc.ABCParser
import com.mayreh.martha.render.{Point, Rect, SingleLineScore, SingleLineScoreLayout}
import com.mayreh.martha.tool.Unity
import play.api.libs.json.Json

object TuneGenerator {

  def main(args: Array[String]): Unit = {
    val Array(songPath, outputPath) = args.map(new java.io.File(_))

    val parser = new ABCParser
    val Right(tune) = parser.parse(scala.io.Source.fromFile(songPath).mkString)

    val score = new SingleLineScore(Rect(0, 0, 1000000, 50000), SingleLineScoreLayout.default.copy(widthPerUnitNoteLength = 100) * 100)
    score.loadVoice(tune.tuneHeader, tune.tuneHeader.voiceHeaders(0), tune.tuneBody.voices(0))

    val obj = ScoreObject(
      Unity.pixel2unity(score.voiceStart),
      score.elements.zipWithIndex.map { case (e, idx) =>
        ScoreElementObject(f"element_${idx}%05d", , Point(Unity.pixel2unity(e.frame.x), Unity.pixel2unity(e.frame.y)))
      }
    )

    score.dumpNodes(outputPath)

    val w = new PrintWriter(new FileOutputStream(new File(outputPath, "obj.json"), false))
    try {
      w.println(Json.prettyPrint(Json.toJson(obj)))
    } finally {
      w.close()
    }
  }
}
