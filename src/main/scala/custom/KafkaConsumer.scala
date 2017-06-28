package custom

import custom.ConfigLoader.{kafkaServer, kafkaTopic, sparkConf, zookeeperUrl}
import kafka.serializer.StringDecoder
import net.liftweb.json.DefaultFormats
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Seconds, StreamingContext}


object KafkaConsumer {

  def main(args: Array[String]): Unit = {
    sparkConf.registerKryoClasses(Array(classOf[DefaultFormats]))

    val sparkStreamingContext = new StreamingContext(sparkConf, Seconds(5))

    val kafkaParam: Map[String, String] = Map(
      "bootstrap.servers" -> kafkaServer,
      "key.deserializer" -> classOf[StringDeserializer].getCanonicalName,
      "value.deserializer" -> classOf[CustomCaseClassDecoder].getCanonicalName,
      "zookeeper.connect" -> zookeeperUrl,
      "group.id" -> "demo-group")

    import org.apache.spark.streaming.kafka._
    val topicSet = Map(kafkaTopic -> 1)
    val streaming = KafkaUtils.createStream[String, CustomCaseClass, StringDecoder, CustomCaseClassDecoder](sparkStreamingContext, kafkaParam, topicSet, StorageLevel.MEMORY_AND_DISK)

    streaming.map { case (_, custom) =>
      custom.toString
    }.print()

    sparkStreamingContext.start()
    sparkStreamingContext.awaitTermination()
  }

}
