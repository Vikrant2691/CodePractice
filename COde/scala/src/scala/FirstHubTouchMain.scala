package com.fedex.smartpost.spark

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.sql.functions._
import com.fedex.smartpost.spark.dao.OrderCreateDao
import com.fedex.smartpost.spark.dao.FirstHubTouchDao
import com.fedex.smartpost.spark.dao.MovementDao
import com.fedex.smartpost.spark.dao.FxgTrackingDao
import com.fedex.smartpost.spark.dao.FirstHubTouchDao
import com.fedex.smartpost.spark.dao.SystemParameterDao
import com.fedex.smartpost.spark.util.SparkUtils
import org.apache.spark.sql.DataFrame
import com.fedex.smartpost.spark.util.DateTimeUtils

object FirstHubTouchMain {
  def main(args: Array[String]) = {
    val sc = SparkUtils.createContext("FirstHubTouchMain")
    val hc = new HiveContext(sc)
    import hc.implicits._

    var sysParams = new SystemParameterDao(hc)
       
    val startDate = DateTimeUtils.getDaysAgo(sysParams.fetchSystemParameterIntValue("FIRST_HUB_TOUCH_START_DAYS_AGO"))
    val endDate = DateTimeUtils.getDaysAgo(sysParams.fetchSystemParameterIntValue("FIRST_HUB_TOUCH_END_DAYS_AGO"))
   
    val movementTable = new MovementDao(hc).fetchMovementTable(startDate, endDate).as("movement")
    val ftmTable = new FxgTrackingDao(hc).fetchDomesticArrivalEvents(startDate, endDate).as("ftm")
    val allScans = movementTable.unionAll(ftmTable)
    val firstHubTouchUpnTime = allScans.groupBy("upn").agg(min("scan_time") as "scan_time")

    val firstHubTouch = firstHubTouchUpnTime.join(allScans, Seq("upn", "scan_time"))
    
    SparkUtils.overwriteOrcTable(firstHubTouch, "hdp_smp.first_hub_touch")
  }

}