package com.fedex.smartpost.spark.schema

import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.types.LongType
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.types.StructField
import org.apache.spark.sql.Row

object FxspDCustomerSchema {
  val schema = StructType(Array(
    StructField("cust_nbr", LongType, true),
    StructField("cust_char_nbr", StringType, true),
    StructField("cust_nm", StringType, true),
    StructField("cust_acct_stat_flg", StringType, true),
    StructField("corp_acct_stat_cd", StringType, true),
    StructField("rtn_flg", StringType, true),
    StructField("impb_cmpn_flg", StringType, true),
    StructField("del_cnfrm_flg", StringType, true),
    StructField("ce_se_fac_id", LongType, true),
    StructField("ce_se_fac_nm", StringType, true),
    StructField("cs_se_fac_city_nm", StringType, true),
    StructField("ce_se_fac_st_cd", StringType, true),
    StructField("ce_se_sub_group_id", LongType, true),
    StructField("ce_se_sub_group_nm", StringType, true),
    StructField("ce_se_sub_group_city_nm", StringType, true),
    StructField("ce_se_sub_group_st_cd", StringType, true),
    StructField("ce_se_group_id", LongType, true),
    StructField("ce_se_group_nm", StringType, true),
    StructField("ce_se_group_city_nm", StringType, true),
    StructField("ce_se_group_st_cd", StringType, true),
    StructField("ce_se_cntry_id", LongType, true),
    StructField("ce_se_cntry_nm", StringType, true),
    StructField("ce_se_div_id", LongType, true),
    StructField("ce_se_div_nm", StringType, true),
    StructField("ce_se_div_city_nm", StringType, true),
    StructField("ce_se_div_st_cd", StringType, true),
    StructField("ce_se_globl_enti_id", LongType, true),
    StructField("ce_se_globl_enti_nm", StringType, true),
    StructField("ce_se_globl_enti_city_nm", StringType, true),
    StructField("ce_se_globl_enti_st_cd", StringType, true),
    StructField("sp_cust_id", StringType, true),
    StructField("natl_acct_nbr", LongType, true),
    StructField("natl_acct_nm", StringType, true),
    StructField("cust_group_nbr", LongType, true),
    StructField("cust_group_nm", StringType, true),
    StructField("sls_terr_nbr", StringType, true),
    StructField("batch_id_nm", StringType, true),
    StructField("b_load_dt_tmstp", StringType, true)))

  val mapRowFunction = (arrStr: Array[String]) => {
    Row(
      (if (arrStr(0).isEmpty() || !arrStr(0).matches("[-+]?\\d*\\.?\\d+")) "0" else arrStr(0)).toLong,
      arrStr(1),
      arrStr(2),
      arrStr(3),
      arrStr(4),
      arrStr(5),
      arrStr(6),
      arrStr(7),
      (if (arrStr(8).isEmpty() || !arrStr(8).matches("[-+]?\\d*\\.?\\d+")) "0" else arrStr(8)).toLong,
      arrStr(9),
      arrStr(10),
      arrStr(11),
      (if (arrStr(12).isEmpty() || !arrStr(12).matches("[-+]?\\d*\\.?\\d+")) "0" else arrStr(12)).toLong,
      arrStr(13),
      arrStr(14),
      arrStr(15),
      (if (arrStr(16).isEmpty() || !arrStr(16).matches("[-+]?\\d*\\.?\\d+")) "0" else arrStr(16)).toLong,
      arrStr(17),
      arrStr(18),
      arrStr(19),
      (if (arrStr(20).isEmpty() || !arrStr(20).matches("[-+]?\\d*\\.?\\d+")) "0" else arrStr(20)).toLong,
      arrStr(21),
      (if (arrStr(22).isEmpty() || !arrStr(22).matches("[-+]?\\d*\\.?\\d+")) "0" else arrStr(22)).toLong,
      arrStr(23),
      arrStr(24),
      arrStr(25),
      (if (arrStr(26).isEmpty() || !arrStr(26).matches("[-+]?\\d*\\.?\\d+")) "0" else arrStr(26)).toLong,
      arrStr(27),
      arrStr(28),
      arrStr(29),
      arrStr(30),
      (if (arrStr(31).isEmpty() || !arrStr(31).matches("[-+]?\\d*\\.?\\d+")) "0" else arrStr(31)).toLong,
      arrStr(32),
      (if (arrStr(33).isEmpty() || !arrStr(33).matches("[-+]?\\d*\\.?\\d+")) "0" else arrStr(33)).toLong,
      arrStr(34),
      arrStr(35),
      arrStr(36),
      arrStr(37))
  }
}