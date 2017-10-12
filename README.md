# Spark-Kafka-Stream-Example
# Kafka Custom Serializable and Decoder

This repo contains the Example of Spark Streaming using Apache Kafka.

We are Feeding Case class Object to Apache Kafka via Kafka Producer and Fetching the same via Spark Streaming and printing the Case class Object in String Form.

### Flow :-<br />
        CustomCaseClass(1, "Sample Name") -> Apache Kafka [Producer]
        Apache Kafka [Consumer] -> Spark Streaming -> CustomCaseClass(1, "Sample Name") -> Show.
        
##### We Are Using :- <br />
        Spark Version :- 2.2.0
        Scala Version :- 2.11.8
        Lift Json Version :- 2.6.2

For Custom Topic Streaming Code is placed under custom Package

We are using Custom Serilizer and DeSerializer for Custom Case class

### Recently Added
### Implicitly Produce Kafka Topic using DataFrame
### Implicitly Consume Kafka Topic using Spark Streaming

Soemthing's wrong then raise an issue. <br>
Happy Coding.
