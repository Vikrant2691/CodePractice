package com.fedex.smartpost.spark.schema

import org.apache.spark.sql.types.TimestampType
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.LongType
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.types.StructField
import com.fedex.smartpost.spark.util.DateTimeUtils

object GSIPostalOrdCreateSchema {
  val schema = StructType(Array(
    StructField("unvsl_pkg_nbr", LongType, true),
    StructField("intnd_dlvr_ntwk_desc", StringType, true),
    StructField("event_tz_tmstp", TimestampType, true),
    StructField("pstl_cd", StringType, true),
    StructField("custnm", StringType, true),
    StructField("shpr_acct_nbr", LongType, true)))

  val mapRowFunction = (arrStr: Array[String]) => {
    val timestampWithTzFormat = "yyyy-MM-dd HH:mm:ss.SSS"
    Row(
      (if (arrStr(0).isEmpty() || !arrStr(0).matches("[-+]?\\d*\\.?\\d+")) "0" else arrStr(0)).toLong,
      arrStr(1),
      DateTimeUtils.toSqlTimestamp(arrStr(2), timestampWithTzFormat),
      arrStr(3),
      arrStr(4),
      (if (arrStr(5).isEmpty() || !arrStr(5).matches("[-+]?\\d*\\.?\\d+")) "0" else arrStr(5)).toLong)
  }
}