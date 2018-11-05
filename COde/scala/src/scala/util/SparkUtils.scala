package com.fedex.smartpost.spark.util

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.SaveMode
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._
import org.joda.time.DateTime
import org.joda.time.Days

object SparkUtils {
  def createContext(appName: String): SparkContext = {
    val conf = new SparkConf().setAppName(appName)
    /*conf.set("spark.shuffle.service.enabled", "true")
    conf.set("spark.dynamicAllocation.enabled", "true")*/
    conf.set("spark.executor.cores", "1");
    conf.set("spark.executor.instances", "20");
    return new SparkContext(conf)
  }
  
  def writeTable(dataFrame : DataFrame, table : String, format : String, saveMode : SaveMode) = {
    dataFrame.withColumn("insert_timestamp", current_timestamp()).write.format(format).mode(saveMode).saveAsTable(table)
  }
  
  def overwriteOrcTable(dataFrame : DataFrame, table : String) = {
    writeTable(dataFrame, table, "orc", SaveMode.Overwrite)
  }
  
  def calculateDaysInSeconds(days: Int) : Int = {
    return Days.days(days).toStandardSeconds().getSeconds()
  }
  
  def getDaysAgo(daysToSubtract : Int) : String = {
    DateTime.now().minusDays(daysToSubtract).toString("YYYYMMdd")
  }
}