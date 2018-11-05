package com.fedex.smartpost.spark.schema

import org.apache.spark.sql.types.DecimalType
import org.apache.spark.sql.types.LongType
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.types.StructField
import org.apache.spark.sql.types.StructType

object DeliveryCostSchema {
  val schema = StructType(Array(
        StructField("location_type", StringType, true),
        StructField("postal_code", StringType, true),
        StructField("package_count", LongType, true),
        StructField("delivery_stop_count", DecimalType(15, 4), true),
        StructField("variable_fuel_cost", DecimalType(15, 4), true),
        StructField("variable_non_fuel_cost", DecimalType(15, 4), true),
        StructField("fixed_non_fuel_cost", DecimalType(15, 4), true)
    ))
}