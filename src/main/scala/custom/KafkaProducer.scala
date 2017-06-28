package custom

import java.util.Properties

import custom.ConfigLoader.{kafkaClientId, kafkaServer, kafkaTopic}

object KafkaProducer {

  def main(args: Array[String]): Unit = {

    val kafkaProperty = new Properties()
    kafkaProperty.put("bootstrap.servers", kafkaServer)
    kafkaProperty.put("client.id", kafkaClientId)
    kafkaProperty.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    kafkaProperty.put("value.serializer", "custom.CustomCaseClassSerializer")

    val caseClassList = 1 to 25 map { index => CustomCaseClass(index, "Custom Name " + index) }
    val kafkaProducer = new org.apache.kafka.clients.producer.KafkaProducer[String, CustomCaseClass](kafkaProperty)

    caseClassList foreach { customCaseClassObj =>
      val producerRecord = new org.apache.kafka.clients.producer.ProducerRecord[String, CustomCaseClass](kafkaTopic, "1", customCaseClassObj)
      kafkaProducer.send(producerRecord)
    }
  }
}
