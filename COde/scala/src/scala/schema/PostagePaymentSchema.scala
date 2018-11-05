package com.fedex.smartpost.spark.schema

import org.apache.spark.sql.types.DoubleType
import org.apache.spark.sql.types.TimestampType
import org.apache.spark.sql.types.DateType
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.types.LongType
import org.apache.spark.sql.types.StructField
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.Row
import com.fedex.smartpost.spark.util.DateTimeUtils
import com.fedex.smartpost.etl.types.java.ParseNullableDouble

object PostagePaymentSchema {
  val schema = StructType(Array(
    StructField("upn", LongType, true),
    StructField("pkg_event_type_cd", StringType, true),
    StructField("event_datetime_utc", TimestampType, true),
    StructField("event_tz", StringType, true),
    StructField("pkg_type_cd_1", StringType, true),
    StructField("pkg_barcd_nbr_1", StringType, true),
    StructField("pkg_weight_lbs", DoubleType, true),
    StructField("pkg_length_inch", DoubleType, true),
    StructField("pkg_width_inch", DoubleType, true),
    StructField("pkg_height_inch", DoubleType, true),
    StructField("dest_sort_cd", StringType, true),
    StructField("entr_point_pstl_cd", StringType, true),
    StructField("hub_cd", StringType, true),
    StructField("prcs_size_cd", StringType, true),
    StructField("mail_class_cd", StringType, true),
    StructField("prcs_ctgy_cd", StringType, true),
    StructField("usps_dest_rate_cd", StringType, true),
    StructField("usps_zone_cd", StringType, true),
    StructField("usps_barcd_cd", StringType, true),
    StructField("usps_prod_cd", StringType, true),
    StructField("pkg_relse_cd", StringType, true),
    StructField("usps_clint_mail_cd", StringType, true),
    StructField("load_close_tmstp", StringType, true),
    StructField("load_close_tz_offset_nbr", StringType, true),
    StructField("usps_del_cnfrm_cd", StringType, true),
    StructField("pstl_rate_cd", StringType, true),
    StructField("pstl_chrg_amt", DoubleType, true),
    StructField("pstl_wgt", DoubleType, true),
    StructField("usps_clint_mailer_nm", StringType, true),
    StructField("umfst_stat_cd", StringType, true),
    StructField("umfst_rect_src_cd", StringType, true),
    StructField("umfst_usps_scan_dt", StringType, true),
    StructField("umfst_usps_prcs_dt", StringType, true),
    StructField("total_pstl_chrg_amt", DoubleType, true),
    StructField("est_flg", StringType, true),
    StructField("est_ddu_pstg_amt", DoubleType, true),
    StructField("est_scf_pstg_amt", DoubleType, true),
    StructField("est_ndc_pstg_amt", DoubleType, true),
    StructField("suppr_pstg_flg", StringType, true),
    StructField("suppr_resn_cd", StringType, true),
    StructField("filename", StringType, true),
    StructField("inserttimestamp", StringType, true),
    StructField("file_create_date", StringType, true)))

  val mapRowFunction = (arrStr: Array[String]) => {
    val timestampWithTzFormat = "yyyy-MM-dd HH:mm:ss.SSS"
    val timestampFormat = "yyyy-MM-dd HH:mm:ss"
    val dateFormat = "yyyy-MM-dd"
    Row(
      (if (arrStr(0).isEmpty() || !arrStr(0).matches("[-+]?\\d*\\.?\\d+")) "0" else arrStr(0)).toLong,
      arrStr(1),
      DateTimeUtils.toSqlTimestamp(arrStr(2), timestampWithTzFormat),
      arrStr(3),
      arrStr(4),
      arrStr(5),
      (if (arrStr(6).isEmpty() || !arrStr(6).matches("[-+]?\\d*\\.?\\d+")) "0" else arrStr(6)).toDouble,
      (if (arrStr(7).isEmpty() || !arrStr(7).matches("[-+]?\\d*\\.?\\d+")) "0" else arrStr(7)).toDouble,
      (if (arrStr(8).isEmpty() || !arrStr(8).matches("[-+]?\\d*\\.?\\d+")) "0" else arrStr(8)).toDouble,
      (if (arrStr(9).isEmpty() || !arrStr(9).matches("[-+]?\\d*\\.?\\d+")) "0" else arrStr(9)).toDouble,
      arrStr(10),
      arrStr(11),
      arrStr(12),
      arrStr(13),
      arrStr(14),
      arrStr(15),
      arrStr(16),
      arrStr(17),
      arrStr(18),
      arrStr(19),
      arrStr(20),
      arrStr(21),
      arrStr(22),
      arrStr(23),
      arrStr(24),
      arrStr(25),
      (if (arrStr(26).isEmpty() || !arrStr(26).matches("[-+]?\\d*\\.?\\d+")) "0" else arrStr(26)).toDouble,
      (if (arrStr(27).isEmpty() || !arrStr(27).matches("[-+]?\\d*\\.?\\d+")) "0" else arrStr(27)).toDouble,
      arrStr(28),
      arrStr(29),
      arrStr(30),
      arrStr(31),
      arrStr(32),
      (if (arrStr(33).isEmpty() || !arrStr(33).matches("[-+]?\\d*\\.?\\d+")) "0" else arrStr(33)).toDouble,
      arrStr(34),
      (if (arrStr(35).isEmpty() || !arrStr(35).matches("[-+]?\\d*\\.?\\d+")) "0" else arrStr(35)).toDouble,
      (if (arrStr(36).isEmpty() || !arrStr(36).matches("[-+]?\\d*\\.?\\d+")) "0" else arrStr(36)).toDouble,
      (if (arrStr(37).isEmpty() || !arrStr(37).matches("[-+]?\\d*\\.?\\d+")) "0" else arrStr(37)).toDouble,
      arrStr(38),
      arrStr(39),
      arrStr(40),
      arrStr(41),
      arrStr(42))
  }
}