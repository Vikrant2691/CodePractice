package com.fedex.smartpost.spark.util

import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.format.DateTimeFormat
import java.sql.Timestamp
import java.sql.Date


object DateTimeUtils {
  
  def toSqlTimestamp(string: String, stringFormat: String) : Timestamp = {
    val trimmedString = string.trim()
    
    if (trimmedString != null && !trimmedString.isEmpty()) {
      val dateTimeFormatter = DateTimeFormat.forPattern(stringFormat).withOffsetParsed()
      
      return new Timestamp(dateTimeFormatter.parseMillis(trimmedString))
    }    
    return null
  }
  
  def toSqlDate(string: String, stringFormat: String) : Date = {
    val trimmedString = string.trim()
    
    if (trimmedString != null && !trimmedString.isEmpty()) {
      val dateTimeFormatter = DateTimeFormat.forPattern(stringFormat)
      
      return new Date(dateTimeFormatter.parseMillis(trimmedString))
    }    
    return null
  }
  
  def extractTimeZoneId(string: String, stringFormat: String) : String = {
    val trimmedString = string.trim()
    
    if (trimmedString != null && !trimmedString.isEmpty()) {
      val dateTimeFormatter = DateTimeFormat.forPattern(stringFormat).withOffsetParsed()
      
      return dateTimeFormatter.parseDateTime(trimmedString).getZone.toTimeZone().getID
    }    
    return null
  }
  
  def calculateDaysInSeconds(days: Int) : Int = {
    return Days.days(days).toStandardSeconds().getSeconds()
  }
  
  def getDaysAgo(daysToSubtract : Int) : String = {
    DateTime.now().minusDays(daysToSubtract).toString("YYYYMMdd")
  }
}