name := "Spark-Kafka-CustomStream"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies += "org.apache.spark" % "spark-streaming_2.11" % "2.1.1"
libraryDependencies += "org.apache.kafka" % "kafka-clients" % "0.10.2.0"
libraryDependencies += "org.apache.spark" % "spark-streaming-kafka-0-8_2.11" % "2.1.1"
libraryDependencies += "net.liftweb" % "lift-json_2.11" % "2.6.2"
libraryDependencies +=  "org.twitter4j" % "twitter4j-stream" % "4.0.6"
libraryDependencies +=  "com.typesafe" % "config" % "1.3.1"
libraryDependencies += "io.spray" %%  "spray-json" % "1.3.3"
libraryDependencies += "org.apache.spark" % "spark-sql_2.11" % "2.1.1" % "provided"
