import ConfigLoader._
import kafka.serializer.StringDecoder
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Seconds, StreamingContext}

class KafkaConsumer {

  def main(args: Array[String]): Unit = {

    val sparkStreamingContext = new StreamingContext(sparkConf, Seconds(5))

    val kafkaParam: Map[String, String] = Map(
      "bootstrap.servers" -> kafkaServer,
      "key.deserializer" -> classOf[StringDeserializer].getCanonicalName,
      "value.deserializer" -> classOf[TweetDecoder].getCanonicalName,
      "zookeeper.connect" -> zookeeperUrl,
      "group.id" -> "demo-group")

    import org.apache.spark.streaming.kafka._
    val topicSet = Map(kafkaTopic -> 2)
    val streaming = KafkaUtils.createStream[String, Tweet, StringDecoder, TweetDecoder](sparkStreamingContext, kafkaParam, topicSet, StorageLevel.MEMORY_AND_DISK)

    streaming.print()

    sparkStreamingContext.start()
    sparkStreamingContext.awaitTermination()
  }
}
