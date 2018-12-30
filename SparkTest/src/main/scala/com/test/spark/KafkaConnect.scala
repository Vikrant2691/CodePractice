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

object KafkaConnect {
  def main(args: Array[String]): Unit = {

    System.setProperty("hadoop.home.dir", "D:/work/hadoop/")
    val conf = new SparkConf().setAppName("HelloKafka").setMaster("local")
    val sc = new SparkContext(conf)
    val spark = SparkSession
      .builder()
      .appName("Spark SQL basic example")
      .getOrCreate()
    import spark.implicits._
    val ssc = new StreamingContext(sc, Seconds(10))

    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "localhost:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "test-consumer-group",
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (false: java.lang.Boolean))

    val topics = Array("test")
    val stream = KafkaUtils.createDirectStream[String, String](
      ssc,
      PreferConsistent,
      Subscribe[String, String](topics, kafkaParams))

    /*val jsonString=stream.map(record=>(record.value()))
   val df = spark.read.json(Seq(jsonString.toString()).toDS)
   df.show(truncate=false)*/
      
   val now = Calendar.getInstance.getTime
   val format= new SimpleDateFormat("yyyyMMddHHmmss")
   val date=format.format(now)
      
   spark.sql("set spark.sql.orc.impl=native")
   stream.foreachRDD(rddRaw=>{
     val rdd= rddRaw.map(_.value().toString())
     val df =spark.read.json(rdd.toDS())
     df.write.mode(SaveMode.Append).orc("D:/work/workspace/SparkTest/data/jsondata"+date)
   })
   
    ssc.start()
    ssc.awaitTermination()

  }
}