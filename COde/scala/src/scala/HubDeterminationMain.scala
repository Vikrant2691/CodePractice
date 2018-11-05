package com.fedex.smartpost.spark

import org.apache.spark.sql.functions._
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.functions.count
import org.apache.spark.sql.functions.sum
import org.apache.spark.sql.functions.when
import org.apache.spark.sql.hive.HiveContext

import com.fedex.smartpost.spark.dao.FirstHubTouchDao
import com.fedex.smartpost.spark.dao.OrderCreateDao
import org.apache.spark.sql.functions
import org.apache.spark.sql.Column
import org.apache.spark.sql.types.IntegerType
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.SaveMode
import com.fedex.smartpost.spark.util.MathUtils
import com.fedex.smartpost.spark.util.SparkUtils
import com.fedex.smartpost.spark.util.CommandLineUtils

object HubDeterminationMain {
  def main(args: Array[String]) = {

    val sc = SparkUtils.createContext("HubDeterminationMain")
    val hc = new HiveContext(sc)
    import hc.implicits._
    hc.sql("use hdp_smp")

    val startDate = CommandLineUtils.getStartDate(args)
    val endDate = CommandLineUtils.getEndDate(args)
    val orderCreate = new OrderCreateDao(hc).fetchEarliestOrderCreate(startDate, endDate)
    val firstHubTouch = new FirstHubTouchDao(hc).fetchFirstHubTouchTable()
    val ocScanJoin = orderCreate.join(firstHubTouch, firstHubTouch.col("upn") === orderCreate.col("upn"))

    ocScanJoin.cache()
    
    val countByTriad = ocScanJoin
      .withColumn("correct_hub", when(firstHubTouch.col("hub_id") === orderCreate.col("hub_id"), 1).otherwise(0))
      .groupBy(orderCreate.col("account_number") as "account_number",
        $"destination_sort_code" as "destination_sort_code",
        orderCreate.col("hub_id") as "expected_hub")
      .agg(count(orderCreate.col("upn")) as "expected_num_pkgs", sum($"correct_hub") as "expected_hub_correct")

    val actualHub = ocScanJoin
      .groupBy(orderCreate.col("account_number") as "account_number",
        $"destination_sort_code" as "destination_sort_code",
        orderCreate.col("hub_id") as "expected_hub",
        firstHubTouch.col("hub_id") as "most_likely_hub_id")
      .agg(count(orderCreate.col("upn")) as "num_pkgs_most_likely_hub")

    val window = Window.partitionBy($"account_number", $"destination_sort_code", $"expected_hub").orderBy($"num_pkgs_most_likely_hub".desc)

    val mostLikelyHub = actualHub.withColumn("rn", rowNumber.over(window)).where($"rn" === 1).drop("rn")

    val hubDetermination = countByTriad.join(mostLikelyHub, countByTriad.col("account_number") <=> mostLikelyHub.col("account_number") && countByTriad.col("destination_sort_code") <=> mostLikelyHub.col("destination_sort_code") && countByTriad.col("expected_hub") <=> mostLikelyHub.col("expected_hub"))
      .withColumn("correct_pct_most_likely_hub", MathUtils.calcPercentage(col("num_pkgs_most_likely_hub"), col("expected_num_pkgs")))
      .withColumn("expected_correct_pct", MathUtils.calcPercentage(col("expected_hub_correct"), col("expected_num_pkgs")))
      .withColumn("insert_timestamp", current_timestamp())
      .drop(mostLikelyHub.col("account_number")).drop(mostLikelyHub.col("destination_sort_code")).drop(mostLikelyHub.col("expected_hub"))

    //val reordered = hubDetermination.select("account_number", "destination_sort_code", "expected_hub", "expected_num_pkgs", "expected_correct_pct", "most_likely_hub_id", "num_pkgs_most_likely_hub", "correct_pct_most_likely_hub", "insert_timestamp")

    SparkUtils.overwriteOrcTable(hubDetermination, "hdp_smp.hub_determination")
  }

}