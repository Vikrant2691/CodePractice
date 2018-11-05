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

object TestOC {
  def main(args: Array[String]) {

    val sc = SparkUtils.createContext("TestOC")
    val sqlContext = new HiveContext(sc)
    sqlContext.sql("use hdp_smp")

    import sqlContext.implicits._
    
    val startDate = "20161027"
    val endDate = "20161028"

    // Go back a little further for postal and ftm events
    val startDatePartition = "20161026"
    val endDatePartition = "20161028"

    val orderCreateTable = new OrderCreateDao(sqlContext).fetchAllOrderCreateTable(startDate, endDate, startDatePartition, endDatePartition)
    
    //orderCreateTable.write.format("json").save("/user/f885871/oc");
  }
}