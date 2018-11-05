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
import com.fedex.smartpost.spark.util.SparkUtils
import org.apache.spark.sql.DataFrame
import com.fedex.smartpost.spark.util.CommandLineUtils
import com.fedex.smartpost.spark.dao.PostalEventDao

object TransitEndMain {
  def main(args: Array[String]) = {
    val sc = SparkUtils.createContext("TransitEndMain")
    val hc = new HiveContext(sc)

    hc.sql("use hdp_smp")

    val startDate = CommandLineUtils.getStartDate(args)
    val endDate = CommandLineUtils.getEndDate(args)
    val orderCreateTables = getOrderCreateTable(args, hc)
    val firstHubTouches = new FirstHubTouchDao(hc).fetchFirstHubTouchTable().as("hub_touch").withColumnRenamed("hub_id", "first_hub_id").withColumnRenamed("scan_time", "start_time_utc")
    val ocFirstTouch = orderCreateTables.join(firstHubTouches, Seq("upn"), "inner")

    val endScans = getEndScans(startDate, endDate, hc)
    val timeInTransit = endScans.join(ocFirstTouch, Seq("upn"), "inner").withColumn("insert_timestamp", current_timestamp())

    //timeInTransit.show()
    SparkUtils.overwriteOrcTable(timeInTransit, "hdp_smp.transit_time")
  }

  def getEndScans(startDate: String, endDate: String, sqlContext: HiveContext): DataFrame = {

    val postalTable = new PostalEventDao(sqlContext).getFirstPostalEndScans(startDate, endDate).as("postalEvent")
    val ftmTable = new FxgTrackingDao(sqlContext).getFirstDeliveryScans(startDate, endDate).as("ftm")
    val bothEndScans = postalTable.unionAll(ftmTable)
    bothEndScans.groupBy("upn", "end_event_type", "end_event_code").agg(min("end_time_utc").as("end_time_utc"))
  }

  def getOrderCreateTable(args: Array[String], sqlContext: HiveContext): DataFrame =
    {

      var sqlSelect = "SELECT distinct(upn), account_number FROM hdp_smp.order_create_orc "

      if (args.length > 1) {
        val startDate = args(0)
        val endDate = args(1)

        if (startDate != null) {
          sqlSelect += "where file_create_date >= '" + startDate + "' "
        }

        if (endDate != null) {
          sqlSelect += "AND file_create_date <= '" + endDate + "' "
        }

      }
      return sqlContext.sql(sqlSelect)
    }
}