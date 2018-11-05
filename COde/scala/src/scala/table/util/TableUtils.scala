package com.fedex.smartpost.spark.table.util

import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.types.StructField
import org.apache.spark.sql.SQLContext

object TableUtils {
  
  def dropTableIfExists(sqlContext: SQLContext, tableName: String) {
    sqlContext.sql("DROP TABLE IF EXISTS " + tableName)
  }
  
  def createExternalTable(
      sqlContext: SQLContext, 
      tableName: String, 
      schema: StructType, 
      partitionBy: String = null) {
    sqlContext.sql(createTableSql(tableName, schema, partitionBy))
  }
  
  def createTableSql(
      tableName: String, 
      schema: StructType, 
      partitionBy: String = null) : String = {
    
    var fieldDefinitions = ""
    
    schema.fields.foreach(  
      (field: StructField) => {
        val line = field.name + "\t" + field.dataType.simpleString + ",\n"
        fieldDefinitions = fieldDefinitions.+(line)
      }
    )
    
    fieldDefinitions = fieldDefinitions.substring(0, fieldDefinitions.length() - 2)
    
    var sqlString = ("CREATE EXTERNAL TABLE " + tableName + " (\n"
          + fieldDefinitions + "\n"
          + ")")
          
    if (partitionBy != null) {
      sqlString += " PARTITIONED BY (" + partitionBy + " STRING) \n"
    }
    
    sqlString += ("ROW FORMAT DELIMITED FIELDS TERMINATED BY '\\t'\n"
          + "STORED as ORC")
    
    return sqlString
  }
}