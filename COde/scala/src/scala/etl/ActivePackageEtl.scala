package com.fedex.smartpost.spark.etl

import org.apache.commons.lang.StringUtils.trimToNull
import org.apache.spark.SparkContext
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.Row
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.functions._
import com.fedex.smartpost.spark.schema.ActivePackageSchema
import com.fedex.smartpost.etl.types.java.ParseNullableLong
import com.fedex.smartpost.etl.types.java.ParseNullableDouble
import com.fedex.smartpost.spark.util.DateTimeUtils


class ActivePackageEtl(sparkContext: SparkContext, sqlContext: SQLContext) {
  
  def convertFileToDataframe(filepath: String) : DataFrame = {     
    val fileRdd = sparkContext.textFile(filepath)
    val fileRddWithoutHeader = fileRdd.filter(row => !row.startsWith("SPB5"))
    
    val activePackageRdd = fileRddWithoutHeader.mapPartitions(mapPartition)
    
    return sqlContext.createDataFrame(activePackageRdd, ActivePackageSchema.schema).withColumn("inserttimestamp", current_timestamp())
  }  
  
  val mapPartition = (rows: Iterator[String]) => rows.map{
    (row: String) => {
      val timestampWithTzFormat = "yyyy-MM-dd HH:mm:ss.SSSZZ"
      val timestampFormat = "yyyy-MM-dd HH:mm:ss"
      val dateFormat = "yyyy-MM-dd"
      
      Row(
          ParseNullableLong.parseString(row.substring(0, 20)), // upn,
          DateTimeUtils.toSqlTimestamp(row.substring(20, 50), timestampWithTzFormat), // event_timestamp_utc
          DateTimeUtils.extractTimeZoneId(row.substring(20, 50), timestampWithTzFormat), // event_timezone
          trimToNull(row.substring(50, 85)), // postal_barcode                  
          trimToNull(row.substring(85, 101)), // fxg_tracking_id
          trimToNull(row.substring(101, 134)), // stop_id
          trimToNull(row.substring(134, 136)), // stop_id_type_cd
          trimToNull(row.substring(136, 143)), // stop_id_vintage_cd
          trimToNull(row.substring(143,  148)), // intended_delivery_network
          trimToNull(row.substring(148,  159)), // fxg_dest_facility
          trimToNull(row.substring(159,  170)), // fhd_dest_facility
          trimToNull(row.substring(170,  181)), // fxsp_dest_facility
          trimToNull(row.substring(181,  202)), // shipper_num
          trimToNull(row.substring(202,  204)), // transaction_flag
          ParseNullableDouble.parseString(row.substring(204,  213)), // pkg_weight
          trimToNull(row.substring(213,  215)), // oversize_flag
          DateTimeUtils.toSqlDate(row.substring(215, 226), dateFormat), // est_devlivery_date
          DateTimeUtils.toSqlDate(row.substring(226,  237), dateFormat), // est_facility_date
          DateTimeUtils.toSqlDate(row.substring(237,  248), dateFormat), // pickup_date
          trimToNull(row.substring(248,  254)), // swak_facility
          DateTimeUtils.toSqlTimestamp(row.substring(254, 274), timestampFormat), // swak_timestamp
          trimToNull(row.substring(274,  280)), // original_facility
          trimToNull(row.substring(280,  284)), // zip_11_confidence
          trimToNull(row.substring(284,  296)), // postal_code
          ParseNullableDouble.parseString(row.substring(296, 308)), // latitude
          ParseNullableDouble.parseString(row.substring(308, 320)), // longitude
          trimToNull(row.substring(320,  324)), // geo_code_score
          trimToNull(row.substring(324,  328)), // geo_vendor
          trimToNull(row.substring(328,  330)), // geo_code_valid
          trimToNull(row.substring(330,  334)), // highrise_indicator
          trimToNull(row.substring(334,  349)), // trailer_num
          ParseNullableDouble.parseString(row.substring(349, 357)), // pkg_length
          ParseNullableDouble.parseString(row.substring(357, 365)), // pkg_width
          ParseNullableDouble.parseString(row.substring(365, 373)), // pkg_height
          ParseNullableLong.parseString(row.substring(373, 385)), // pkgs_in_shipment
          trimToNull(row.substring(385, 396)), // product_offering
//          trimToNull(row.substring(396, 400)), // service_code
          null, // filename,
          null // inserttimestamp
      )
    }
  }
}