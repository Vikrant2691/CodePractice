package com.fedex.smartpost.spark.util

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.min
import com.fedex.smartpost.spark.domain.OrderCreate

object OrderCreateUtils {
  def filterEarliestUpns(orderCreateTable: DataFrame): DataFrame = {
    orderCreateTable.groupBy(
      "account_number",
      "hub_id",
      "destination_sort_code",
      "event_dt",
      "day_of_week",
      "upn"
    ).agg(min("unix_event_timestamp")).drop("event_time_local_utc").withColumnRenamed("min(unix_event_timestamp)", "unix_event_timestamp")
  }
}