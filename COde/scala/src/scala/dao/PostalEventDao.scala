package com.fedex.smartpost.spark.dao

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._
import com.fedex.smartpost.spark.domain.PostalEvent
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.SQLContext

class PostalEventDao(sqlContext: SQLContext) {

  import sqlContext.implicits._

  def getUpnsByEventCode(eventCode: Array[String], startDate: String = null, endDate: String = null): DataFrame = {
    var sqlSelect = "SELECT distinct(upn) " +
      " FROM hdp_smp.postal_event_orc where postal_event_code in " + eventCode.mkString("('", "','", "') ")

    if (startDate != null) {
      sqlSelect += "AND file_create_date >= '" + startDate + "' "
    }

    if (endDate != null) {
      sqlSelect += "AND file_create_date <= '" + endDate + "' "
    }
    return sqlContext.sql(sqlSelect)
  }

  /*
  def addUpnEventIndicator(dfWithUpns: DataFrame, columnName: String, eventCode: Array[String], startDate: String = null, endDate: String = null): DataFrame = {
    val event = getUpnsByEventCode(eventCode, startDate, endDate)

    return dfWithUpns.join(event, event.col("upn") === dfWithUpns.col("upn"), "left_outer")
      .withColumn(columnName, when(event.col("upn") <=> null, 0).otherwise(1))
      .drop(event.col("upn"))
  }
*/
  // TODO - I only coded for 01 events.  Ideally we would use a list of event like OTSM does.  JRV 31Aug2016
  def getFirstPostalEndScans(startDate: String = null, endDate: String = null): DataFrame = {

    var sqlSelect =
      "SELECT upn, 'POSTAL' as end_event_type, min(event_timestamp_utc) as end_time_utc, postal_event_code as end_event_code " +
        "FROM hdp_smp.postal_event_orc " +
        "WHERE postal_event_code in ('01') "

    if (startDate != null) {
      sqlSelect += "AND file_create_date >= '" + startDate + "' "
    }

    if (endDate != null) {
      sqlSelect += "AND file_create_date <= '" + endDate + "' "
    }

    sqlSelect += "group by upn, postal_event_code"
    return sqlContext.sql(sqlSelect)
  }
  
  def getPostalEvents(startDate: String, endDate: String, startDatePartition: String, endDatePartition: String): Dataset[PostalEvent] = {

    var sqlSelect =
      "SELECT upn, event_timestamp_utc, postal_event_code, postal_code " +
        "FROM postal_event_orc " +
        "WHERE file_create_date >= '" + startDatePartition + "' " +
        "AND file_create_date <= '" + endDatePartition + "' " +
        "AND date_format(event_timestamp_utc, 'yyyyMMdd') >= '" + startDate + "' " +
        "AND date_format(event_timestamp_utc, 'yyyyMMdd') <= '" + endDate + "' "

    return sqlContext.sql(sqlSelect).withColumn("unix_event_timestamp", unix_timestamp($"event_timestamp_utc")).drop($"event_timestamp_utc").as[PostalEvent]
  }
}