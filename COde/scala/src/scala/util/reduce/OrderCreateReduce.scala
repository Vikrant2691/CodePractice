package com.fedex.smartpost.spark.util.reduce

import org.apache.spark.api.java.function.ReduceFunction
import com.fedex.smartpost.spark.domain.OrderCreate
import org.apache.commons.lang.StringUtils

object OrderCreateReduce extends ReduceFunction[OrderCreate] {
  def call(a: OrderCreate, b: OrderCreate): OrderCreate = {
     //Priority: event timestamp and then if share is not null
    if(StringUtils.isNotBlank(a.recipient_share_id) && StringUtils.isNotBlank(b.recipient_share_id) && a.unix_event_timestamp < b.unix_event_timestamp) {
      return a;
    }
    else if(StringUtils.isNotBlank(a.recipient_share_id) && StringUtils.isNotBlank(b.recipient_share_id) && b.unix_event_timestamp < a.unix_event_timestamp) {
      return b;
    }
    else if(StringUtils.isNotBlank(a.recipient_share_id)) {
      return a;
    }
    else if(StringUtils.isNotBlank(b.recipient_share_id)) {
      return b;
    }
    else if(a.unix_event_timestamp < b.unix_event_timestamp) {
      return a;
    }
    else {
      return b;
    }
  }
}