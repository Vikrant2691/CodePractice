package com.fedex.smartpost.spark.epdialerts


import org.apache.spark.SparkContext
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.sql.functions._
import org.apache.spark.sql.SaveMode
import com.fedex.smartpost.spark.dao.HubDeterminationDao
import com.fedex.smartpost.spark.dao.FirstHubTouchDao
import com.fedex.smartpost.spark.dao.OrderCreateDao
import com.fedex.smartpost.spark.util.SparkUtils


object CustomerWaitModel {
 def main(args: Array[String]) = {
    val sc = SparkUtils.createContext("CustomerWaitModel")
    val hc = new HiveContext(sc)
    import hc.implicits._
    hc.sql("use hdp_smp")
    
   val orderCreateTable = new OrderCreateDao(hc).fetchEarliestOrderCreate().drop($"hub_id")
            
   var firstHubTouch = new FirstHubTouchDao(hc).fetchFirstHubTouchTable()
   
   //get the firstHubTouch with movement and get the difference between the first scan time and the order create timestamp
   val joinedData = orderCreateTable.join(
      firstHubTouch,
      firstHubTouch.col("upn") === orderCreateTable.col("upn"),
      "inner"
      //time before first scan - difference in seconds
    ).withColumn("difference", unix_timestamp($"scan_time") - $"unix_event_timestamp")
    
    val reducedJoinData = joinedData
                          .drop(firstHubTouch.col("upn"))
                          .drop($"scan_time")
                          .drop($"unix_event_timestamp")
    
    //use temp table so that we can use hive's percentile function to get the actual value
    reducedJoinData.registerTempTable("package_wait_times")
    
    
    val customerWaitTimes = hc.sql(
      "SELECT hub_id, account_number, day_of_week " +
      ",round(percentile(difference, 0.1)) as percentile_10 " +
      ",round(percentile(difference, 0.2)) as percentile_20 " +
      ",round(percentile(difference, 0.3)) as percentile_30 " +
      ",round(percentile(difference, 0.4)) as percentile_40 " +
      ",round(percentile(difference, 0.5)) as percentile_50 " +
      ",round(percentile(difference, 0.6)) as percentile_60 " +
      ",round(percentile(difference, 0.7)) as percentile_70 " +
      ",round(percentile(difference, 0.8)) as percentile_80 " +
      ",round(percentile(difference, 0.9)) as percentile_90 " +
      ",round(percentile(difference, 0.91)) as percentile_91 " +
      ",round(percentile(difference, 0.92)) as percentile_92 " +
      ",round(percentile(difference, 0.93)) as percentile_93 " +
      ",round(percentile(difference, 0.94)) as percentile_94 " +
      ",round(percentile(difference, 0.95)) as percentile_95 " +
      ",round(percentile(difference, 0.96)) as percentile_96 " +
      ",round(percentile(difference, 0.97)) as percentile_97 " +
      ",round(percentile(difference, 0.98)) as percentile_98 " +
      ",round(percentile(difference, 0.99)) as percentile_99 " +
      ",avg(difference) as average " +
      ",count(*) as count " + 
      "FROM package_wait_times " +
      "group by hub_id, account_number, day_of_week " +
      "order by hub_id, account_number, day_of_week"
    )
    
    
   SparkUtils.overwriteOrcTable(customerWaitTimes, "hdp_smp.customer_wait_time")
  }
 }