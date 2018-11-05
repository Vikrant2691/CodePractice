package com.fedex.smartpost.spark.schema

import org.apache.spark.sql.types.DoubleType
import org.apache.spark.sql.types.TimestampType
import org.apache.spark.sql.types.DateType
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.types.LongType
import org.apache.spark.sql.types.StructField
import org.apache.spark.sql.types.StructType

object SP47PostagePaymentSchema {
    val schema = StructType(Array(
        StructField("upn",                      LongType, true),
        StructField("pkg_event_type_cd",        StringType, true),
        StructField("event_datetime_utc",       TimestampType, true),
        StructField("event_tz",                 StringType, true),
        StructField("pkg_type_cd_1",            StringType, true),
        StructField("pkg_barcd_nbr_1",          StringType, true),
        StructField("pkg_weight_lbs",           DoubleType, true),
        StructField("pkg_length_inch",          DoubleType, true),
        StructField("pkg_width_inch",           DoubleType, true),
        StructField("pkg_height_inch",          DoubleType, true),
        StructField("dest_sort_cd",             StringType, true),
        StructField("entr_point_pstl_cd",       StringType, true),
        StructField("hub_cd",                   StringType, true),
        StructField("prcs_size_cd",             StringType, true),
        StructField("mail_class_cd",            StringType, true),
        StructField("prcs_ctgy_cd",             StringType, true),
        StructField("usps_dest_rate_cd",        StringType, true),
        StructField("usps_zone_cd",             StringType, true),
        StructField("usps_barcd_cd",            StringType, true),
        StructField("usps_prod_cd",             StringType, true),
        StructField("pkg_relse_cd",             StringType, true),
        StructField("usps_clint_mail_cd",       StringType, true),
        StructField("load_close_tmstp",         StringType, true),
        StructField("load_close_tz_offset_nbr", StringType, true),
        StructField("usps_del_cnfrm_cd",        StringType, true),
        StructField("pstl_rate_cd",             StringType, true),
        StructField("pstl_chrg_amt",            DoubleType, true),
        StructField("pstl_wgt",                 DoubleType, true),
        StructField("usps_clint_mailer_nm",     StringType, true),
        StructField("umfst_stat_cd",            StringType, true),
        StructField("umfst_rect_src_cd",        StringType, true),
        StructField("umfst_usps_scan_dt",       StringType, true),
        StructField("umfst_usps_prcs_dt",       StringType, true),
        StructField("total_pstl_chrg_amt",      DoubleType, true),
        StructField("est_flg",                  StringType, true),
        StructField("est_ddu_pstg_amt",         DoubleType, true),
        StructField("est_scf_pstg_amt",         DoubleType, true),
        StructField("est_ndc_pstg_amt",         DoubleType, true),
        StructField("suppr_pstg_flg",           StringType, true),
        StructField("suppr_resn_cd",            StringType, true),
        StructField("filename",                 StringType, true),
        StructField("inserttimestamp",          StringType, true)
    ))
}