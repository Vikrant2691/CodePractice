package com.fedex.smartpost.spark.domain

import java.sql.Timestamp

case class GSIPostalFTM (
  unvsl_pkg_nbr:Long,
  loc_fac_id_value_cd:String,
  loc_fac_type_cd:String,
  loc_fac_co_nm:String,  
  prodtype:String,
  mailclass:String,
  mailclass_desc:String,
  dldate:Timestamp
)
