package com.fedex.smartpost.spark.domain

case class GSIActualPayment (
  unvsl_pkg_nbr:Long,
  ovsz_flg:String,
  blln_flg:String, 
  count_total:Double,
  actl_pstl_chrg_amt:Double
)
  
