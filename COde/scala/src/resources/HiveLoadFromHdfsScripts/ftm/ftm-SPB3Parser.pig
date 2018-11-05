register FxspHadoopUtils.jar;

define parseString     com.fedex.smartpost.etl.types.java.ParseString();
define parseDateTime   com.fedex.smartpost.etl.types.datetime.ParseNullableDateTime();
define parseDate       com.fedex.smartpost.etl.types.datetime.ParseNullableDate();
define parseBigDecimal com.fedex.smartpost.etl.types.java.ParseNullableBigDecimal();
define parseLong       com.fedex.smartpost.etl.types.java.ParseNullableLong();
define parseDouble     com.fedex.smartpost.etl.types.java.ParseNullableDouble();
define getTimeZoneId   com.fedex.smartpost.etl.types.datetime.GetTimeZoneId();

ftmFileWithHeader = LOAD '$path' USING PigStorage() AS (row:bytearray);

ftmFile = FILTER ftmFileWithHeader BY NOT $0 MATCHES 'SP.*';

myFtm = foreach ftmFile GENERATE

  (long)      parseLong(row,       0,  20) AS upn,
  (chararray) parseString(row,    20,  55) AS pstl_barcd_nbr,
  (chararray) parseString(row,    55,  69) AS dest_pstl_cd,
  (chararray) parseString(row,    69,  72) AS country_cd,
  (chararray) parseString(row,    72,  74) AS weight_src_cd,
  (double)    parseDouble(row,    74,  83) AS pkg_weight_lbs,
  (chararray) parseString(row,    83,  85)   AS dim_src_cd,
  (double)    parseDouble(row,    85,  93)   AS pkg_length_inch,
  (double)    parseDouble(row,    93, 101)  AS pkg_width_inch,
  (double)    parseDouble(row,   101, 109) AS pkg_height_inch,
  (chararray) parseString(row,   109, 112) AS ftm_event_cd,

  (datetime)  parseDateTime(row, 112, 142, 'yyyy-MM-dd HH:mm:ss.SSSZ') AS ftm_event_datetime_utc,
  (chararray) getTimeZoneId(parseString(row, 112, 142), 'yyyy-mm-dd HH:mm:ss.SSSZ') AS ftm_event_tz,
  
  (chararray) parseString(row,   142, 183) AS city_nm,
  (chararray) parseString(row,   183, 186) AS state,
  (chararray) parseString(row,   186, 192) AS event_type_code,
  (chararray) parseString(row,   192, 196) AS product_type_cd,

  (datetime)  parseDateTime(row, 196, 226, 'yyyy-MM-dd HH:mm:ss.SSSZ') AS edtw_start_datetime_utc,
  (chararray) getTimeZoneId(parseString(row, 196, 226), 'yyyy-mm-dd HH:mm:ss.SSSZ') AS edtw_start_tz,

  (datetime)  parseDateTime(row, 226, 256, 'yyyy-MM-dd HH:mm:ss.SSSZ') AS edtw_end_datetime_utc,
  (chararray) getTimeZoneId(parseString(row, 226, 256), 'yyyy-MM-dd HH:mm:ss.SSSZ') AS edtw_end_tz,


  (double)    parseDouble(row,   256, 262) AS edtw_conf_score_nbr,
  (chararray) parseString(row,   262, 266) AS edtw_reason_cd,
  (chararray) parseString(row,   266, 277) AS edtw_src_sys_nbr,
  (datetime)  parseDateTime(row, 277, 288,'yyyy-MM-dd') AS tendered_dt,
  (chararray) parseString(row,   288, 290) AS pres_cd,
  (chararray) parseString(row,   290, 293) AS facility_type,	
  (chararray) parseString(row,   293, 296) AS facility_id,	
  (chararray) parseString(row,   296, 301) AS facility_id_value,
  (chararray) parseString(row,   301, 352) AS facility_company,
  
  (chararray) '$path'                      AS filename,
  (datetime)  CurrentTime()                AS inserttimestamp;  

rankedByUpn = RANK myFtm BY upn;

myFtmRankedByUpn = FOREACH rankedByUpn GENERATE $1 ..;

store myFtmRankedByUpn into 'hdp_smp.fxg_tracking_orc' using org.apache.hive.hcatalog.pig.HCatStorer('file_create_date=$date');