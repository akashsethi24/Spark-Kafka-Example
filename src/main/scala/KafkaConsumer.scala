import ConfigLoader._
import kafka.serializer.StringDecoder
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Seconds, StreamingContext}
import spray.json._
import DefaultJsonProtocol._
import net.liftweb.json._

object KafkaConsumer {

  def main(args: Array[String]): Unit = {
    sparkConf.registerKryoClasses(Array(classOf[DefaultFormats]))

    val sparkStreamingContext = new StreamingContext(sparkConf, Seconds(5))

    val kafkaParam: Map[String, String] = Map(
      "bootstrap.servers" -> kafkaServer,
      "key.deserializer" -> classOf[StringDeserializer].getCanonicalName,
      "value.deserializer" -> classOf[StringDeserializer].getCanonicalName,
      "zookeeper.connect" -> zookeeperUrl,
      "group.id" -> "demo-group")

    import org.apache.spark.streaming.kafka._
    val topicSet = Map(kafkaTopic -> 1)
    val streaming = KafkaUtils.createStream[String, String, StringDecoder, StringDecoder](sparkStreamingContext, kafkaParam, topicSet, StorageLevel.MEMORY_AND_DISK)

    streaming.map { case (id, tweet) =>
      implicit val formats = DefaultFormats
      parse(tweet).extract[Tweet]
    }.print()

    sparkStreamingContext.start()
    sparkStreamingContext.awaitTermination()
  }

  val castStringToCaseClass: (String) => Tweet = (value: String) => {
    implicit val tweetFormat = jsonFormat2(Tweet)
    value.toJson.convertTo[Tweet]
  }
}
