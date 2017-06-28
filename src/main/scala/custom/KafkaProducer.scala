package custom

import java.util.Properties

import custom.ConfigLoader.{kafkaClientId, kafkaServer, kafkaTopic}

/**
  * Created by akash on 28/6/17.
  */
object KafkaProducer {

  def main(args: Array[String]): Unit = {

    val kafkaProperty = new Properties()
    kafkaProperty.put("bootstrap.servers", kafkaServer)
    kafkaProperty.put("client.id", kafkaClientId)
    kafkaProperty.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    kafkaProperty.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    val kafkaProducer = new org.apache.kafka.clients.producer.KafkaProducer[String, String](kafkaProperty)
    val producerRecord = new org.apache.kafka.clients.producer.ProducerRecord[String, String](kafkaTopic, "1", Tweet("Akash24", "Adele").getFilteredTweet)
    1 to 25 map { number => kafkaProducer.send(producerRecord) }

  }
}
