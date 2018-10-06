package com.test.spark

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object SparkTest {
  def main(args: Array[String]): Unit = {
    
    System.setProperty("hadoop.home.dir", "D:/hadoop/")
    val conf = new SparkConf().setAppName("SparkTest").setMaster("local")
    val sc = new SparkContext(conf)
    val spark = SparkSession
      .builder()
      .appName("Spark SQL basic example")
      .config("spark.some.config.option", "some-value")
      .getOrCreate()
      
    val df= spark.read.option("header", true).csv("file:///D:/workspace/SparkTest/test.csv")
    df.show()
  }
}