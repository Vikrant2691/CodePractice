package com.fedex.smartpost.spark.schema

import org.apache.spark.sql.Row
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.types.StructField
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.types.LongType
import org.apache.spark.sql.types.DoubleType
import java.sql.Timestamp
import org.apache.spark.sql.types.TimestampType
import com.fedex.smartpost.spark.util.DateTimeUtils

object PstgGsiApCountsSchema {
  val timestampWithTzFormat = "yyyy-MM-dd HH:mm:ss.SSS"
  val timestampFormat = "yyyy-MM-dd HH:mm:ss"
  val dateFormat = "yyyy-MM-dd"

  val schema = StructType(Array(
    StructField("shpr_acct_nbr", LongType, true), //6117414
    StructField("event_date_dt", TimestampType, true), //2017-01-24 16:02:13.933
    StructField("intnd_dlvr_ntwk_desc", StringType, true), //FXSP
    StructField("office_wsc_pkg_count", DoubleType, true), //0
    StructField("fxg_pkg_count", DoubleType, true), //1
    StructField("fhd_pkg_count", DoubleType, true), //0
    StructField("fxsp_pkg_count", DoubleType, true), //0
    StructField("usps_pkg_count", DoubleType, true), //0
    StructField("file_create_date", StringType, true))) //20170124

  val mapRowFunction = (arrStr: Array[String]) => {
    Row((if (arrStr(0).isEmpty() || !arrStr(0).matches("[-+]?\\d*\\.?\\d+")) "0" else arrStr(0)).toLong, //6117414
      DateTimeUtils.toSqlTimestamp(arrStr(1), timestampWithTzFormat), //2017-01-24 16:02:13.933
      arrStr(2), //FXSP
      (if (arrStr(3).isEmpty() || !arrStr(3).matches("[-+]?\\d*\\.?\\d+")) "0" else arrStr(3)).toDouble, //0
      (if (arrStr(4).isEmpty() || !arrStr(4).matches("[-+]?\\d*\\.?\\d+")) "0" else arrStr(4)).toDouble, //1
      (if (arrStr(5).isEmpty() || !arrStr(5).matches("[-+]?\\d*\\.?\\d+")) "0" else arrStr(5)).toDouble, //0
      (if (arrStr(6).isEmpty() || !arrStr(6).matches("[-+]?\\d*\\.?\\d+")) "0" else arrStr(6)).toDouble, //0
      (if (arrStr(7).isEmpty() || !arrStr(7).matches("[-+]?\\d*\\.?\\d+")) "0" else arrStr(7)).toDouble, //0
      arrStr(8)) //20170124
  }
}
