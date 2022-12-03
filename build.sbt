ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "compliance-system"
  )

libraryDependencies ++= Seq(
  // scala-csv
  "com.github.tototoshi" %% "scala-csv" % "1.3.10",
  // spark
  "org.apache.spark" %% "spark-core" % "3.2.2",
  "org.apache.spark" %% "spark-sql" % "3.2.2",
  // Spray JSON
  "io.spray" %%  "spray-json" % "1.3.6",
  // Scopt
  "com.github.scopt" %% "scopt" % "4.1.0"
)