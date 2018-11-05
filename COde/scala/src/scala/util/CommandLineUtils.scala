package com.fedex.smartpost.spark.util

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.SaveMode
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._
import org.joda.time.DateTime
import org.joda.time.Days

object CommandLineUtils {
  def createContext(appName: String): SparkContext = {
    val conf = new SparkConf().setAppName(appName)
    conf.set("spark.shuffle.service.enabled", "true")
    conf.set("spark.dynamicAllocation.enabled", "true")
    return new SparkContext(conf)
  }
  
  def getStartDate(args: Array[String]) : String =
  {
      if (args.length < 2) {
        return null;
      }
      return args(0);
  }
  
   def getEndDate(args: Array[String]) : String =
  {
      if (args.length < 2) {
        return null;
      }
      return args(1);
  }
}