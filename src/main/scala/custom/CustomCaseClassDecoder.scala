package custom

import java.io.{ByteArrayInputStream, ObjectInputStream}

import kafka.utils.VerifiableProperties

class CustomCaseClassDecoder(props: VerifiableProperties) extends kafka.serializer.Decoder[CustomCaseClass] {
  override def fromBytes(bytes: Array[Byte]): CustomCaseClass = {

    val byteIn = new ByteArrayInputStream(bytes)
    val objIn = new ObjectInputStream(byteIn)
    val obj = objIn.readObject().asInstanceOf[CustomCaseClass]
    byteIn.close()
    objIn.close()
    obj
  }
}
