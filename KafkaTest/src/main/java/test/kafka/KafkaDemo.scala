package test.kafka

import org.apache.spark.SparkConf
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies._
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.HasOffsetRanges
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming._
import java.util.Calendar
import java.text.SimpleDateFormat
import org.apache.spark.sql.SaveMode
import org.apache.spark.TaskContext

object KafkaDemo {
  def main(args: Array[String]): Unit = {
    
    val conf = new SparkConf().setAppName("HelloKafka").setMaster("local")
    val sc = new SparkContext(conf)
    val spark = SparkSession
      .builder()
      .appName("Spark SQL basic example")
      .getOrCreate()
    import spark.implicits._
    val ssc = new StreamingContext(sc, Seconds(10))
    
    
   print("hi")


    
  }
}