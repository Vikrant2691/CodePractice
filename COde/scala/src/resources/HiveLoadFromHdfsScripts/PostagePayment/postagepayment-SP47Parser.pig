register FxspHadoopUtils.jar;

define parseString     com.fedex.smartpost.etl.types.java.ParseString();
define parseDateTime   com.fedex.smartpost.etl.types.datetime.ParseNullableDateTime();
define parseDate       com.fedex.smartpost.etl.types.datetime.ParseNullableDate();
define parseBigDecimal com.fedex.smartpost.etl.types.java.ParseNullableBigDecimal();
define parseLong       com.fedex.smartpost.etl.types.java.ParseNullableLong();
define getTimeZoneId   com.fedex.smartpost.etl.types.datetime.GetTimeZoneId();

SET default_parallel 1;

postagePaymentFileWithHeader = LOAD '$path' USING PigStorage() AS (row:bytearray);

postagePaymentFile = FILTER postagePaymentFileWithHeader BY NOT $0 MATCHES 'SP.*';

mypostagePayment = FOREACH postagePaymentFile GENERATE

  (long)      parseLong(row,       0,  20) AS upn,
  (chararray) parseString(row,    20,  26) AS pkg_event_type_cd,
  (datetime)  parseDateTime(row, 26, 50, 'yyyy-MM-dd HH:mm:ss.SSS') AS event_datetime_utc,
  (chararray) parseString(row, 50, 55)     AS event_tz,
  (chararray) parseString(row,    55,  66) AS pkg_type_cd_1,
  (chararray) parseString(row,    66,  97) AS pkg_barcd_nbr_1,
  (bigdecimal)    parseBigDecimal(row,    97,  105) AS pkg_weight_lbs,
  (bigdecimal)    parseBigDecimal(row,    105, 112) AS pkg_length_inch,
  (bigdecimal)    parseBigDecimal(row,    112, 119) AS pkg_width_inch,
  (bigdecimal)    parseBigDecimal(row,   119, 126) AS pkg_height_inch,
  (chararray) parseString(row,   126, 140) AS dest_sort_cd,  
  (chararray) parseString(row,   140, 154) AS entr_point_pstl_cd,
  (chararray) parseString(row,   154, 159) AS hub_cd,
  (chararray) parseString(row,   159, 161) AS prcs_size_cd,
  (chararray) parseString(row,   161, 164) AS mail_class_cd,
  (chararray) parseString(row,   164, 166) AS prcs_ctgy_cd,
  (chararray) parseString(row,   166, 168) AS usps_dest_rate_cd,
  (chararray) parseString(row,   168, 171) AS usps_zone_cd,
  (chararray) parseString(row,   171, 173) AS usps_barcd_cd,
  (chararray) parseString(row,   173, 177) AS usps_prod_cd,
  (chararray) parseString(row,   177, 179) AS pkg_relse_cd,
  (chararray) parseString(row,   179, 189) AS usps_clint_mail_cd,
  (chararray) parseString(row,   189, 213) AS load_close_tmstp,
  (chararray) parseString(row,   213, 218) AS load_close_tz_offset_nbr,
  (chararray) parseString(row,   218, 220) AS usps_del_cnfrm_cd,
  (chararray) parseString(row,   220, 225) AS pstl_rate_cd,
  (bigdecimal) 	  parseBigDecimal(row,   225, 234) AS pstl_chrg_amt,
  (bigdecimal) 	  parseBigDecimal(row,   234, 242) AS pstl_wgt,
  (chararray) parseString(row,   242, 293) AS usps_clint_mailer_nm,
  (chararray) parseString(row,   293, 296) AS umfst_stat_cd,
  (chararray) parseString(row,   296, 306) AS umfst_rect_src_cd,
  (chararray) parseString(row,   306, 317) AS umfst_usps_scan_dt,
  (chararray) parseString(row,   317, 328) AS umfst_usps_prcs_dt,
  (bigdecimal) 	  parseBigDecimal(row,   328, 337) AS total_pstl_chrg_amt,
  (chararray) parseString(row,   337, 348) AS est_flg,
  (bigdecimal) 	  parseBigDecimal(row,   348, 357) AS est_ddu_pstg_amt,
  (bigdecimal) 	  parseBigDecimal(row,   357, 366) AS est_scf_pstg_amt,
  (bigdecimal) 	  parseBigDecimal(row,   366, 375) AS est_ndc_pstg_amt,
  (chararray) parseString(row,   375, 377) AS suppr_pstg_flg,
  (chararray) parseString(row,   377, 380) AS suppr_resn_cd,
  
  (chararray) '$path'                      AS filename,
  (datetime)  CurrentTime()                AS inserttimestamp;  

rankedByUpn = RANK mypostagePayment BY upn;

mypostagePaymentRankedByUpn = FOREACH rankedByUpn GENERATE $1 ..;

store mypostagePaymentRankedByUpn into 'hdp_smp.postage_payment' using org.apache.hive.hcatalog.pig.HCatStorer('file_create_date=$date');