package com.fedex.smartpost.spark.table

import org.apache.spark.sql.hive.HiveContext

import com.fedex.smartpost.spark.schema.DeliveryCostMatrixSchema
import com.fedex.smartpost.spark.table.util.TableUtils
import com.fedex.smartpost.spark.util.SparkUtils

object DeliveryCostMatrixTable {
  val tableName = "hdp_smp.delivery_cost_matrix_orc"
  
  def main(args: Array[String]) {
    val sparkContext = SparkUtils.createContext("deliveryCostMatrixTable")
    val sqlContext = new HiveContext(sparkContext)
    
    TableUtils.dropTableIfExists(sqlContext, tableName)
    TableUtils.createExternalTable(sqlContext, tableName, DeliveryCostMatrixSchema.schema)
  }
}