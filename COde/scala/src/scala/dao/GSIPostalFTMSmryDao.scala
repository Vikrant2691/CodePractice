package com.fedex.smartpost.spark.dao

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.SQLContext

import com.fedex.smartpost.spark.domain.GSIPostalFTMSmry

class GSIPostalFTMSmryDao(sqlContext: SQLContext) {

  import sqlContext.implicits._
  
  def GSIPtlFTM(startDate: String, endDate: String, startDatePartition: String, endDatePartition: String): Dataset[GSIPostalFTMSmry] = {
    val ftmDAO = new GSIPostalFTMDao(sqlContext)
    val orderCrDAO = new GSIPostalOrderCreateDao(sqlContext)
    val paymentDAO = new GSIPostalPaymentDao(sqlContext)
    val results = createFTMSmmry(startDate, endDate, startDatePartition, endDatePartition, ftmDAO, orderCrDAO, paymentDAO)
    results.as[GSIPostalFTMSmry]
  }
  
  def createFTMSmmry(startDate: String, endDate: String, startDatePartition: String, endDatePartition: String, 
                     ftmDAO: GSIPostalFTMDao, orderCrDAO: GSIPostalOrderCreateDao, paymentDAO: GSIPostalPaymentDao): DataFrame = {
    
    val ftmDF = ftmDAO.getFFTM(startDate, endDate, startDatePartition, endDatePartition).toDF()
    val orderCrDF= orderCrDAO.retrieveShipperOrders(startDate, endDate, startDatePartition, endDatePartition).toDF()
    val estPaymentDF = paymentDAO.getGSIEstimatedPaymentEvents(startDate, endDate, startDatePartition, endDatePartition).toDF().drop("count_total")
    val actPaymentDF = paymentDAO.getGSIActualPaymentEvents(startDate, endDate, startDatePartition, endDatePartition).toDF().drop("count_total")

    ftmDF.registerTempTable("ftm")
    orderCrDF.registerTempTable("orderCr")
    estPaymentDF.registerTempTable("estPayment")
    actPaymentDF.registerTempTable("actPayment")

    val query = "SELECT *" +
                "  FROM ftm f, orderCr o, estPayment e, actPayment a" +
                " WHERE f.unvsl_pkg_nbr = o.unvsl_pkg_nbr" +
                "   AND f.unvsl_pkg_nbr = e.unvsl_pkg_nbr" +
                "   AND f.unvsl_pkg_nbr = a.unvsl_pkg_nbr"
    
    var results = sqlContext.sql(query)
    println("THIS IS THE SQL")
    results.show                                      
    results
  }
}