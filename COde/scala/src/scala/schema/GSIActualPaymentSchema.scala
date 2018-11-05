package com.fedex.smartpost.spark.schema

import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.LongType
import org.apache.spark.sql.types.DoubleType
import com.fedex.smartpost.spark.util.DateTimeUtils
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.types.StructField

object GSIActualPaymentSchema {  
  val schema = StructType(Array(
    StructField("unvsl_pkg_nbr", LongType, true),
    StructField("ovsz_flg", StringType, true),
    StructField("blln_flg", StringType, true),
    StructField("count_total", DoubleType, true),
    StructField("actl_pstl_chrg_amt", DoubleType, true)))

  val mapRowFunction = (arrStr: Array[String]) => {    
    Row((if (arrStr(0).isEmpty() || !arrStr(0).matches("[-+]?\\d*\\.?\\d+")) "0" else arrStr(0)).toLong,
      arrStr(1),
      arrStr(2),
      (if (arrStr(3).isEmpty() || !arrStr(3).matches("[-+]?\\d*\\.?\\d+")) "0" else arrStr(3)).toDouble,
      (if (arrStr(4).isEmpty() || !arrStr(4).matches("[-+]?\\d*\\.?\\d+")) "0" else arrStr(4)).toDouble)
  }
}