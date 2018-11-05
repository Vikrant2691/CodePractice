package com.fedex.smartpost.spark.schema

import org.apache.spark.sql.Row
import org.apache.spark.sql.types.LongType
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.types.StructField
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.types.TimestampType

import com.fedex.smartpost.spark.util.DateTimeUtils

object GSIPostalFTMSchema {

  val schema = StructType(Array(
    StructField("unvsl_pkg_nbr", LongType, true),
    StructField("loc_fac_id_value_cd", StringType, true),
    StructField("loc_fac_type_cd", StringType, true),
    StructField("loc_fac_co_nm", StringType, true),
    StructField("prodtype", StringType, true),
    StructField("mailclass", StringType, true),
    StructField("mailclass_desc", StringType, true),
    StructField("dldate", TimestampType, true)))

  val mapRowFunction = (arrStr: Array[String]) => {
    val timestampWithTzFormat = "yyyy-MM-dd HH:mm:ss.SSS"
    Row(
      (if (arrStr(0).isEmpty() || !arrStr(0).matches("[-+]?\\d*\\.?\\d+")) "0" else arrStr(0)).toLong,
      arrStr(1),
      arrStr(2),
      arrStr(3),
      arrStr(4),
      arrStr(5),
      arrStr(6),
      (if (arrStr(7) == null || arrStr(7).isEmpty() || arrStr(7).equalsIgnoreCase("null") ) null else DateTimeUtils.toSqlTimestamp(arrStr(7), timestampWithTzFormat)))
  }
}