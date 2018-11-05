package com.fedex.smartpost.spark.dao

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.sql.Row

class SystemParameterDao(sqlContext: HiveContext) {
  def fetchSystemParameterStringValue(name : String) : String = {
    return fetchSystemParameter(name).getString(0)
  }
  
  def fetchSystemParameterIntValue(name : String) : Int = {
    return fetchSystemParameterStringValue(name).toInt
  }
  
  def fetchSystemParameter(name : String) : Row = {
    return sqlContext.sql(
        "SELECT value " +
        "FROM hdp_smp.system_parameters " +
        "WHERE name = '" + name + "'"
    ).first()
  }
}