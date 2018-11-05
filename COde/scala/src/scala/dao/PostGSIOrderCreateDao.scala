package com.fedex.smartpost.spark.dao


import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.SQLContext
import com.fedex.smartpost.spark.domain.GSIPostalOrdCreate

class PostGSIOrderCreateDao (sqlContext: SQLContext) {
  
  import sqlContext.implicits._
  
  def getFFTM (startDate: String, endDate: String, startDatePartition: String, endDatePartition: String): Dataset[GSIPostalOrdCreate] =  {
   
   var sqlSelect =
        "SELECT ord.upn AS unvsl_pkg_nbr, " +
        "ap.intended_delivery_network AS intnd_dlvr_ntwk_desc, " +
        "ord.event_tz_tmstp,"+
        "substring(ord.pstl_cd, 1, 5) AS pstl_cd,"+
        "cust.ce_se_globl_enti_nm AS custnm "+
	      "FROM hdp_smp.fxsp_d_customer cust, " +
        "hdp_smp.active_package_orc ap, " +
	      "( SELECT upn, " +
        "event_time_local_utc AS event_tz_tmstp, " +
        "account_number AS shpr_acct_nbr, " +
        "destination_sort_code AS pstl_cd " +
	      "FROM hdp_smp.order_create_orc " +
	      "WHERE file_create_date '"+  startDate + "' " + ") ord  " +    
		    "WHERE ord.shpr_acct_nbr = cust.cust_nbr " +
		    "AND ord.upn = ap.upn "
   
		      println(" SELECT FFTM" , sqlSelect)
    
           var df = sqlContext.sql(sqlSelect)
           df.show()
    

           return df.as[GSIPostalOrdCreate]

     }    
}