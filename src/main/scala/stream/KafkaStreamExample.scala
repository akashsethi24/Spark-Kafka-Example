package stream

import custom.ConfigLoader
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.streaming.{OutputMode, Trigger}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.functions._

object KafkaStreamExample {

  def main(args: Array[String]): Unit = {
    @transient val spark: SparkSession = SparkSession.builder().config(ConfigLoader.sparkConf).getOrCreate()
    import spark.implicits._
    val records = spark.
      readStream.
      format("kafka").
      option("subscribepattern", "testing").
      option("kafka.bootstrap.servers", "localhost:9092").
      option("startingoffsets", "latest").
      option("maxOffsetsPerTrigger", 10).
      load

    val schema = StructType(Seq(StructField("id", IntegerType),
      StructField("person", StructType(Seq(StructField("name", StringType))))))
    val result = records.selectExpr("CAST(value AS STRING) AS json")
      .select(from_json($"json", schema).as("data"))
      .select("data.*")


    val ints = result
      .withColumn("t", current_timestamp())
      .withWatermark("t", "1 minutes")
      .groupBy(window($"t", "1 minutes") as "window")
      .agg(count("*") as "total")

    ints.printSchema()
    val see = ints.writeStream.
      format("console").
      option("truncate", value = false).
      trigger(Trigger.ProcessingTime(10)).
      outputMode(OutputMode.Complete()).
      queryName("from-kafka-to-console").
      start
    see.awaitTermination()
  }
}
