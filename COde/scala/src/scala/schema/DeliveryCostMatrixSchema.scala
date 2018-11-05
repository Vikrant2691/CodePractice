package com.fedex.smartpost.spark.schema

import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.types.StructField
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.types.DecimalType

object DeliveryCostMatrixSchema {
  val schema = StructType(Array(
      StructField("postal_code", StringType, true),
      StructField("variable_pkg_cost", DecimalType(32,2), true),
      StructField("1_pkg", DecimalType(32,2), true),
      StructField("2_pkg", DecimalType(32,2), true),
      StructField("3_pkg", DecimalType(32,2), true),
      StructField("4_pkg", DecimalType(32,2), true),
      StructField("5_pkg", DecimalType(32,2), true),
      StructField("6_pkg", DecimalType(32,2), true),
      StructField("7_pkg", DecimalType(32,2), true),
      StructField("8_pkg", DecimalType(32,2), true),
      StructField("9_pkg", DecimalType(32,2), true),
      StructField("10_pkg", DecimalType(32,2), true),
      StructField("11_pkg", DecimalType(32,2), true),
      StructField("12_pkg", DecimalType(32,2), true),
      StructField("version", StringType, true)
    ))
}