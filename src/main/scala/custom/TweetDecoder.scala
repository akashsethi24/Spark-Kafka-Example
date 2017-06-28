package custom

import com.fasterxml.jackson.databind.ObjectMapper
import kafka.serializer.Decoder
import kafka.utils.VerifiableProperties

/**
  * Created by akash on 28/6/17.
  */
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
