package com.fedex.smartpost.spark.domain

case class PostalEvent(
    upn:Long,
    postal_event_code:String,
    postal_code:String,
    unix_event_timestamp:Long)