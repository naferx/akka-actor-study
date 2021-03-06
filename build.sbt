name := "akka-actor-study"

version := "1.0.0"

scalaVersion := "2.11.8"

val akkaVersion = "2.4.12"

libraryDependencies ++= Seq(
    "com.typesafe.akka"     %%    "akka-actor"        %   akkaVersion,
    "com.typesafe.akka"     %%    "akka-slf4j"        %   akkaVersion,
    "ch.qos.logback"        %     "logback-classic"   %   "1.1.7",

    // Utils
    "com.typesafe"          %     "config"            %   "1.3.1",

    // Testing
    "org.scalatest"         %%    "scalatest"         %   "3.0.0"      %  Test,
    "com.typesafe.akka"     %%    "akka-testkit"      %   akkaVersion  %  Test
)
