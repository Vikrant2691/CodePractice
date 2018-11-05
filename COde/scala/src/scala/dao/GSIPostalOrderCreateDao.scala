package com.fedex.smartpost.spark.dao


import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.SQLContext
import com.fedex.smartpost.spark.domain.GSIPostalOrdCreate

class GSIPostalOrderCreateDao (sqlContext: SQLContext) {
  
  import sqlContext.implicits._
  
  def retrieveShipperOrders(startDate: String, endDate: String, startDatePartition: String, endDatePartition: String): Dataset[GSIPostalOrdCreate] =  {
    
	 var sqlSelect = 
	   "SELECT b.unvsl_pkg_nbr, " +
            "a.intended_delivery_network as intnd_dlvr_ntwk_desc, " +
            "b.event_tz_tmstp, " +
            "b.pstl_cd, " +
            "b.custnm, " +
            "b.shpr_acct_nbr " +
       "FROM ( SELECT ord.upn AS unvsl_pkg_nbr, " +
                     "ord.event_tz_tmstp, " +
                     "substring(ord.pstl_cd, 1, 5) AS pstl_cd, " +
                     "ord.shpr_acct_nbr, " +
                     "cust.ce_se_globl_enti_nm AS custnm " +
                "FROM hdp_smp.fxsp_d_customer cust, " +
                   "( SELECT upn, " +
                            "event_time_local_utc AS event_tz_tmstp, " +
                            "account_number AS shpr_acct_nbr, " +
                            "destination_sort_code AS pstl_cd " +
                       "FROM hdp_smp.order_create_orc  " +
                      "WHERE upn != 0 " +
                        "AND file_create_date >= '" + startDate + "' " +
                       " AND file_create_date <= '" + endDate + "' " +
                       " AND event_time_local_utc >= '"+ startDatePartition + "' " +
                       " AND event_time_local_utc <= '"+ endDatePartition + "' " +
                       " AND upn != '0' ) ord " +
               "WHERE ord.shpr_acct_nbr = cust.cust_nbr ) b, " +
       "( SELECT upn,  " +
                "intended_delivery_network, " + 
                "event_timestamp_utc, " +
                "rank_number " +
           "FROM ( SELECT upn,  " +
                         "intended_delivery_network, " + 
                         "event_timestamp_utc, " +
                         "RANK() OVER (PARTITION BY upn ORDER BY event_timestamp_utc DESC) AS rank_number " +
                    "FROM hdp_smp.active_package_orc " +
                   "WHERE upn != 0 " +
                    " AND file_create_date >= '" + startDate + "' " +
                    " AND file_create_date <= '" + endDate + "' " +
                    " AND event_timestamp_utc >= '" + startDatePartition + "' " +
                    " AND event_timestamp_utc <= '" + endDatePartition + "' " +
                    " AND upn != '0' ) ap " +
          "WHERE ap.rank_number = 1 ) a " + 
     "WHERE a.upn = b.unvsl_pkg_nbr "
		    
    return sqlContext.sql(sqlSelect).as[GSIPostalOrdCreate]
  }    
}