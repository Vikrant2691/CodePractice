package com.fedex.smartpost.spark.epdialerts

import org.apache.spark.SparkContext
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.sql.functions._
import com.fedex.smartpost.spark.dao.HubDeterminationDao
import com.fedex.smartpost.spark.dao.FirstHubTouchDao
import com.fedex.smartpost.spark.dao.OrderCreateDao
import com.fedex.smartpost.spark.dao.SystemParameterDao
import com.fedex.smartpost.spark.util.SparkUtils
import com.fedex.smartpost.spark.util.DateTimeUtils
import com.fedex.smartpost.spark.dao.CustomerDao


object CustomerSortPercentage {
 def main(args: Array[String]) = {
    val sc = SparkUtils.createContext("CustomerSortPercentage")
    val hc = new HiveContext(sc)
    import hc.implicits._
    hc.sql("use hdp_smp")
    
   var sysParams = new SystemParameterDao(hc)
    
   val startDate = DateTimeUtils.getDaysAgo(sysParams.fetchSystemParameterIntValue("CUST_SORT_PERCENT_START_DAYS_AGO"))
   val endDate = DateTimeUtils.getDaysAgo(sysParams.fetchSystemParameterIntValue("CUST_SORT_PERCENT_END_DAYS_AGO"))
   
   val orderCreateTable = new OrderCreateDao(hc).fetchEarliestOrderCreate(startDate, endDate)
                          .drop($"destination_sort_code").drop($"hub_id").drop($"event_time_local_utc")
                          .drop($"event_dt").drop($"day_of_week")
            
   var firstHubTouch = new FirstHubTouchDao(hc).fetchFirstHubTouchTable()
   
   val joinedData = orderCreateTable.join(
      firstHubTouch,
      firstHubTouch.col("upn") === orderCreateTable.col("upn"),
      "left_outer"
    )
       
   val scanTotal = joinedData.where($"scan_time".isNotNull).drop(firstHubTouch.col("upn")).drop(orderCreateTable.col("upn")).drop($"hub_id").drop($"scan_time").groupBy($"account_number").count()   
   val epdiTotal = joinedData.groupBy($"account_number").count().drop(firstHubTouch.col("upn")).drop($"hub_id").drop($"scan_time")
   
  val customerSortPercentage = epdiTotal.join(
      scanTotal,
      epdiTotal.col("account_number") === scanTotal.col("account_number"),
      "inner"
    ).withColumn("sort_percentage", when(epdiTotal.col("count") >= 1000, round((scanTotal.col("count") / epdiTotal.col("count")) * 100)).otherwise(101)).drop(epdiTotal.col("count")).drop(scanTotal.col("count")).drop(scanTotal.col("account_number"))
  
  //Brad Semrad - renamed the column because as of 9-12-2016, the query plan was having problems distinguishing the columns apart in the withColumn 
  val whitelist = new CustomerDao(hc).fetchWhitelistCustomerTable().withColumnRenamed("account_number", "whitelist_account_number");
    
  val customerSortPercentageWhitelisted = customerSortPercentage.join(
      whitelist,
      customerSortPercentage.col("account_number") === whitelist.col("whitelist_account_number"),
      "left_outer"
  ).withColumn("sort_percentage", when($"whitelist_account_number".isNotNull, 102).otherwise($"sort_percentage")).drop($"whitelist_account_number")
         
   SparkUtils.overwriteOrcTable(customerSortPercentageWhitelisted, "hdp_smp.customer_sort_percentage")
  }
 }