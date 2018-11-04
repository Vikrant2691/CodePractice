package com.spark.mongoconnector

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

object SparkTest {
  
  def main(args: Array[String]): Unit = {
    
    val conf= new SparkConf()
    val sc= new SparkContext(conf)
    
    val rdd= sc.textFile("file:///home/Hadoop/test")
    rdd.collect().foreach(println)
    
  }
}