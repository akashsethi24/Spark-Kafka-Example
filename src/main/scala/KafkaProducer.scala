import ConfigLoader._
import java.util.Properties

class KafkaProducer {

  def main(args: Array[String]): Unit = {

    val kafkaProperty = new Properties()
    kafkaProperty.put("bootstrap.servers", kafkaServer)
    kafkaProperty.put("client.id", kafkaClientId)
    kafkaProperty.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    kafkaProperty.put("value.serializer", "TweetSerializer")

    val kafkaProducer = new org.apache.kafka.clients.producer.KafkaProducer[String, Tweet](kafkaProperty)

  }
}
