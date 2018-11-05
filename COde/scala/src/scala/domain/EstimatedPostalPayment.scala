package com.fedex.smartpost.spark.domain

case class EstimatedPostalPayment(
    upn:Long,
    est_flg:String,
    total_pstl_chrg_amt:Double,
    est_ddu_pstg_amt:Double,
    est_scf_pstg_amt:Double,
    est_ndc_pstg_amt:Double)