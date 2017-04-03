import java.io.{ByteArrayOutputStream, ObjectOutputStream}
import java.util

import com.typesafe.config.ConfigFactory
import kafka.utils.VerifiableProperties
import net.liftweb.json.Extraction._
import net.liftweb.json._
import org.apache.kafka.common.serialization.Deserializer
import org.apache.spark.SparkConf

object ConfigLoader {

  val configFactory = ConfigFactory.load()

  val sparkAppName = configFactory.getString("spark.app.name")
  val sparkMaster = configFactory.getString("spark.ip")
  val sparkCores = configFactory.getString("spark.cores")
  val sparkMemory = configFactory.getString("spark.executor.memory")

  val kafkaServer = configFactory.getString("kafka.server")
  val kafkaTopic = configFactory.getString("kafka.topic")
  val kafkaClientId = configFactory.getString("kafka.clientId")

  val zookeeperUrl = configFactory.getString("zookeeper.server")

  val tweetConsumerKey = configFactory.getString("twitter.consumer.key")
  val tweetConsumerSecret = configFactory.getString("twitter.consumer.secret")
  val tweetAccessToken = configFactory.getString("twitter.access.token")
  val tweetAccessSecret = configFactory.getString("twitter.access.secret")

  val sparkConf = new SparkConf()
    .setAppName(sparkAppName)
    .setMaster(sparkMaster)
    .set("spark.executor.memory", sparkMemory)
    .set("spark.executor.core", sparkCores)
    .registerKryoClasses(Array(classOf[DefaultFormats]))
}

case class Tweet(user: String, tweet: String) {

  implicit val formats: Formats = DefaultFormats

  def getFilteredTweet: String = {
    val user = new String(this.user.toCharArray.filter(_.toString.matches("^[a-zA-z0-9]*$")))
    val tweet = new String(this.tweet.toCharArray.filter(_.toString.matches("^[a-zA-z0-9]*$")))
    val filterTweet = Tweet(user, tweet)
    compact(render(decompose(filterTweet)))
  }
}

import com.fasterxml.jackson.databind.ObjectMapper
import kafka.serializer.Decoder
import org.apache.kafka.common.serialization.Serializer

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

class TweetDecoder(props: VerifiableProperties = null) extends Decoder[Tweet] {

  override def fromBytes(bytes: Array[Byte]): Tweet = {
    val mapper = new ObjectMapper

    try {
      mapper.readValue(bytes, classOf[Tweet])
    } catch {
      case exception: Exception => throw new Exception(" Unable to Deserialize Tweet Byte to Tweet " + exception.getMessage)
    }
  }
}