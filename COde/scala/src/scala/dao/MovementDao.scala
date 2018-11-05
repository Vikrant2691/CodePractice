package com.fedex.smartpost.spark.dao

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.hive.HiveContext

class MovementDao(sqlContext: HiveContext) {
  def fetchMovementTable(startDate: String = null, endDate: String = null): DataFrame = {

    var sqlSelect = "SELECT distinct upn, hub_code as hub_id, pkg_event_timestamp_utc as scan_time  " +
      "FROM hdp_smp.movement_orc " +
      "WHERE smartpst_product_code='DOM' and hub_code != '0'"

    if (startDate != null) {
      sqlSelect += "AND file_create_date >= '" + startDate + "' "
    }

    if (endDate != null) {
      sqlSelect += "AND file_create_date <= '" + endDate + "' "
    }

    return sqlContext.sql(sqlSelect)
  }
}