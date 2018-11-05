package com.fedex.smartpost.spark.domain

case class PostalPayment(
    upn:Long,
    mail_class_cd:String,
    pkg_relse_cd:String,
    prcs_size_cd:String,
    pstl_chrg_amt:Double)