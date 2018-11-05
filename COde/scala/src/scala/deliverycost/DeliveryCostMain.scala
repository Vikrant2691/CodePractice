package com.fedex.smartpost.spark.deliverycost

import org.apache.spark.sql.hive.HiveContext

import com.fedex.smartpost.spark.util.SparkUtils
import com.fedex.smartpost.spark.table.util.TableUtils
import org.apache.hadoop.hdfs.client.HdfsUtils
import java.io.File
import com.fedex.smartpost.spark.deliverycost.util.ExcelUtils
import org.apache.spark.sql.SaveMode
import java.io.FileInputStream
import java.io.FileOutputStream
import org.apache.hadoop.fs.Path
import org.apache.hadoop.fs.FileSystem
import org.apache.hadoop.conf.Configuration
import com.fedex.smartpost.spark.schema.DeliveryCostMatrixSchema

object DeliveryCostMain {
  val ROOT_HADOOP_DIR = "hdfs://surus-nameservice/data/PROD/smartpost/delivery_cost"
  val CSV_DIR = ROOT_HADOOP_DIR + "/csv/"
  val EXCEL_DIR = ROOT_HADOOP_DIR + "/original/"
  
  val DB_DOMAIN = "hdp_smp."
  val DELIVERY_COST_TABLENAME = DB_DOMAIN + "delivery_cost_orc"
  val DELIVERY_COST_MATRIX_TABLENAME = DB_DOMAIN + "delivery_cost_matrix_orc"
  
  val N_PKG_CALCULATION = 12
  val FILE_VERSION_PATTERN = "((Jan|Feb|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec).+)(?=_)".r
  
  def main(args: Array[String]) {
    val sparkContext = SparkUtils.createContext("deliveryCostMain")
    sparkContext.setLogLevel("WARN")
    
    val sqlContext = new HiveContext(sparkContext)
    val deliveryCost = new DeliveryCost(sparkContext, sqlContext)
    
    val filename = args(0)    
    val fileVersion = FILE_VERSION_PATTERN.findFirstIn(filename).get
    
    val fs = FileSystem.get(new Configuration)
    val filepath = new Path(EXCEL_DIR + filename)    
    
    // Break Excel file about into CSV
    val pkgDensityOneCsvPath = new Path(CSV_DIR + fileVersion + "_pkg_density_1.csv")
    val pkgDensityOneCsv = fs.create(pkgDensityOneCsvPath)
    
    val pkgDensityTwoCsvPath = new Path(CSV_DIR + fileVersion + "_pkg_density_2.csv")    
    val pkgDensityTwoCsv = fs.create(pkgDensityTwoCsvPath)
    
    ExcelUtils.convertXlsToCsv(fs.open(filepath), Array(pkgDensityOneCsv, pkgDensityTwoCsv))
   
    // Load CSV files and create package density matrix
    val deliveryCostDensity1 = deliveryCost.loadDeliveryCostFile(pkgDensityOneCsvPath.toUri().getPath, fileVersion)
    val deliveryCostDensity2 = deliveryCost.loadDeliveryCostFile(pkgDensityTwoCsvPath.toUri().getPath, fileVersion)
    val deliveryCostMatrix = deliveryCost.createDeliveryCostMatrix(deliveryCostDensity1, deliveryCostDensity2, N_PKG_CALCULATION)
      .select(
        "postal_code",
        "variable_pkg_cost",
        "1_pkg",
        "2_pkg",
        "3_pkg",
        "4_pkg",
        "5_pkg",
        "6_pkg",
        "7_pkg",
        "8_pkg",
        "9_pkg",
        "10_pkg",
        "11_pkg",
        "12_pkg",
        "version"
    )
    
    SparkUtils.writeTable(deliveryCostMatrix.as(DELIVERY_COST_MATRIX_TABLENAME), DELIVERY_COST_MATRIX_TABLENAME, "orc", SaveMode.Append)
    sqlContext.sparkContext.stop()
  }
}