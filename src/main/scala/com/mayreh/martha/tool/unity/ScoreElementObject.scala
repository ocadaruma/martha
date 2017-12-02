package com.mayreh.martha.tool.unity

import com.mayreh.martha.render.Point
import play.api.libs.json.{Format, Json}

case class ScoreElementObject(
  id: String,
  elementType: String,
  position: Point)

object ScoreElementObject {
  implicit val point: Format[Point] = Json.format[Point]
  implicit val format: Format[ScoreElementObject] = Json.format[ScoreElementObject]
}

case class ScoreObject(
  initialX: Float,
  elements: Seq[ScoreElementObject])

object ScoreObject {
  implicit val format: Format[ScoreObject] = Json.format[ScoreObject]
}
