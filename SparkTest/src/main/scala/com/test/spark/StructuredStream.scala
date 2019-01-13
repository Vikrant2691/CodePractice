package com.test.spark


import org.apache.spark.SparkConf
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming._
import java.util.Calendar
import java.text.SimpleDateFormat
import org.apache.spark.sql.SaveMode


object StructuredStream {
  def main(args: Array[String]): Unit = {
    
   System.setProperty("hadoop.home.dir", "D:/work/hadoop/")
    
    val conf = new SparkConf().setAppName("HelloKafka").setMaster("local")
    val sc = new SparkContext(conf)
    
    val spark = SparkSession
      .builder()
      .appName("Spark SQL basic example")
      .getOrCreate()
      
    import spark.implicits._

    
    val df = spark
      .read
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("subscribe", "test")
      .load()
    df.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)")
      .as[(String, String)]
    
  }
  
    
  
}