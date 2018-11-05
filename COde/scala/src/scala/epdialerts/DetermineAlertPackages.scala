package com.fedex.smartpost.spark.epdialerts


import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.sql.functions._
import com.fedex.smartpost.spark.util.SparkUtils

class DetermineAlertPackages() {
  val minimumNumberOfPackages = 1000
  
  def determinePackages(hc : HiveContext, waitTimePackage : DataFrame, baselineWaitTimes : DataFrame, customerTable : DataFrame, customerSortPercentage : DataFrame, blacklist : DataFrame, numberOfSeconds : Int, typeOfAlerts : String) = {
    import hc.implicits._
    val tempTable = typeOfAlerts + "_temp"
    
    //join in baseline to get the percentile
    val waitTimePackageWithBaseline = waitTimePackage.join(
      baselineWaitTimes, Seq("account_number", "hub_id", "day_of_week"),
      "inner"
    )
    
    val alertPackages = waitTimePackageWithBaseline.where($"current_wait_time" >= ($"percentile_98"  + numberOfSeconds))
         
    val rolledupAlertPackages = rollupPackage(hc, alertPackages, tempTable)
        
    //re-join in the baseline to get the other necessary columns to output
    val alertedPackageInfo = rolledupAlertPackages.join(
      baselineWaitTimes, Seq("account_number", "hub_id", "day_of_week"),
      "inner"
    ).withColumn("expected_scan_dt", to_date(from_unixtime(unix_timestamp($"event_date") + $"percentile_98"))) //use 98th
    .withColumn("days_past_expected_scan_dt", datediff(current_date(), $"expected_scan_dt")) //use current date - expected date
    
    val finalAlertedPackageInfo = alertedPackageInfo.join(
      customerTable, $"cust_nbr" === $"account_number",
      "left_outer"
    ).drop(customerTable.col("cust_nbr"))
    
    SparkUtils.overwriteOrcTable(finalAlertedPackageInfo, "hdp_smp.epdi_notifications_" + typeOfAlerts)
    
    val nonBlackList = finalAlertedPackageInfo .join(
      blacklist, blacklist.col("account_number") === finalAlertedPackageInfo.col("account_number"),
      "left_outer"
    //with the left join it will have an account number if the customer is blacklisted
    ).where(blacklist.col("account_number").isNull).drop(blacklist.col("account_number"))
    
    val filteredFinalAlertedPackageInfo = nonBlackList.where($"total_records_nbr" >= minimumNumberOfPackages).where($"percentile_hist_count" >= minimumNumberOfPackages)
    .join(
      customerSortPercentage, customerSortPercentage.col("account_number") === finalAlertedPackageInfo.col("account_number"),
      "left_outer"
    ).drop(customerSortPercentage.col("account_number"))
    
    SparkUtils.overwriteOrcTable(filteredFinalAlertedPackageInfo, "hdp_smp.filtered_epdi_notifications_" + typeOfAlerts)    
  }
  
  def determinePackagesSupplemental(hc : HiveContext, waitTimePackage : DataFrame, baselineWaitTimes : DataFrame, customerTable : DataFrame, customerSortPercentage : DataFrame, blacklist : DataFrame) = {
    import hc.implicits._
    //join in baseline to get the percentile
    val waitTimePackageWithBaseline = waitTimePackage.join(
      baselineWaitTimes, Seq("account_number", "hub_id", "day_of_week"),
      "left_outer"
    )
    
   //filter out < 1000 or no baseline
    val insufficientPackages = waitTimePackageWithBaseline.where($"percentile_hist_count" < minimumNumberOfPackages || $"percentile_hist_count".isNull)
        
    val insufficientPackagesWithTooLongWaitTimes = insufficientPackages.where($"current_wait_time" >= SparkUtils.calculateDaysInSeconds(14))
        
    val rolledUp = rollupPackage(hc, insufficientPackagesWithTooLongWaitTimes, "supplemental")
        
    val rolledUpWithBaseline = rolledUp.join(
      baselineWaitTimes, Seq("account_number", "hub_id", "day_of_week"),
      "left_outer"
    )
    
    val customerInfoRolledUp = rolledUpWithBaseline.join(
      customerTable, $"cust_nbr" === $"account_number",
      "left_outer"
    ).drop(customerTable.col("cust_nbr"))
        
    SparkUtils.overwriteOrcTable(customerInfoRolledUp, "hdp_smp.epdi_notifications_supplemental")
    
    val filteredCustomerInfoRolledUp = customerInfoRolledUp.where($"total_records_nbr" >= minimumNumberOfPackages)
    
    SparkUtils.overwriteOrcTable(filteredCustomerInfoRolledUp, "hdp_smp.filtered_epdi_notifications_supplemental")
  }
  
  def rollupPackage(hc: HiveContext, dataFrame : DataFrame, tempTable : String) : DataFrame = {
    dataFrame.registerTempTable(tempTable)
    
    return hc.sql(
      "SELECT hub_id, account_number, day_of_week " +
      ",to_date(from_unixtime(unix_event_timestamp)) as event_date" +
      ",round(percentile(current_wait_time, 0.5)) as median_wait_time " +
      ",max(current_wait_time) as max_wait_time " +
      ",count(*) as total_records_nbr " +
      "FROM " + tempTable + " " +
      "group by hub_id, account_number, day_of_week, to_date(from_unixtime(unix_event_timestamp)) " +
      "order by hub_id, account_number, day_of_week, to_date(from_unixtime(unix_event_timestamp))"
    )
  }
}