package com.fedex.smartpost.spark.dao

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.SQLContext
import com.fedex.smartpost.spark.domain.PostalPayment
import com.fedex.smartpost.spark.domain.EstimatedPostalPayment

class PostalPaymentDao(sqlContext: SQLContext) {

  import sqlContext.implicits._
  
  def getPostalPaymentEvents(startDate: String, endDate: String, startDatePartition: String, endDatePartition: String): Dataset[PostalPayment] = {

    var sqlSelect =
      "SELECT upn, mail_class_cd, pkg_relse_cd, prcs_size_cd, pstl_chrg_amt " +
        "FROM postage_payment " +
        "WHERE pkg_relse_cd != 'E' " + 
        "AND file_create_date >= '" + startDatePartition + "' " +
        "AND file_create_date <= '" + endDatePartition + "' " +
        "AND date_format(event_timestamp_utc, 'yyyyMMdd') >= '" + startDate + "' " +
        "AND date_format(event_timestamp_utc, 'yyyyMMdd') <= '" + endDate + "' "

    println(sqlSelect)
    var df = sqlContext.sql(sqlSelect)
    df.show()
        
    return df.as[PostalPayment]
  }
  
  def getEstimatedPostalPaymentEvents(startDate: String, endDate: String, startDatePartition: String, endDatePartition: String): Dataset[EstimatedPostalPayment] = {

    var sqlSelect =
      "SELECT upn, est_flg, total_pstl_chrg_amt, est_ddu_pstg_amt, est_scf_pstg_amt, est_ndc_pstg_amt " +
        "FROM postage_payment " +
        "WHERE pkg_relse_cd = 'E' " + 
        "AND file_create_date >= '" + startDatePartition + "' " +
        "AND file_create_date <= '" + endDatePartition + "' " +
        "AND date_format(event_timestamp_utc, 'yyyyMMdd') >= '" + startDate + "' " +
        "AND date_format(event_timestamp_utc, 'yyyyMMdd') <= '" + endDate + "' "

    return sqlContext.sql(sqlSelect).as[EstimatedPostalPayment]
  }
}