package com.fedex.smartpost.spark.domain

case class FxgTracking(
    upn:Long,
    event_code:String, 
    unix_event_timestamp:Long)