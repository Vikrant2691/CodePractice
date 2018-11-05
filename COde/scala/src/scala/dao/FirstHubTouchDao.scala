package com.fedex.smartpost.spark.dao

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.hive.HiveContext

import scala.reflect.io.Path
import org.apache.spark.sql.SaveMode
import org.uncommons.maths.statistics.DataSet
import org.apache.spark.sql.Dataset
import com.fedex.smartpost.spark.domain.FirstHubTouch

class FirstHubTouchDao(sqlContext: HiveContext) {
  import sqlContext.implicits._
  
  def fetchFirstHubTouchTable(): DataFrame = {
    val firstHubTouchTable = sqlContext.sql(
      "SELECT upn, " +
        "hub_id, " +
        "scan_time " +
        "FROM hdp_smp.first_hub_touch ")

    return firstHubTouchTable
  }
  
  def fetchFirtHubTouchTableDataset() : Dataset[FirstHubTouch] = {
    return fetchFirstHubTouchTable().as[FirstHubTouch]
  }

}