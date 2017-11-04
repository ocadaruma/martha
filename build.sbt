name := "martha"

version := "0.1"

scalaVersion := "2.12.3"

libraryDependencies ++= Seq(
  "com.lihaoyi" %% "fastparse" % "1.0.0",
  "org.apache.xmlgraphics" % "batik-svggen" % "1.9.1",
  "org.scala-lang.modules" %% "scala-xml" % "1.0.6",
  "org.scalatest" %% "scalatest" % "3.0.4" % "test"
)
