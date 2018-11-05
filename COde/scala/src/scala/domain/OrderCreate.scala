package com.fedex.smartpost.spark.domain

case class OrderCreate(
    upn:Long,
    account_number:Long, 
    pstl_bar_cd:String,
    unix_event_timestamp:Long,
    destination_sort_code:String,
    recipient_share_id:String,
    recipient_raw_address_id:String,
    recipient_city:String, 
    recipient_state:String,
    fxg_destination_id:String,
    fhd_destination_id:String,
    fxsp_destination_id:String)