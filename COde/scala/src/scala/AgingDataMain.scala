package com.fedex.smartpost.spark

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.functions.current_timestamp
import org.apache.spark.sql.functions.when
import org.apache.spark.sql.functions._
import org.apache.spark.sql.hive.HiveContext

import com.fedex.smartpost.spark.dao.HubDeterminationDao
import org.apache.spark.sql.DataFrame
import com.fedex.smartpost.spark.dao.CustomerDao
import com.fedex.smartpost.spark.dao.FirstHubTouchDao
import com.fedex.smartpost.spark.dao.OrderCreateDao
import com.fedex.smartpost.spark.dao.PostalEventDao
import com.fedex.smartpost.spark.dao.SystemParameterDao
import com.fedex.smartpost.spark.util.SparkUtils
import com.fedex.smartpost.spark.util.DateTimeUtils
import com.fedex.smartpost.spark.dao.FxgTrackingDao

object AgingDataMain {
  def main(args: Array[String]) {

    val sc = SparkUtils.createContext("AgingData")
    val sqlContext = new HiveContext(sc)
    sqlContext.sql("use hdp_smp")

    import sqlContext.implicits._

    var sysParams = new SystemParameterDao(sqlContext)

    val sysParamStartDate = sysParams.fetchSystemParameterIntValue("AGING_START_DAYS_AGO");
    val sysParamEndDate = sysParams.fetchSystemParameterIntValue("AGING_END_DAYS_AGO");

    val sysParamStartDatePartition = sysParams.fetchSystemParameterIntValue("AGING_START_PARTITION_DAYS_AGO");
    val sysParamEndDatePartition = sysParams.fetchSystemParameterIntValue("AGING_END_PARTITION_DAYS_AGO");

    val startDate = DateTimeUtils.getDaysAgo(sysParamStartDate)
    val endDate = DateTimeUtils.getDaysAgo(sysParamEndDate)

    // Go back a little further for postal and ftm events
    val startDatePartition = DateTimeUtils.getDaysAgo(sysParamStartDate + sysParamStartDatePartition + 20)
    val endDatePartition = DateTimeUtils.getDaysAgo(sysParamEndDate + sysParamEndDatePartition)

    val orderCreateTable = new OrderCreateDao(sqlContext).fetchEarliestOrderCreate(startDate, endDate, startDatePartition, endDatePartition)
      .as("order_create")
      .drop("min(unix_event_timestamp)")

    val ocScanJoin = addHubScanData(orderCreateTable, sqlContext)
    val stcUpns = getStcUpns(sqlContext, startDatePartition, endDatePartition)
    val ocStcJoin = addUpnEventIndicator(ocScanJoin, stcUpns, "stc_scan")

    val aauUpns = getPostalAauUpns(sqlContext, startDatePartition, endDatePartition)

    val ocStcAau = addUpnEventIndicator(ocStcJoin, aauUpns, "aau_scan")

    val groupedData = ocStcAau.groupBy(
      "account_number",
      "hub_id",
      "event_dt",
      "day_of_week")
      .agg(count(ocStcAau.col("upn")) as "oc_records",
        sum($"sort_scan") as "scan_records",
        sum($"aau_scan") as "aau_records",
        sum($"stc_scan") as "stc_records")

    val groupedDataWithCustInfo = addCustomerData(groupedData, sqlContext)

    //groupedDataWithCustInfo.show()
    SparkUtils.overwriteOrcTable(groupedDataWithCustInfo, "hdp_smp.aging_report")

  }

  def addHubScanData(dfWithUpns: DataFrame, sqlContext: HiveContext): DataFrame = {
    val firstHubTable = new FirstHubTouchDao(sqlContext).fetchFirstHubTouchTable().as("hub_touch")
      .withColumnRenamed("hub_id", "first_scan_hub_id")
      .drop("scan_time")

    val upnsWithScan = dfWithUpns.join(firstHubTable, Seq("upn"), "left_outer")
      .drop(firstHubTable.col("upn"))
      .withColumn("sort_scan", when(firstHubTable.col("first_scan_hub_id") <=> null, 0).otherwise(1))

    val hubDeterminationTable = new HubDeterminationDao(sqlContext).fetchHubDeterminationTable()
      .as("hub_determine")

    val scanAndHub = upnsWithScan.join(
      hubDeterminationTable, upnsWithScan.col("account_number") <=> hubDeterminationTable.col("account_number") && upnsWithScan.col("destination_sort_code") <=> hubDeterminationTable.col("destination_sort_code") && upnsWithScan.col("hub_id") <=> hubDeterminationTable.col("expected_hub"), "left_outer")
      .withColumn("probable_hub_id", when(hubDeterminationTable.col("most_likely_hub_id") <=> null, upnsWithScan.col("hub_id"))
        .otherwise(hubDeterminationTable.col("most_likely_hub_id")))
      .drop(hubDeterminationTable.col("account_number"))
      .drop(hubDeterminationTable.col("destination_sort_code"))
      .drop(hubDeterminationTable.col("expected_hub"))
      .drop(upnsWithScan.col("hub_id"))
      .drop(hubDeterminationTable.col("most_likely_hub_id"))

    val hubData = scanAndHub.withColumn("hub_id", when(scanAndHub.col("first_scan_hub_id") <=> null, scanAndHub.col("probable_hub_id"))
      .otherwise(scanAndHub.col("first_scan_hub_id")))
      .drop(scanAndHub.col("first_scan_hub_id"))
      .drop(scanAndHub.col("probable_hub_id"))

    return hubData
  }

  def getStcUpns(sqlContext: HiveContext, startPartition: String, endPartition: String): DataFrame = {

    val postalStcUpns = getPostalStcUpns(sqlContext, startPartition, endPartition)
    val ftmStcUpns = new FxgTrackingDao(sqlContext).getDeliveredUpns(startPartition, endPartition)
    return postalStcUpns.unionAll(ftmStcUpns).distinct()
  }

  def getPostalStcUpns(sqlContext: HiveContext, startPartition: String, endPartition: String): DataFrame = {

    val eventCodes = Array("21", "56", "09", "14", "53", "23", "22", "04", "52", "54", "05", "24", "06", "29", "02", "57", "01", "55", "51", "26", "25", "28")
    return new PostalEventDao(sqlContext).getUpnsByEventCode(eventCodes, startPartition, endPartition)
  }

  def getPostalAauUpns(sqlContext: HiveContext, startPartition: String, endPartition: String): DataFrame = {

    val eventCodes = Array("07")
    return new PostalEventDao(sqlContext).getUpnsByEventCode(eventCodes, startPartition, endPartition)
  }

  def addUpnEventIndicator(originalFrame: DataFrame, eventFrame: DataFrame, columnName: String): DataFrame = {

    return originalFrame.join(eventFrame, eventFrame.col("upn") === originalFrame.col("upn"), "left_outer")
      .withColumn(columnName, when(eventFrame.col("upn") <=> null, 0).otherwise(1))
      .drop(eventFrame.col("upn"))
  }

  def addCustomerData(dfWithAcctNumber: DataFrame, sqlContext: HiveContext): DataFrame = {

    val customerTable = new CustomerDao(sqlContext).fetchCustomerTable()

    return dfWithAcctNumber.join(
      customerTable,
      dfWithAcctNumber.col("account_number") <=> customerTable.col("cust_nbr"),"left_outer").drop("cust_nbr")

  }
}