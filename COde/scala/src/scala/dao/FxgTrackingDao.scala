package com.fedex.smartpost.spark.dao

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.SQLContext
import com.fedex.smartpost.spark.domain.FxgTracking
import org.apache.spark.sql.functions._
import org.apache.spark.sql.Dataset

class FxgTrackingDao(sqlContext: SQLContext) {
  
  import sqlContext.implicits._
  
  def fetchDomesticArrivalEvents(startDate: String = null, endDate: String = null): DataFrame = {

    var sqlSelect = "SELECT distinct upn, nvl(facility_id_value, 'GROUND') as hub_id, ftm_event_datetime_utc as scan_time " +
      "FROM hdp_smp.fxg_tracking_orc " +
      "WHERE upn != 0 AND product_type_cd='DOM' and ftm_event_cd='AR' "

    if (startDate != null) {
      sqlSelect += "AND file_create_date >= '" + startDate + "' "
    }

    if (endDate != null) {
      sqlSelect += "AND file_create_date <= '" + endDate + "' "
    }
    return sqlContext.sql(sqlSelect)

  }

  def getDeliveredUpns(startDate: String = null, endDate: String = null): DataFrame = {

    var sqlSelect = "SELECT distinct(upn) " +
      "FROM hdp_smp.fxg_tracking_orc " +
      "WHERE upn != 0 AND ftm_event_cd='DL' "

    if (startDate != null) {
      sqlSelect += "AND file_create_date >= '" + startDate + "' "
    }

    if (endDate != null) {
      sqlSelect += "AND file_create_date <= '" + endDate + "' "
    }

    return sqlContext.sql(sqlSelect)

  }

  def getFirstDeliveryScans(startDate: String = null, endDate: String = null): DataFrame = {

    var sqlSelect = "SELECT distinct upn, nvl(facility_id_value, 'GROUND') as end_event_type, min(ftm_event_datetime_utc) as end_time_utc, ftm_event_cd as end_event_code " +
      "FROM hdp_smp.fxg_tracking_orc " +
      "WHERE upn != 0 AND ftm_event_cd='DL' "

    if (startDate != null) {
      sqlSelect += "AND file_create_date >= '" + startDate + "' "
    }

    if (endDate != null) {
      sqlSelect += "AND file_create_date <= '" + endDate + "' "
    }
    sqlSelect += "group by upn, ftm_event_cd"

    return sqlContext.sql(sqlSelect)

  }
  
   def getEventsPerUpn(startDate: String, endDate: String, startDatePartition : String, endDatePartition : String): DataFrame = {

    var sqlSelect = "SELECT upn, ftm_event_datetime_utc as event_time_local_utc, ftm_event_cd as event_code " +
      "FROM fxg_tracking_orc " +
      "WHERE upn != 0 " +
      "AND file_create_date >= '" + startDate + "' " +
      "AND file_create_date <= '" + endDate + "' " +
      "AND date_format(ftm_event_datetime_utc, 'yyyyMMdd') >= '" + startDatePartition + "' "  +
      "AND date_format(ftm_event_datetime_utc, 'yyyyMMdd') <= '" + endDatePartition + "' "

    return sqlContext.sql(sqlSelect).withColumn("unix_event_timestamp", unix_timestamp($"event_time_local_utc")).drop($"event_time_local_utc")

  }
   
   def getFirstEventPerUpn(startDate: String, endDate: String, startDatePartition : String, endDatePartition : String): Dataset[FxgTracking] = {

    val events = getEventsPerUpn(startDate, endDate, startDatePartition, endDatePartition);
    
    val eventsDataSet = events.as[FxgTracking]
       
    return eventsDataSet.groupBy($"upn").reduce((a, b) => if (a.unix_event_timestamp < b.unix_event_timestamp) a else b).map((rowArray) => rowArray._2)
  }
   
  def getLatestEventPerUpn(startDate: String, endDate: String, startDatePartition : String, endDatePartition : String): Dataset[FxgTracking] = {

    val events = getEventsPerUpn(startDate, endDate, startDatePartition, endDatePartition);
    
    val eventsDataSet = events.as[FxgTracking]
       
    return eventsDataSet.groupBy($"upn").reduce((a, b) => if (a.unix_event_timestamp > b.unix_event_timestamp) a else b).map((rowArray) => rowArray._2)
  }
  

}