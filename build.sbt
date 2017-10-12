name := "Spark-Kafka-CustomStream"

version := "1.0"

scalaVersion := "2.11.8"

resolvers += Resolver.mavenLocal

libraryDependencies += "org.apache.spark" %% "spark-streaming" % "2.2.0"
libraryDependencies += "org.apache.spark" %% "spark-streaming-kafka-0-8" % "2.2.0"
libraryDependencies += "org.apache.kafka" % "kafka-clients" % "0.10.2.0"

libraryDependencies +=  "com.typesafe" % "config" % "1.3.1"

val spark = "org.apache.spark" % "spark-core_2.11" % "2.2.0"
val kafka = "org.apache.spark" % "spark-sql-kafka-0-10_2.11" % "2.2.0"
val sql =   "org.apache.spark" % "spark-sql_2.11" % "2.2.0"

libraryDependencies += spark
libraryDependencies += sql
libraryDependencies += kafka
