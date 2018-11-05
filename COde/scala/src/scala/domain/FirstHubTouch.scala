package com.fedex.smartpost.spark.domain

case class FirstHubTouch(
    upn:Long,
    hub_id:String, 
    unix_event_timestamp:Long)