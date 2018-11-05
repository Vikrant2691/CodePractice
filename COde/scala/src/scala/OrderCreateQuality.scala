package com.fedex.smartpost.spark

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.Column
import org.apache.spark.sql.SaveMode
import org.apache.spark.sql.functions._
import org.apache.spark.sql.functions
import org.apache.spark.sql.functions.col
import org.apache.spark.sql.functions.count
import org.apache.spark.sql.functions.countDistinct
import org.apache.spark.sql.functions.current_timestamp
import org.apache.spark.sql.functions.sum
import org.apache.spark.sql.functions.when
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.sql.types.IntegerType

import com.fedex.smartpost.spark.dao.CustomerDao
import com.fedex.smartpost.spark.dao.FirstHubTouchDao
import com.fedex.smartpost.spark.dao.OrderCreateDao
import com.fedex.smartpost.spark.util.OrderCreateUtils
import com.fedex.smartpost.spark.util.SparkUtils
import com.fedex.smartpost.spark.util.MathUtils
import com.fedex.smartpost.spark.util.CommandLineUtils

object OrderCreateQualityMain {
  def main(args: Array[String]) = {

    val sc = SparkUtils.createContext("OrderCreateQualityMain")

    val hc = new HiveContext(sc)
    import hc.implicits._
    hc.sql("use hdp_smp")

  val startDate = CommandLineUtils.getStartDate(args)
    val endDate = CommandLineUtils.getEndDate(args)

    val orderCreateTableAll = new OrderCreateDao(hc).fetchOrderCreateAll(startDate, endDate)
    val dupeScans = orderCreateTableAll.select($"upn", $"account_number").groupBy($"account_number" as "account_number").agg(count($"upn") - countDistinct($"upn") as "extra_order_creates")

    val orderCreates = OrderCreateUtils.filterEarliestUpns(orderCreateTableAll)

    val firstHubTouch = new FirstHubTouchDao(hc).fetchFirstHubTouchTable()
    val ocScanJoin = orderCreates.join(firstHubTouch, Seq("upn"), "left_outer")
      .withColumn("correct_hub", when(firstHubTouch.col("hub_id") === orderCreates.col("hub_id"), 1).otherwise(0))
      .groupBy($"account_number" as "account_number")
      .agg(count(orderCreates.col("upn")) as "oc_records", sum($"correct_hub") as "correct_hub")

    val ocDupeJoin = dupeScans.join(ocScanJoin, "account_number")

    val customerTable = new CustomerDao(hc).fetchCustomerTable()

    val orderCreateQuality = customerTable.join(
      ocDupeJoin,
      ocDupeJoin.col("account_number") === customerTable.col("cust_nbr")).drop("cust_nbr")
      .withColumn("correct_pct", MathUtils.calcPercentage(col("correct_hub"), col("oc_records")))
      .withColumn("extra_oc_pct", MathUtils.calcPercentage(col("extra_order_creates"), col("oc_records")))

    //val reordered = orderCreateQuality.select("global_entity_id", "global_entity_nm", "account_number", "oc_records", "correct_hub", "extra_order_creates", "correct_pct", "extra_oc_pct", "insert_timestamp")
    //reordered.write.mode(SaveMode.Overwrite).orc("order_create_quality")
       SparkUtils.overwriteOrcTable(orderCreateQuality, "hdp_smp.order_create_quality")
  }

}