package com.fedex.smartpost.spark.epdialerts


import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.sql.functions._
import com.fedex.smartpost.spark.util.SparkUtils

class DetermineAlertPackagesPackageBased() {
  val minimumNumberOfPackages = 1000
  
  def determinePackages(hc : HiveContext, waitTimePackage : DataFrame, baselineWaitTimes : DataFrame, numberOfSeconds : Int, typeOfAlerts : String) = {
    import hc.implicits._
    val tempTable = typeOfAlerts + "_temp"
    
    //join in baseline to get the percentile
    val waitTimePackageWithBaseline = waitTimePackage.join(
      baselineWaitTimes, Seq("account_number", "hub_id", "day_of_week"),
      "inner"
    )
    
    val alertPackages = waitTimePackageWithBaseline.where($"current_wait_time" >= ($"percentile_98"  + numberOfSeconds))
       
    SparkUtils.overwriteOrcTable(alertPackages, "brad_" + typeOfAlerts);
  }
  
  def determinePackagesSupplemental(hc : HiveContext, waitTimePackage : DataFrame, baselineWaitTimes : DataFrame) = {
    import hc.implicits._
    //join in baseline to get the percentile
    val waitTimePackageWithBaseline = waitTimePackage.join(
      baselineWaitTimes, Seq("account_number", "hub_id", "day_of_week"),
      "left_outer"
    )
    
   //filter out < 1000 or no baseline
    val insufficientPackages = waitTimePackageWithBaseline.where($"percentile_hist_count" < minimumNumberOfPackages || $"percentile_hist_count".isNull)
        
    val insufficientPackagesWithTooLongWaitTimes = insufficientPackages.where($"current_wait_time" >= SparkUtils.calculateDaysInSeconds(14))
    
    SparkUtils.overwriteOrcTable(insufficientPackagesWithTooLongWaitTimes, "brad_supplemental");
                
  }
  
}