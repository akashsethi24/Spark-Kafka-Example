package implicitly

import custom.ConfigLoader
import org.apache.spark.sql.functions._
import org.apache.spark.sql._

object SparkWithKafka {

  @transient val spark: SparkSession = SparkSession.builder().config(ConfigLoader.sparkConf).getOrCreate()

  import spark.implicits._

  implicit class KafkaStreamReader(reader: DataFrameReader) {

    def readFromKafka(broker: String, topic: String, startingOffsets: String = "earliest"): DataFrame = {
      val completeDS = spark.read
        .format("kafka")
        .option("kafka.bootstrap.servers", broker)
        .option("subscribe", topic)
        .load()
        .select(col("value").as[String])

      val sampleDS = completeDS.limit(50)
      val schema = spark.read.json(sampleDS).schema
      reader.format("kafka")
        .option("kafka.bootstrap.servers", broker)
        .option("subscribe", topic)
        .option("startingOffsets", startingOffsets)
        .load()
        .withColumn("value", from_json(col("value").cast("string"), schema))
        .select(col("value.*"))
    }
  }

  implicit class kafkaDFStreamWriter(dataFrame: DataFrame) {

    def writeToKafka(broker: String, topic: String): Unit = {

      dataFrame.select(to_json(struct("*")) as 'value)
        .write
        .format("kafka")
        .option("kafka.bootstrap.servers", broker)
        .option("topic", topic)
        .save()
    }
  }

  implicit class kafkaDSStreamWriter[A](dataSet: Dataset[A]) {

    def writeToKafka(broker: String, topic: String): Unit = {

      dataSet.select(to_json(struct("*")) as 'value)
        .write
        .format("kafka")
        .option("kafka.bootstrap.servers", broker)
        .option("topic", topic)
        .save()
    }
  }

}
