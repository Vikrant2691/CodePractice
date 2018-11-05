package com.fedex.smartpost.spark.dao

import scala.reflect.runtime.universe

import org.apache.spark.sql.Dataset
import org.apache.spark.sql.SQLContext

import com.fedex.smartpost.spark.domain.GSIActualPayment
import com.fedex.smartpost.spark.domain.GSIEstimatedPayment

class GSIPostalPaymentDao(sqlContext: SQLContext) {

  import sqlContext.implicits._

  def getGSIEstimatedPaymentEvents(startDate: String, endDate: String, startDatePartition: String, endDatePartition: String): Dataset[GSIEstimatedPayment] = {
      var sqlGSISelect =
        "SELECT upn AS unvsl_pkg_nbr, " + 
               "usps_dest_rate_cd AS dest_rate_ind, " +
               "COUNT(*) AS count_total, " +
               "SUM(total_pstl_chrg_amt) AS tot_est_pstl_chrg_amt, " +
               "SUM(est_ddu_pstg_amt) AS tot_est_ddu_pstg_amt, " +
               "SUM(est_scf_pstg_amt) AS tot_est_scf_pstg_amt, " +
               "SUM(est_ndc_pstg_amt) AS tot_est_ndc_pstg_amt " +
          "FROM hdp_smp.postage_payment " +
         "WHERE pkg_relse_cd = 'E' " +
           "AND file_create_date >= '" + startDate + "'" +
           "AND file_create_date <= '" + endDate + "'" +
           "AND event_datetime_utc >= '" + startDatePartition + "' " +
           "AND event_datetime_utc <= '" + endDatePartition + "' " +
      "GROUP BY upn, prcs_size_cd, usps_dest_rate_cd "

      return sqlContext.sql(sqlGSISelect).as[GSIEstimatedPayment]
  }

  def getGSIActualPaymentEvents(startDate: String, endDate: String, startDatePartition: String, endDatePartition: String): Dataset[GSIActualPayment] = {
      var sqlGSIActSelect =
        "SELECT upn AS unvsl_pkg_nbr, " +
               "CASE prcs_size_cd " +
                 "WHEN 'O' " +
                 "THEN true " +
                 "ELSE false " +
               "END as ovsz_flg, " +
               "CASE prcs_size_cd " +
                 "WHEN 'B' " +
                 "THEN true " +
                 "ELSE false " +
               "END as blln_flg, " +
               "COUNT(*) AS count_total ," +
               "SUM(pstl_chrg_amt) AS actl_pstl_chrg_amt " +
          "FROM hdp_smp.postage_payment " +
         "WHERE pkg_relse_cd != 'E' " +
           "AND file_create_date >= '" + startDate + "'" +
           "AND file_create_date <= '" + endDate + "'" +
           "AND event_datetime_utc >= '" + startDatePartition + "' " +
           "AND event_datetime_utc <= '" + endDatePartition + "' " +
      "GROUP BY upn, prcs_size_cd "

      return sqlContext.sql(sqlGSIActSelect).as[GSIActualPayment]
  }
}