package custom

import java.util

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.common.serialization.Deserializer

/**
  * Created by akash on 28/6/17.
  */
class TweetDeSerializer extends Deserializer[Tweet] {

  override def configure(map: util.Map[String, _], b: Boolean): Unit = {}


  override def close(): Unit = {}

  override def deserialize(topic: String, data: Array[Byte]): Tweet = {
    val mapper = new ObjectMapper

    try {
      mapper.readValue(data, classOf[Tweet])
    } catch {
      case exception: Exception => throw new Exception(" Unable to Deserialize CassandraRNF Byte to CassandraRNF " + exception.getMessage)
    }
  }
}
