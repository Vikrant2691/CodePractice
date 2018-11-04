package com.test.spark

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object SparkTest {
  def main(args: Array[String]): Unit = {
    
    System.setProperty("hadoop.home.dir", "D:/work/hadoop/")
    val conf = new SparkConf().setAppName("SparkTest").setMaster("local")
    val sc = new SparkContext(conf)
    val spark = SparkSession
      .builder()
      .appName("Spark SQL basic example")
      .getOrCreate()
      
    val df= spark.read.option("header", true).csv("hdfs://192.168.111.128:8020/user/data/bunchofwords.txt")
    df.show()
    
    }
}