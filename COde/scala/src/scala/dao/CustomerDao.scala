package com.fedex.smartpost.spark.dao

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.hive.HiveContext

class CustomerDao(sqlContext: HiveContext) {
  def fetchCustomerTable() : DataFrame = {
    val customerTable = sqlContext.sql(
        "SELECT cust_nbr, " +
          "cust_nm as customer_nm, " +  
          "ce_se_globl_enti_id as global_entity_id, " +
          "ce_se_globl_enti_nm as global_entity_nm " +
        "FROM hdp_smp.fxsp_d_customer"
    )
    
    return customerTable
  }
  
  def fetchBlacklistCustomerTable() : DataFrame = {
    val customerTable = sqlContext.sql(
        "SELECT account_number " +
        "FROM hdp_smp.blacklist_customers"
    )
    
    return customerTable
  }
  
   def fetchWhitelistCustomerTable() : DataFrame = {
    val customerTable = sqlContext.sql(
        "SELECT account_number " +
        "FROM hdp_smp.whitelist_customers"
    )
    
    return customerTable
  }
}