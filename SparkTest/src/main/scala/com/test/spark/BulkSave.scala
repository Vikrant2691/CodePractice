package com.test.spark

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object BulkSave {
  def main(args: Array[String]): Unit = {
        System.setProperty("hadoop.home.dir", "D:/work/hadoop/")
    val conf = new SparkConf().setAppName("HelloKafka").setMaster("local")
    val sc = new SparkContext(conf)
    val spark = SparkSession
      .builder()
      .appName("Spark SQL basic example")
      .getOrCreate()
    import spark.implicits._
    
    spark.sql("set spark.sql.orc.impl=native")
    spark.read.orc("D:/work/workspace/SparkTest/data/*").show()
    
    
  }
  
}