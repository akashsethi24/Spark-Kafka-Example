package stream

import custom.ConfigLoader
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.streaming.{OutputMode, Trigger}

object KafkaStreamExample {

  def main(args: Array[String]): Unit = {
    @transient val spark: SparkSession = SparkSession.builder().config(ConfigLoader.sparkConf).getOrCreate()
    val records = spark.
      readStream.
      format("kafka").
      option("subscribepattern", "testing").
      option("kafka.bootstrap.servers", "localhost:9092").
      option("startingoffsets", "latest").
      option("maxOffsetsPerTrigger", 1).
      load

    val result = records.selectExpr("CAST(value AS STRING) AS value")
      .selectExpr("from_json(struct(value.*))")
    val see = result.writeStream.
      format("console").
      option("truncate", value = false).
      trigger(Trigger.ProcessingTime(10)).
      outputMode(OutputMode.Append).
      queryName("from-kafka-to-console").
      start 
    see.awaitTermination()
  }
}
