name := "akka-quickstart-scala"

version := "1.0"

scalaVersion := "2.12.2"

val akkaVersion = "2.5.4"
val akkaHttpVersion = "10.0.10"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion, 
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-actor"  % akkaVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.10", 

  "com.typesafe.akka" %% "akka-testkit" % akkaVersion,

  "org.scalatest" %% "scalatest" % "3.0.1" % "test"
)

assemblyOutputPath in assembly := new File("./target/app.jar")