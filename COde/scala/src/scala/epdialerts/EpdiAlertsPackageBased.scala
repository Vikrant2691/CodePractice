package com.fedex.smartpost.spark.epdialerts

import org.apache.spark.SparkContext
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.sql.functions._
import com.fedex.smartpost.spark.dao.FirstHubTouchDao
import com.fedex.smartpost.spark.dao.HubDeterminationDao
import com.fedex.smartpost.spark.dao.CustomerDao
import com.fedex.smartpost.spark.dao.CustomerSortPercentageDao
import com.fedex.smartpost.spark.dao.OrderCreateDao
import com.fedex.smartpost.spark.util.SparkUtils
import com.fedex.smartpost.spark.util.DateTimeUtils
import com.fedex.smartpost.spark.dao.SystemParameterDao

object EpdiAlertsPackageBased {
  def main(args: Array[String]) = {
    val sc = SparkUtils.createContext("EpdiAlerts-PackageBased")
    val hc = new HiveContext(sc)
    import hc.implicits._
    hc.sql("use hdp_smp")
  
   val sysParams = new SystemParameterDao(hc)
       
   val startDate = DateTimeUtils.getDaysAgo(sysParams.fetchSystemParameterIntValue("EPDI_ALERTS_START_DAYS_AGO"))
   val endDate = DateTimeUtils.getDaysAgo(sysParams.fetchSystemParameterIntValue("EPDI_ALERTS_END_DAYS_AGO"))
   
   val orderCreateTable = new OrderCreateDao(hc).fetchEarliestOrderCreate(startDate, endDate).where($"account_number" === "106307075").where($"event_dt" === "20161020").withColumnRenamed("hub_id", "expected_hub")
    
   var firstHubTouch = new FirstHubTouchDao(hc).fetchFirstHubTouchTable()
     
   val orderCreateWithOutMovement = orderCreateTable.join(
      firstHubTouch,
      firstHubTouch.col("upn") === orderCreateTable.col("upn"),
      "left_outer"
    ).where($"scan_time" <=> null).drop(firstHubTouch.col("upn")).drop($"hub_id").drop($"scan_time")
    
   val hubDeterminationTable = new HubDeterminationDao(hc).fetchHubDeterminationTable().as("hub_determination")
    
   val orderCreateWithOutMovementWithHub = orderCreateWithOutMovement.join(
      hubDeterminationTable, Seq("account_number", "destination_sort_code", "expected_hub"),
      "left_outer"
    ).withColumnRenamed("hub_id", "most_likely_hub_id").withColumn("hub_id", when($"most_likely_hub_id".isNotNull, $"most_likely_hub_id").otherwise($"expected_hub"))
        
    val waitTimePackage = orderCreateWithOutMovementWithHub.withColumn("current_wait_time", unix_timestamp() - $"unix_event_timestamp")
           
    val baselineWaitTimes = hc.sql("SELECT hub_id, account_number, day_of_week, percentile_50, percentile_98, count FROM customer_wait_time").withColumnRenamed("count", "percentile_hist_count")   
    
    val customerDao = new CustomerDao(hc)
    val customerTable = customerDao.fetchCustomerTable()
    val blacklist = customerDao.fetchBlacklistCustomerTable();
    val customerSortPercentage = new CustomerSortPercentageDao(hc).fetchCustomerSortPercentageTable()
    
    val determinePackageAlerts = new DetermineAlertPackagesPackageBased()
    
    
    waitTimePackage.cache()
    baselineWaitTimes.cache()
    
    //Do the calculations 
    determinePackageAlerts.determinePackages(hc, waitTimePackage, baselineWaitTimes, DateTimeUtils.calculateDaysInSeconds(2), "alerts")
    //determinePackageAlerts.determinePackages(hc, waitTimePackage, baselineWaitTimes, 0, "flagged")
    //determinePackageAlerts.determinePackagesSupplemental(hc, waitTimePackage, baselineWaitTimes)
  }
  
  
}