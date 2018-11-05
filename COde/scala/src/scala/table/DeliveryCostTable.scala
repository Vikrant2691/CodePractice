package com.fedex.smartpost.spark.table

import org.apache.spark.sql.SQLContext
import com.fedex.smartpost.spark.schema.DeliveryCostSchema
import org.apache.spark.sql.types.StructField
import com.fedex.smartpost.spark.table.util.TableUtils
import com.fedex.smartpost.spark.util.SparkUtils
import org.apache.spark.sql.hive.HiveContext

object DeliveryCostTable {
  val tableName = "hdp_smp.delivery_cost_orc"
  val partitionBy = "file_create_date"
  
  def main(args: Array[String]) {
    val sparkContext = SparkUtils.createContext("deliveryCostTable")
    val sqlContext = new HiveContext(sparkContext)
    
    TableUtils.dropTableIfExists(sqlContext, tableName)
    TableUtils.createExternalTable(sqlContext, tableName, DeliveryCostSchema.schema, partitionBy)
  }
}