package custom

import java.io.{ByteArrayOutputStream, ObjectOutputStream}
import java.util

import org.apache.kafka.common.serialization.Serializer

/**
  * Created by akash on 28/6/17.
  */
class TweetSerializer extends Serializer[Tweet] {

  override def configure(map: util.Map[String, _], b: Boolean): Unit = {}

  override def serialize(key: String, tweet: Tweet): Array[Byte] = {
    val byteArrayOutputStream: ByteArrayOutputStream = new ByteArrayOutputStream()
    val objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)
    objectOutputStream.writeObject(tweet)
    objectOutputStream.close()
    byteArrayOutputStream.toByteArray
  }

  override def close(): Unit = {}
}
