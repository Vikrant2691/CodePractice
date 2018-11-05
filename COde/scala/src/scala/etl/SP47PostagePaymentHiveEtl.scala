package com.fedex.smartpost.spark.etl

import org.apache.commons.lang.StringUtils.trimToNull
import org.apache.spark.SparkContext
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.Row
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.functions._
import com.fedex.smartpost.spark.schema.SP47PostagePaymentSchema
import com.fedex.smartpost.etl.types.java.ParseNullableLong
import com.fedex.smartpost.etl.types.java.ParseNullableDouble
import com.fedex.smartpost.spark.util.DateTimeUtils
import java.nio.file.Paths

class SP47PostagePaymentHiveEtl( sparkContext: SparkContext, sqlContext: SQLContext ) {
  
  def convertFileToDataframe(filepath: String) : DataFrame = {     
    
    val filename = Paths.get(filepath).getFileName().toString();
    
    val fileRdd = sparkContext.textFile(filepath)
    val fileRddWithoutHeader = fileRdd.filter(row => !row.startsWith("SP47"))    
    
    val mapPartition = (rows: Iterator[String]) => rows.map{
      (row: String) => {
        val timestampWithTzFormat = "yyyy-MM-dd HH:mm:ss.SSS"
        val timestampFormat = "yyyy-MM-dd HH:mm:ss"
        val dateFormat = "yyyy-MM-dd"
        
        Row(
            ParseNullableLong.parseString(row.substring(0,  20)),                          // upn
            trimToNull(row.substring(20,  26)),                                            // pkg_event_type_cd
            DateTimeUtils.toSqlTimestamp(row.substring(26, 50), timestampWithTzFormat),    // event_datetime_utc
            trimToNull(row.substring(50, 55)),                                             // event_tz
            trimToNull(row.substring(55,  66)),                                            // pkg_type_cd_1
            trimToNull(row.substring(66,  97)),                                            // pkg_barcd_nbr_1
            ParseNullableDouble.parseString(row.substring(97,  105)),                      // pkg_weight_lbs
            ParseNullableDouble.parseString(row.substring(105, 112)),                      // pkg_length_inch
            ParseNullableDouble.parseString(row.substring(112, 119)),                      // pkg_width_inch
            ParseNullableDouble.parseString(row.substring(119, 126)),                      // pkg_height_inch
            trimToNull(row.substring(126, 140)),                                           // dest_sort_cd
            trimToNull(row.substring(140, 154)),                                           // entr_point_pstl_cd
            trimToNull(row.substring(154, 159)),                                           // hub_cd
            trimToNull(row.substring(159, 161)),                                           // prcs_size_cd
            trimToNull(row.substring(161, 164)),                                           // mail_class_cd
            trimToNull(row.substring(164, 166)),                                           // prcs_ctgy_cd
            trimToNull(row.substring(166, 168)),                                           // usps_dest_rate_cd
            trimToNull(row.substring(168, 171)),                                           // usps_zone_cd
            trimToNull(row.substring(171, 173)),                                           // usps_barcd_cd
            trimToNull(row.substring(173, 177)),                                           // usps_prod_cd
            trimToNull(row.substring(177, 179)),                                           // pkg_relse_cd
            trimToNull(row.substring(179, 189)),                                           // usps_clint_mail_cd
            trimToNull(row.substring(189, 213)),                                           // load_close_tmstp
            trimToNull(row.substring(213, 218)),                                           // load_close_tz_offset_nbr
            trimToNull(row.substring(218, 220)),                                           // usps_del_cnfrm_cd
            trimToNull(row.substring(220, 225)),                                           // pstl_rate_cd
            ParseNullableDouble.parseString(row.substring(225, 234)),                      // pstl_chrg_amt
            ParseNullableDouble.parseString(row.substring(234, 242)),                      // pstl_wgt
            trimToNull(row.substring(242, 293)),                                           // usps_clint_mailer_nm
            trimToNull(row.substring(293, 296)),                                           // umfst_stat_cd
            trimToNull(row.substring(296, 306)),                                           // umfst_rect_src_cd
            trimToNull(row.substring(306, 317)),                                           // umfst_usps_scan_dt
            trimToNull(row.substring(317, 328)),                                           // umfst_usps_prcs_dt
            ParseNullableDouble.parseString(row.substring(328, 337)),                      // total_pstl_chrg_amt
            trimToNull(row.substring(337, 348)),                                           // est_flg
            ParseNullableDouble.parseString(row.substring(348, 357)),                      // est_ddu_pstg_amt
            ParseNullableDouble.parseString(row.substring(357, 366)),                      // est_scf_pstg_amt
            ParseNullableDouble.parseString(row.substring(366, 375)),                      // est_ndc_pstg_amt
            trimToNull(row.substring(375, 377)),                                           // suppr_pstg_flg
            trimToNull(row.substring(377, 380)),                                           // suppr_resn_cd
            filename,                                                                      // filename
            null                                                                           // inserttimestamp
        )
      }
    }
    
    val postagePaymentRdd = fileRddWithoutHeader.mapPartitions(mapPartition)
    return sqlContext.createDataFrame(postagePaymentRdd, SP47PostagePaymentSchema.schema).withColumn("inserttimestamp", current_timestamp())
  }  
}
