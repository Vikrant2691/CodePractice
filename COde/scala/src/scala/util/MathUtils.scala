package com.fedex.smartpost.spark.util

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.SaveMode
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions
import org.apache.spark.sql.Column
import org.apache.spark.sql.types.IntegerType

object MathUtils {
 def calcPercentage(numerator: Column, divisor: Column): Column = {

    (functions.round(numerator.multiply(100).divide(divisor))).cast(IntegerType)

  }
}