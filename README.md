# Spark-Kafka-Stream-Example
This repo contains the Example of Spark Streaming using Apache Kafka.

We are Feeding Case class Object to Apache Kafka via Kafka Producer and Fetching the same via Spark Streaming and printing the Case class Object in String Form.

### Flow :-<br />
        CustomCaseClass(1, "Sample Name") -> Apache Kafka [Producer]
        Apache Kafka [Consumer] -> Spark Streaming -> CustomCaseClass(1, "Sample Name") -> Show.
        
##### We Are Using :- <br />
        Spark Version :- 2.1.1
        Scala Version :- 2.11.8
        Spark Streaming Version :- 2.0.0
        Lift Json Version :- 2.6.2

For Custom Topic Streaming Code is placed under custom Package

Soemthing's wrong then raise an issue. <br>
Happy Coding.
