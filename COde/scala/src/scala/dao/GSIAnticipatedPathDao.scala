package com.fedex.smartpost.spark.dao

import scala.reflect.runtime.universe

import org.apache.spark.sql.Dataset
import org.apache.spark.sql.SQLContext

import com.fedex.smartpost.spark.domain.GSIAnticipatedPath

class GSIAnticipatedPathDao (sqlContext: SQLContext) {
  
  import sqlContext.implicits._
  
  def selectInsertIntoPstgGsiApCounts (startDate: String, endDate: String, startDatePartition: String, endDatePartition: String): Unit = {
    sqlContext.setConf("hive.exec.dynamic.partition", "true")
    sqlContext.setConf("hive.exec.dynamic.partition.mode", "nonstrict")
    
    var sqlInsert=            
            "INSERT INTO TABLE hdp_smp.pstg_gsi_ap_counts PARTITION(file_create_date) " +
            "SELECT firstScan.shpr_acct_nbr AS shpr_acct_nbr, " +
                   "firstScan.adv_orig_dt AS event_dt, " +
            	     "firstScan.intended_delivery_network AS IDN, " +
            	     "SUM(totalCounts.office_flg ) AS office_flg, " +
                   "SUM(totalCounts.fxg_flg ) AS fxg_flg, " +
                   "SUM(totalCounts.fhd_flg ) AS fhd_flg, " +
                   "SUM(totalCounts.fxsp_flg ) AS fxsp_flg, " +
                   "SUM(totalCounts.pstl_flg ) AS pstl_flg, " +
            	     "date_format(adv_orig_dt, 'yyyyMMdd') as file_create_date " +
            "FROM ( SELECT COALESCE(a.intended_delivery_network, 'NoEPDI') AS intended_delivery_network, " +
                           "b.event_tz_tmstp AS adv_orig_dt, " +
                            "b.unvsl_pkg_nbr, " +
                            "b.pstl_cd, " +
                            "b.shpr_acct_nbr, " +
                            "b.custnm         " +
                       "FROM ( SELECT ord.upn AS unvsl_pkg_nbr, " +
                                     "ord.event_tz_tmstp,		 " +
                                     "substring(ord.pstl_cd, 1, 5) AS pstl_cd, " +
                                     "ord.shpr_acct_nbr, " +
                                     "cust.ce_se_globl_enti_nm AS custnm " +
                                "FROM hdp_smp.fxsp_d_customer cust, " +
                                     "( SELECT upn, " +
                                              "event_time_local_utc AS event_tz_tmstp, " +
                                              "account_number AS shpr_acct_nbr, " +
                                              "destination_sort_code AS pstl_cd " +
                                         "FROM hdp_smp.order_create_orc  " +
                                         "WHERE file_create_date >= \"" + startDate + "\" " +
                                          " AND file_create_date <= \"" + endDate + "\" " +
                                          " AND event_time_local_utc >= \"" + startDatePartition + "\" " +
                                          " AND event_time_local_utc <= \"" + endDatePartition + "\" " +
                                          " AND product_type IN ('DOM', 'SMS') " +
                                          "AND upn != '0' ) ord       " +
                               "WHERE ord.shpr_acct_nbr = cust.cust_nbr ) b, " +
                            "( SELECT upn,  " +
                                     "intended_delivery_network, " + 
                                     "event_timestamp_utc, " +
                                     "rank_number " +
                                "FROM ( SELECT upn,  " +
                                              "intended_delivery_network, " + 
                                              "event_timestamp_utc, " +
                                              "RANK() OVER (PARTITION BY upn ORDER BY event_timestamp_utc ASC) AS rank_number " +
                                         "FROM hdp_smp.active_package_orc " +
                                        "WHERE file_create_date >= \"" + startDate + "\" " +
                                          " AND file_create_date <= \"" + endDate + "\" " +
                                          " AND event_timestamp_utc >= \"" + startDatePartition + "\" " +
                                          " AND event_timestamp_utc <= \"" + endDatePartition + "\" " +
                                          " AND upn != '0' ) ap " +
                               "WHERE ap.rank_number = 1 ) a       " + 
                     "WHERE a.upn = b.unvsl_pkg_nbr ) firstScan, " +
                   "( SELECT upn AS unvsl_pkg_nbr, " +
                            "ftm_event_datetime_utc AS event_tz_tmstp, " +
                            "CASE  " +
                              "WHEN ftm_event_cd = 'IP' " + 
                               "AND facility_type IN ('KL', 'FF', '') " +
                              "THEN 1 " +
                              "ELSE 0 " +
                            "END AS office_flg, " + 
                            "CASE  " +
                              "WHEN ftm_event_cd = 'PU' " + 
                               "AND facility_type IN ('FG') " +
                              "THEN 1 " +
                              "ELSE 0 " +
                            "END AS fxg_flg, " +
                            "CASE  " +
                              "WHEN ftm_event_cd = 'PU' " + 
                               "AND facility_type IN ('HD') " +
                              "THEN 1 " +
                              "ELSE 0 " +
                            "END AS fhd_flg, " +
                            "0 AS fxsp_flg, " +
                            "0 AS pstl_flg " +
                       "FROM fxg_tracking_orc ft " +
                      "WHERE ftm_event_cd IN ('PU', 'IP') " +
                        "AND facility_type IN ('KL','FF','','FG','HD') " +
                        "AND product_type_cd IN ('DOM', 'SMS') " +
                        "AND file_create_date >= \"" + startDate + "\" " +
                        " AND file_create_date <= \"" + endDate + "\" " +
                        " AND ftm_event_datetime_utc >= \"" + startDatePartition + "\" " +
                        " AND ftm_event_datetime_utc <= \"" + endDatePartition + "\" " +
                        " AND upn != 0 " +
                      "UNION " +
                     "SELECT upn AS unvsl_pkg_nbr, " +  
                            "pkg_event_timestamp_utc AS event_tz_tmstp,  " +
                            "0 AS office_flg, " +
                            "0 AS fxg_flg, " +
                            "0 AS fhd_flg, " +
                            "1 AS fxsp_flg, " +
                            "0 AS pstl_flg " +
                       "FROM hdp_smp.movement_orc " +
                      "WHERE file_create_date >= \"" + startDate + "\" " +
                        " AND file_create_date <= \"" + endDate + "\" " +
                        " AND pkg_event_timestamp_utc >= \"" + startDatePartition + "\" " +
                        " AND pkg_event_timestamp_utc <= \"" + endDatePartition + "\" " +
                        " AND upn != 0 " +
                      "UNION " +
                     "SELECT upn AS unvsl_pkg_nbr, " + 
                            "event_timestamp_utc AS event_tz_tmstp, " + 
                            "0 AS office_flg, " +
                            "0 AS fxg_flg, " +
                            "0 AS fhd_flg, " +
                            "0 AS fxsp_flg, " +
                            "1 AS pstl_flg " +
                       "FROM hdp_smp.postal_event_orc " +
                      "WHERE file_create_date >= \"" + startDate + "\" " +
                        " AND file_create_date <= \"" + endDate + "\" " +
                        " AND event_timestamp_utc >= \"" + startDatePartition + "\" " +
                        " AND event_timestamp_utc <= \"" + endDatePartition + "\" " +
                        " AND upn != 0 ) totalCounts " +
             "WHERE firstScan.unvsl_pkg_nbr = totalCounts.unvsl_pkg_nbr " +
            "GROUP BY firstScan.shpr_acct_nbr, firstScan.adv_orig_dt, firstScan.intended_delivery_network"
    sqlContext.sql(sqlInsert)
  } 
  
  def retrievePstgGsiApData(startPartitionDate: String, endPartitionDate:String, startDate: String, endDate: String) : Dataset[GSIAnticipatedPath] = {

    val sqlText = "SELECT shpr_acct_nbr, " +
                         "event_date_dt, " +
                         "intnd_dlvr_ntwk_desc, " +
          	             "office_wsc_pkg_count, " +
                         "fxg_pkg_count, " +
                         "fhd_pkg_count, " +
                         "fxsp_pkg_count, " +
                         "usps_pkg_count, " +
        	               "file_create_date " +
                    "FROM hdp_smp.pstg_gsi_ap_counts " +
                   "WHERE file_create_date >= \"" + startPartitionDate + "\" " +
           	         "AND file_create_date <= \"" + endPartitionDate + "\" " +
            	       "AND event_date_dt >= \"" + startDate + "\" " +
            	       "AND event_date_dt <= \"" + endDate + "\" "
    
    sqlContext.sql(sqlText).as[GSIAnticipatedPath]
  }
}