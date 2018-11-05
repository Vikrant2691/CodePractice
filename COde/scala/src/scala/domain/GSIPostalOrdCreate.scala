package com.fedex.smartpost.spark.domain

case class GSIPostalOrdCreate (    
 unvsl_pkg_nbr: Long, 
 intnd_dlvr_ntwk_desc:String, 
 event_tz_tmstp: String,
 pstl_cd: String,
 custnm: String,
 shpr_acct_nbr: Long
)
