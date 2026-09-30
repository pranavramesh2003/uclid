name := "uclid"
version := "0.9.5"
maintainer := "spramod@cse.iitk.ac.in"
scalaVersion := "2.12.20"
 
scalacOptions += "-feature"
scalacOptions += "-unchecked"
scalacOptions += "-deprecation"

libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"
libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2" withSources()
libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.2"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.2" % "test"
libraryDependencies += "com.github.scopt" %% "scopt" % "3.7.1"
libraryDependencies += "org.json4s" %% "json4s-jackson" % "4.0.3"

// Z3 native libraries are bundled in ./z3/bin; the forked test JVM must be able to load them
Test / fork := true
Test / javaOptions ++= {
  val z3Bin = (baseDirectory.value / "z3" / "bin").absolutePath
  val opts = Seq(s"-Djava.library.path=$z3Bin")
  // JDK 24+ warns about (and will eventually block) restricted native calls such as System.loadLibrary
  if (java.lang.Runtime.version().feature() >= 22) opts :+ "--enable-native-access=ALL-UNNAMED" else opts
}
Test / envVars ++= {
  val root = baseDirectory.value.absolutePath
  val z3Bin = s"$root/z3/bin"
  Map(
    "DYLD_LIBRARY_PATH" -> z3Bin,
    "PATH" -> Seq(z3Bin, s"$root/cvc5/bin", s"$root/delphi/bin", s"$root/oracles", root, sys.env.getOrElse("PATH", "")).mkString(":")
  )
}

// do not require tests before building a fat JAR
test in assembly := {}

enablePlugins(JavaAppPackaging)
