package stream

import custom.ConfigLoader
import org.apache.spark.sql.{SQLContext, SparkSession}
import org.apache.spark.sql.execution.streaming.MemoryStream

object ComplexOperation {
  @transient val spark: SparkSession = SparkSession.builder().config(ConfigLoader.sparkConf).getOrCreate()
  implicit val ctx: SQLContext = spark.sqlContext

  def main(args: Array[String]): Unit = {

    /*val intsIn = MemoryStream[Int]*/
    /*val ints = intsIn.toDF
      .withColumn("t", current_timestamp())
      .withWatermark("t", "5 minutes")
      .groupBy(window($"t", "5 minutes") as "window")
      .agg(count("*") as "total")*/
  }
}
