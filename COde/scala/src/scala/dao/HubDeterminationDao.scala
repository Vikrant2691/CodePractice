package com.fedex.smartpost.spark.dao

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.hive.HiveContext

class HubDeterminationDao(sqlContext: HiveContext) {
  def fetchHubDeterminationTable() : DataFrame = {
    val hubDeterminationTable = sqlContext.sql(
      "SELECT account_number, " +
        "destination_sort_code, " +
        "expected_hub, " +
        "most_likely_hub_id " +
      "FROM hdp_smp.hub_determination "
    )
    
    return hubDeterminationTable
  }
}