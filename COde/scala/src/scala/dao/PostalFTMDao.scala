package com.fedex.smartpost.spark.dao

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.SQLContext
import com.fedex.smartpost.spark.domain.GSIPostalFTM

class PostalFTMDao(sqlContext: SQLContext) {
  
  import sqlContext.implicits._
  
  def getFFTM (startDate: String, endDate: String, startDatePartition: String, endDatePartition: String): Dataset[GSIPostalFTM] =  {
   
   var sqlSelect =
       "SELECT   unvsl_pkg_nbr, " +
         "CASE  WHEN rn_desc = 1 AND arloc IS NOT NULL THEN arloc ELSE NULL END AS arloc, " +
         "CASE  WHEN rn_asc = 1  AND odloc IS NOT NULL THEN odloc ELSE NULL END AS odloc, " +
         "CASE  WHEN rn_asc = 1  AND odloc_fac_type_cd IS NOT NULL THEN odloc_fac_type_cd ELSE NULL END AS odloc_fac_type_cd," +
         "CASE  WHEN rn_asc = 1  AND odloc_fac_co_nm IS NOT NULL THEN odloc_fac_co_nm ELSE NULL  END AS odloc_fac_co_nm, " +
         "CASE  WHEN rn_desc = 1 AND arloc_fac_type_cd IS NOT NULL THEN arloc_fac_type_cd ELSE NULL  END AS arloc_fac_type_cd, " +
         "CASE  WHEN rn_desc = 1 AND arloc_fac_co_nm IS NOT NULL THEN arloc_fac_co_nm  ELSE NULL END AS arloc_fac_co_nm, " +
         "dlprod_type_code AS prodtype, " +
         "mailclass     AS mailclass," +
         "dleventdate   AS dldate " +
      "FROM ( " +
      "SELECT upn AS unvsl_pkg_nbr, ftm_event_cd, " +
         "CASE ftm_event_cd WHEN 'AR' THEN facility_id_value ELSE NULL  END AS arloc," + 
         "CASE ftm_event_cd WHEN 'AR' THEN facility_type     ELSE NULL  END AS arloc_fac_type_cd," + 
         "CASE ftm_event_cd WHEN 'AR' THEN facility_company  ELSE NULL  END AS arloc_fac_co_nm, " +
         "CASE ftm_event_cd WHEN 'DL' THEN product_type_cd   ELSE NULL  END AS dlprod_type_cd," +       
         "CASE ftm_event_cd WHEN 'OD' THEN facility_id_value ELSE NULL  END AS odloc," +
         "CASE ftm_event_cd WHEN 'OD' THEN facility_type     ELSE NULL  END AS odloc_fac_type_cd," +
         "CASE ftm_event_cd WHEN 'OD' THEN facility_company  ELSE NULL  END AS odloc_fac_co_nm," +
         "CASE ftm_event_cd WHEN 'DL' THEN ftm_event_datetime_utc  ELSE NULL  END AS dleventdate," +
		     "CASE ftm_event_cd WHEN 'DL' THEN product_type_cd   ELSE NULL  END AS dlprod_type_code," +
         "CASE ftm_event_cd WHEN 'DL' THEN substring(pstl_barcd_nbr, 3, 3) ELSE NULL END AS mailclass," +
         "ROW_NUMBER() OVER (PARTITION BY upn, ftm_event_cd ORDER BY ftm_event_datetime_utc ASC) rn_asc," +
	       "ROW_NUMBER() OVER (PARTITION BY upn, ftm_event_cd ORDER BY ftm_event_datetime_utc DESC) rn_desc " +
        "FROM fxg_tracking_orc ft " +
         "WHERE ftm_event_cd != 'null' " +
         "AND ftm_event_cd IN ('DL', 'OD', 'AR') " +
         "AND upn != 0 " +
         "AND file_create_date > '"+  startDate + "' " +
         ") SQ " 

  
   println(" SELECT FFTM" , sqlSelect)
    var df = sqlContext.sql(sqlSelect)
    df.show()
    

   return df.as[GSIPostalFTM]

     }    
}