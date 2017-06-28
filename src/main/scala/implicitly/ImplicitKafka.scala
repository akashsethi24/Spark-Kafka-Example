package implicitly

import custom.ConfigLoader
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, DataFrameReader, DataFrameWriter, SparkSession}

object ImplicitKafka {

  @transient val spark: SparkSession = SparkSession.builder().config(ConfigLoader.sparkConf).getOrCreate()

  import spark.implicits._

  implicit class KafkaStreamReader(reader: DataFrameReader) {

    def kafka(broker: String, topic: String, startingOffsets: String = "latest"): DataFrame = {
      val sample = spark.read
        .format("kafka")
        .option("kafka.bootstrap.servers", broker)
        .option("subscribe", topic)
        .load()
        .select(col("value").as[String])
        .limit(100)
        .rdd

      val schema = spark.read.json(sample).schema
      reader.format("kafka")
        .option("kafka.bootstrap.servers", broker)
        .option("subscribe", topic)
        .option("startingOffsets", startingOffsets)
        .load()
        .withColumn("value", from_json(col("value").cast("string"), schema))
    }
  }

  implicit class kafkaStreamWriter[A](writer: DataFrameWriter[A]) {

    def kafka(broker: String, topic: String): Unit = {

      val clz = classOf[DataFrameWriter[_]]
      val method = clz.getDeclaredMethod("df")
      val df = method.invoke(writer).asInstanceOf[DataFrame]

      df.select(to_json(struct("*")) as 'value)
        .write
        .format("kafka")
        .option("kafka.bootstrap.servers", broker)
        .option("topic", topic)
        .save()
    }
  }

}
