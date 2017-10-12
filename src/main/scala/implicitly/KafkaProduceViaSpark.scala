package implicitly

import custom.ConfigLoader.kafkaServer
import implicitly.SparkWithKafka._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

/**
  * Created by akash on 6/7/17.
  */
object KafkaProduceViaSpark {

  val spark: SparkSession = SparkSession.builder().master("local[2]").appName("test").getOrCreate()

  def main(args: Array[String]): Unit = {

    val kafkaTopic = "testing"
    val nestedColumn = Array(col("id"), struct(col("name")).alias("person"))
    val sampleDF = spark.createDataFrame(Seq((1001, "Akash"), (1002, "Rishabh"), (1003, "Kunal")))
      .toDF("id", "name").select(nestedColumn: _*)
    sampleDF.writeToKafka(kafkaServer, kafkaTopic)
    spark.read.readFromKafka(kafkaServer, kafkaTopic).show
  }
}
