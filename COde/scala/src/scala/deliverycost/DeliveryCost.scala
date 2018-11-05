package com.fedex.smartpost.spark.deliverycost

import org.apache.spark.SparkContext
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.functions._

import com.fedex.smartpost.spark.schema.DeliveryCostSchema

class DeliveryCost(sparkContext: SparkContext, sqlContext: SQLContext) {

  import sqlContext.implicits._
  
  val POSTAL_CODE = "postal_code"
  val VARIABLE_PKG_COST = "variable_pkg_cost"
  val VARIABLE_FUEL_COST = "variable_fuel_cost"
  val VARIABLE_NON_FUEL_COST = "variable_non_fuel_cost"
  val FIXED_NON_FUEL_COST = "fixed_non_fuel_cost"
  val PKG_COUNT = "package_count"  
  val SINGLE_PKG_COST = "1_pkg"
  val VERSION = "version"
  
  def loadDeliveryCostFile(filepath: String, fileVersion: String) : DataFrame = {    
    val deliveryCosts = sqlContext.read.format("com.databricks.spark.csv")
      // Use first row as header, so it won't be included in loaded data
      .option("header", "true")
      .schema(DeliveryCostSchema.schema)
      .load(filepath)
      
    return deliveryCosts.withColumn(VERSION, lit(fileVersion))
  }
  
  def createDeliveryCostMatrix(deliveryCost1Pkg: DataFrame, deliveryCost2Pkg: DataFrame, n: Int) : DataFrame = {
    val version = deliveryCost1Pkg.first().getAs[String](VERSION)
    val allZips = sqlContext.range(1, 100000).withColumnRenamed("id", POSTAL_CODE)
    
    val joinedDeliveryCost = deliveryCost1Pkg.as("tab_1").join(deliveryCost2Pkg.as("tab_2"), POSTAL_CODE)
      .drop(col("tab_1." + VERSION))
      .drop(col("tab_2." + VERSION))
    
    // Calculate variable cost based on 1 & 2 package densities
    val variablePkgCostTab1 = round($"tab_1.variable_non_fuel_cost" / $"tab_1.delivery_stop_count", 2)
    val variablePkgCostTab2 = round($"tab_2.variable_non_fuel_cost" / $"tab_2.delivery_stop_count", 2)
    
    val variablePkgCost = (variablePkgCostTab2 - variablePkgCostTab1)    
    
    val singlePkgCost = round(($"tab_1.variable_fuel_cost" + $"tab_1.variable_non_fuel_cost" + $"tab_1.fixed_non_fuel_cost") / $"tab_1.package_count", 2)
    
    var deliveryCostMatrix = allZips.join(joinedDeliveryCost, Seq(POSTAL_CODE), "left_outer")
      .withColumn(VERSION, lit(version))
      .withColumn(VARIABLE_PKG_COST, variablePkgCost)
      .withColumn(SINGLE_PKG_COST, singlePkgCost)
      .select(
          col(VERSION),
          col(POSTAL_CODE),
          col(VARIABLE_PKG_COST),
          col(SINGLE_PKG_COST)       
      ).cache()
  
    // Find the average variable package cost
    val avgVariablePkgCost = deliveryCostMatrix//.where($"variable_pkg_cost" >= 0)
      .agg(round(avg(col(VARIABLE_PKG_COST)), 2).as("avg_cost"))
      .first().getAs[java.math.BigDecimal]("avg_cost")
    
    // Find the average single package cost
    val avgPkgCost = deliveryCost1Pkg//.unionAll(deliveryCost2Pkg)
      .agg(
          sum(col(VARIABLE_FUEL_COST)).as("variable_fuel_cost_total"), 
          sum(col(VARIABLE_NON_FUEL_COST)).as("variable_non_fuel_cost_total"), 
          sum(col(FIXED_NON_FUEL_COST)).as("fixed_non_fuel_cost_total"), 
          sum(col(PKG_COUNT)).as("package_count_total")
      )
      .withColumn("avg_1_pkg", round(($"variable_fuel_cost_total" + $"variable_non_fuel_cost_total" + $"fixed_non_fuel_cost_total") / $"package_count_total", 2))
      .first().getAs[java.math.BigDecimal]("avg_1_pkg")

    val isVariablePkgCostInvalid = () => (isnull(col(VARIABLE_PKG_COST)) || col(VARIABLE_PKG_COST) <= 0)
    
    // Replace any negative or missing cost with the average
    val adjustedVariablePkgCost = when(isVariablePkgCostInvalid(), avgVariablePkgCost).otherwise(col(VARIABLE_PKG_COST))
    val adjustedPkgCost = when(isVariablePkgCostInvalid(), avgPkgCost).otherwise(col(SINGLE_PKG_COST))
    
    deliveryCostMatrix = deliveryCostMatrix
      .withColumn(SINGLE_PKG_COST, adjustedPkgCost)
      .withColumn(VARIABLE_PKG_COST, adjustedVariablePkgCost)      
    
    for (i <- 2 to n) {
      val calculatedColumn = round((deliveryCostMatrix.col((i - 1) + "_pkg") * (i - 1) + col(VARIABLE_PKG_COST)) / i, 2)
      
      deliveryCostMatrix = deliveryCostMatrix.withColumn(i + "_pkg", calculatedColumn)
    }
    
    return deliveryCostMatrix.withColumn(POSTAL_CODE, lpad(col(POSTAL_CODE), 5, "0"))
  }
}