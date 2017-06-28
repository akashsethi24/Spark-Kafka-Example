package custom

import com.typesafe.config.{Config, ConfigFactory}
import net.liftweb.json.DefaultFormats
import org.apache.spark.SparkConf

/**
  * Created by akash on 28/6/17.
  */
object ConfigLoader {

  val configFactory: Config = ConfigFactory.load()

  val sparkAppName: String = configFactory.getString("spark.app.name")
  val sparkMaster: String = configFactory.getString("spark.ip")
  val sparkCores: String = configFactory.getString("spark.cores")
  val sparkMemory: String = configFactory.getString("spark.executor.memory")

  val kafkaServer: String = configFactory.getString("kafka.server")
  val kafkaTopic: String = configFactory.getString("kafka.topic")
  val kafkaClientId: String = configFactory.getString("kafka.clientId")

  val zookeeperUrl: String = configFactory.getString("zookeeper.server")

  val sparkConf: SparkConf = new SparkConf()
    .setAppName(sparkAppName)
    .setMaster(sparkMaster)
    .set("spark.executor.memory", sparkMemory)
    .set("spark.executor.core", sparkCores)
    .registerKryoClasses(Array(classOf[DefaultFormats]))
}
