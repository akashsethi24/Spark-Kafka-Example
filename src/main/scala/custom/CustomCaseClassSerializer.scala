package custom

import java.io.{ByteArrayOutputStream, ObjectOutputStream}
import java.util

import org.apache.kafka.common.serialization.Serializer

/**
  * Created by akash on 28/6/17.
  */
class CustomCaseClassSerializer extends Serializer[CustomCaseClass] {
  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = {

  }

  override def serialize(topic: String, data: CustomCaseClass): Array[Byte] = {

    val byteOut = new ByteArrayOutputStream()
    val objOut = new ObjectOutputStream(byteOut)
    objOut.writeObject(data)
    objOut.close()
    byteOut.close()
    byteOut.toByteArray
  }

  override def close(): Unit = {

  }
}
