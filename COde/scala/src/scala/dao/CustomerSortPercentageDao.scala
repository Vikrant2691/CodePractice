package com.fedex.smartpost.spark.dao

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.hive.HiveContext

class CustomerSortPercentageDao(sqlContext: HiveContext) {
  def fetchCustomerSortPercentageTable() : DataFrame = {
    val customerSortPercentageTable = sqlContext.sql(
       "SELECT account_number, " +
         "sort_percentage " + 
       "FROM hdp_smp.customer_sort_percentage "
    )
    
    return customerSortPercentageTable
  }
}