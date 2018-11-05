package com.fedex.smartpost.spark.dao

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions._
import com.fedex.smartpost.spark.util.OrderCreateUtils
import org.apache.spark.sql.expressions.Window
import com.fedex.smartpost.spark.domain.OrderCreate
import com.fedex.smartpost.spark.util.reduce.OrderCreateReduce
import com.fedex.smartpost.spark.util.reduce.OrderCreateReduce
import org.apache.spark.sql.SQLContext

class OrderCreateDao(sqlContext: SQLContext) {

  import sqlContext.implicits._
   
  def fetchEarliestOrderCreate(startDate: String = null, endDate: String = null, startDatePartition : String = null, endDatePartition : String = null): DataFrame = {
    val orderCreateTable = fetchOrderCreateTable(startDate, endDate, startDatePartition, endDatePartition)

    OrderCreateUtils.filterEarliestUpns(orderCreateTable)
  }
  
  def fetchOrderCreateAll(startDate: String = null, endDate: String = null, startDatePartition : String = null, endDatePartition : String = null): DataFrame = {
    return fetchOrderCreateTable(startDate, endDate, startDatePartition, endDatePartition)
  }

  def fetchOrderCreateTable(startDate: String = null, endDate: String = null, startDatePartition : String = null, endDatePartition : String = null): DataFrame = {
   
    var sqlSelect = "SELECT upn, " +
        "account_number, " +
        "substring(destination_sort_code, 0, 5) as destination_sort_code, " +
        "hub_id, " +
        "event_time_local_utc, " +
        "date_format(event_time_local_utc, 'yyyyMMdd') as event_dt, " +
        "date_format(event_time_local_utc, 'EEEE') as day_of_week " +
        "FROM hdp_smp.order_create_orc " +
        "WHERE product_type <> 'RTN' "
        
    if (startDatePartition != null) {
      sqlSelect += "AND file_create_date >= '" + startDatePartition + "' "   
    }
    
    if (endDatePartition != null) {
      sqlSelect += "AND file_create_date <= '" + endDatePartition + "' "
    }
    
    if (startDate != null) {
      sqlSelect += "AND date_format(event_time_local_utc, 'yyyyMMdd') >= '" + startDate + "' "   
    }
    
    if (endDate != null) {
      sqlSelect += "AND date_format(event_time_local_utc, 'yyyyMMdd') <= '" + endDate + "' "
    }
    
    return sqlContext.sql(sqlSelect).withColumn("unix_event_timestamp", unix_timestamp($"event_time_local_utc"))
  }
  
   def fetchAllOrderCreateTable(startDate: String, endDate: String, startDatePartition : String, endDatePartition : String): Dataset[OrderCreate] = {
    var sqlSelect = "SELECT upn, " +
        "account_number, " +
        "pstl_bar_cd, " +
        "event_time_local_utc, " +
        "destination_sort_code, " +
        "recipient_share_id, " +
        "recipient_raw_address_id, " +
        "recipient_city, " +
        "recipient_state, " +
        "fxg_destination_id, " +
        "fhd_destination_id, " +
        "fxsp_destination_id " +
        "FROM order_create_orc " +
        "WHERE file_create_date >= '" + startDatePartition + "' " +  
        "AND file_create_date <= '" + endDatePartition + "' " +
        "AND date_format(event_time_local_utc, 'yyyyMMdd') >= '" + startDate + "' "  +
        "AND date_format(event_time_local_utc, 'yyyyMMdd') <= '" + endDate + "' "

    val oc = sqlContext.sql(sqlSelect).withColumn("unix_event_timestamp", unix_timestamp($"event_time_local_utc")).drop($"event_time_local_utc")
       
    val dsTop = oc.as[OrderCreate].groupBy($"upn").reduce(OrderCreateReduce.call(_,_)).map((rowArray) => rowArray._2)
       
    return dsTop
  }
}