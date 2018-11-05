register FxspHadoopUtils.jar;

define parseString     com.fedex.smartpost.etl.types.java.ParseString();
define parseDateTime   com.fedex.smartpost.etl.types.datetime.ParseNullableDateTime();
define parseDate       com.fedex.smartpost.etl.types.datetime.ParseNullableDate();
define parseBigDecimal com.fedex.smartpost.etl.types.java.ParseNullableBigDecimal();
define parseLong       com.fedex.smartpost.etl.types.java.ParseNullableLong();
define parseDouble     com.fedex.smartpost.etl.types.java.ParseNullableDouble();
define parseInt        com.fedex.smartpost.etl.types.java.ParseNullableInt();
define getTimeZoneId   com.fedex.smartpost.etl.types.datetime.GetTimeZoneId();

movementFileWithHeader = LOAD '$path' USING PigStorage() AS (row:bytearray);

movementFile = FILTER movementFileWithHeader BY NOT $0 MATCHES 'SP.*';

mymovement = foreach movementFile GENERATE

  (long)      parseLong(row,       0,  20) AS upn,
  (chararray) parseString(row,    20,  24) AS smartpst_product_code,
  (chararray) parseString(row,    24,  35) AS pkg_type_code,
  (chararray) parseString(row,    35,  66) AS pkg_barcode,
  (chararray) parseString(row,    66,  77) AS pkg_type_code_2,
  (chararray) parseString(row,   77,  108) AS pkg_barcode_2,
  (chararray) parseString(row,  108,  114) AS pkg_event_type_code,
  (datetime)  parseDateTime(row, 114,  138 , 'yyyy-MM-dd HH:mm:ss.SSS') AS pkg_event_timestamp_utc,
  (chararray) parseString(row,      138, 143) AS pkt_event_timezone_offset,
  (chararray) parseString(row,   143, 174) AS publish_app_name,
  (chararray) parseString(row,   174, 185) AS uid_name,
  (chararray) parseString(row,   185, 196) AS sort_crit_name,
  (chararray) parseString(row,   196, 198) AS indc_reject_code,
  (chararray) parseString(row,   198, 200) AS not_on_file_reject_code,
  (chararray) parseString(row,   200, 202) AS scan_carry_over_flag,
  (chararray) parseString(row,   202, 204) AS auto_swak_code,
  (chararray) parseString(row,   204, 206) AS offline_method,
  (chararray) parseString(row,   206, 213) AS dvc_name,
  (chararray) parseString(row,   213, 244) AS scan_appli_name,
  (chararray) parseString(row,   244, 250) AS sorter_name,
  (chararray) parseString(row,   250, 252) AS dock_layout,
  (chararray) parseString(row,   252, 263) AS chute_name,
  (chararray) parseString(row,   263, 271) AS sort_shift_number,
  (chararray) parseString(row,   271, 285) AS destination_sort_code,
  (chararray) parseString(row,   285, 288) AS distribution_center,
  (chararray) parseString(row,   288, 290) AS weight_source_code, 
  (chararray) parseString(row,   290, 292) AS weight_unit, 
  (bigdecimal)parseBigDecimal(row,   292, 300) AS pkg_weight, 
  (chararray) parseString(row,   300, 302) AS dim_source_code, 
  (chararray) parseString(row,   302, 304) AS dim_unit, 
  (bigdecimal) parseBigDecimal(row,   304, 311) AS pkg_length, 
  (bigdecimal) parseBigDecimal(row,   311, 318) AS pkg_width, 
  (bigdecimal) parseBigDecimal(row,   318, 325) AS pkg_height, 
  (chararray) parseString(row,   325, 330) AS hub_code, 
  (chararray) parseString(row,   330, 341) AS dest_sort_group, 
  (chararray) parseString(row,   341, 355) AS entr_point_postal_code, 
  (chararray) parseString(row,   355, 357) AS routing_code, 
  (chararray) parseString(row,   357, 359) AS mail_class, 
  (chararray) parseString(row,   359, 361) AS mail_sub_class, 
  (chararray) parseString(row,   361, 363) AS del_confirm_flag, 
  (chararray) parseString(row,   363, 365) AS prcs_size, 
  (chararray) parseString(row,   365, 367) AS prcs_category, 
  (chararray) parseString(row,   367, 373) AS prcs_entr_code, 
  (chararray) parseString(row,   373, 394) AS customer_manifest_id,
  (chararray) parseString(row,   394, 404) AS customer_mail_id,
  (chararray) parseString(row,   404, 419) AS container_name,
  (chararray) parseString(row,   419, 424) AS billing_service_code,
  (chararray) parseString(row,   424, 426) AS persh_flag,
  (chararray) parseString(row,   426, 428) AS other_regty_material_flagh,
  (chararray) parseString(row,   428, 430) AS dspn_flag,
  (chararray) parseString(row,   430, 461) AS grnd_pkg_barcode,
  (chararray) parseString(row,   461, 466) AS grnd_hub_code,
  (chararray) parseString(row,   466, 476) AS grnd_carrier_xmit_control,
  (chararray) parseString(row,   476, 487) AS grnd_depart_event_code,
  (bigdecimal) parseBigDecimal(row,   487, 495) AS grnd_pkg_weight,
  (chararray) parseString(row,   495, 497) AS grnd_weight_unit,
  (bigdecimal) parseBigDecimal(row,   497, 505) AS grnd_pkg_width,
  (bigdecimal) parseBigDecimal(row,   505, 513) AS grnd_pkg_length,
  (bigdecimal) parseBigDecimal(row,   513, 521) AS grnd_pkg_height,
  (chararray) parseString(row,   521, 523) AS grnd_dim_unit,
  (datetime)  parseDateTime(row, 523, 547, 'yyyy-MM-dd HH:mm:ss.SSS') AS grnd_dprt_del_event_tmstp_utc,
  (chararray) parseString(row,   547, 552)    AS grnd_dprt_timezone_offset,
  (chararray) parseString(row,   552, 555) AS grnd_address_usage_type,
  (chararray) parseString(row,   555, 588) AS grnd_city,
  (chararray) parseString(row,   588, 591) AS grnd_state,
  (chararray) parseString(row,   591, 594) AS grnd_country,
  (chararray) parseString(row,   594, 596) AS billing_del_type,
  (datetime)  parseDateTime(row,   596, 620, 'yyyy-MM-dd HH:mm:ss.SSS') AS billing_del_timestamp_utc,
  (chararray) parseString(row,   620, 625)    AS billing_del_timezone_offset,
  (chararray) parseString(row,   625, 636) AS billing_group,
  (datetime)  parseDateTime(row,   636, 660, 'yyyy-MM-dd HH:mm:ss.SSS') AS billing_group_release_tmstp_utc,
  (chararray) parseString(row,   660, 665) AS billing_group_release_tz_offset,
  (chararray) parseString(row,   665, 668) AS billing_address_usage_type,
  (chararray) parseString(row,   668, 680) AS billing_postal_code,
  (chararray) parseString(row,   680, 683) AS billing_country,
  (int)       parseInt(row,   683, 693)    AS customer_account_number,
  (chararray) parseString(row,   693, 697) AS usps_service_code,
  (chararray) parseString(row,   697, 708) AS trans_sort_group_name,
  (chararray) parseString(row,   708, 729) AS mac_address,
  (chararray) parseString(row,   729, 761) AS sort_server_name, 
  (chararray) '$path'                      AS filename,
  (datetime)  CurrentTime()                AS insert_timestamp;  

rankedByUpn = RANK mymovement BY upn;

mymovementRankedByUpn = FOREACH rankedByUpn GENERATE $1 ..;
 
store mymovementRankedByUpn into 'hdp_smp.movement_orc' using org.apache.hive.hcatalog.pig.HCatStorer('file_create_date=$date');