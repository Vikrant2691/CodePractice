SET default_parallel 1;

register FxspHadoopUtils.jar;

define parseString      com.fedex.smartpost.etl.types.java.ParseString();
define parseDateTime    com.fedex.smartpost.etl.types.datetime.ParseNullableDateTime();
define parseDate        com.fedex.smartpost.etl.types.datetime.ParseNullableDateTime();
define parseBigDecimal  com.fedex.smartpost.etl.types.java.ParseNullableBigDecimal();
define parseLong        com.fedex.smartpost.etl.types.java.ParseNullableLong();
define getTimeZoneId    com.fedex.smartpost.etl.types.datetime.GetTimeZoneId();

stopIdFileWithHeader = LOAD '$path' USING PigStorage() AS (row:bytearray);

stopIdFile = FILTER stopIdFileWithHeader BY NOT $0 MATCHES 'SP.*';

stopIdMap = FOREACH stopIdFile GENERATE 

  (long)      parseLong(row,    0,   20)   AS upn,
  (datetime)  parseDateTime(row, 20, 50, 'yyyy-MM-dd HH:mm:ss.SSSZ') AS event_timestamp_utc,
  (chararray) getTimeZoneId(parseString(row, 20, 50), 'yyyy-mm-dd HH:mm:ss.SSSZ') AS event_timezone,
  (chararray) parseString(row,   50,   85) AS postal_barcode,                  
  (chararray) parseString(row,   85,  101) AS fxg_tracking_id,
  (chararray) parseString(row,   101, 134) AS stop_id,
  (chararray) parseString(row,   134, 136) AS stop_id_type_cd,
  (chararray) parseString(row,  136,  143) AS stop_id_vintage_cd,
  (chararray) parseString(row,  143,  148) AS intended_delivery_network,
  (chararray) parseString(row,  148,  159) AS fxg_dest_facility,
  (chararray) parseString(row,  159,  170) AS fhd_dest_facility,
  (chararray) parseString(row,  170,  181) AS fxsp_dest_facility,
  (chararray) parseString(row,  181,  202) AS shipper_num,
  (chararray) parseString(row,  202,  204) AS transaction_flag,
  (bigdecimal) parseBigDecimal(row,  204,  213) AS pkg_weight,
  (chararray) parseString(row,  213,  215) AS oversize_flag,
  (datetime) parseDate(row,  215,  226, 'yyyy-MM-dd') AS est_devlivery_date,
  (datetime) parseDate(row,  226,  237, 'yyyy-MM-dd') AS est_facility_date,
  (datetime) parseDate(row,  237,  248, 'yyyy-MM-dd') AS pickup_date,
  (chararray) parseString(row,  248,  254) AS swak_facility,
  (datetime) parseDateTime(row,  254,  274, 'yyyy-MM-dd HH:mm:ss') AS swak_timestamp,
  (chararray) parseString(row,  274,  280) AS original_facility,
  (chararray) parseString(row,  280,  284) AS zip_11_confidence,
  (chararray) parseString(row,  284,  296) AS postal_code,
  (bigdecimal) parseBigDecimal(row,  296,  308) AS latitude,
  (bigdecimal) parseBigDecimal(row,  308,  320) AS longitude,
  (chararray) parseString(row,  320,  324) AS geo_code_score,
  (chararray) parseString(row,  324,  328) AS geo_vendor,
  (chararray) parseString(row,  328,  330) AS geo_code_valid,
  (chararray) parseString(row,  330,  334) AS highrise_indicator,
  (chararray) parseString(row,  334,  349) AS trailer_num,
  (bigdecimal) parseBigDecimal(row,  349,  357) AS pkg_length,
  (bigdecimal) parseBigDecimal(row,  357,  365) AS pkg_width,
  (bigdecimal) parseBigDecimal(row,  365,  373) AS pkg_height,
  (long) parseLong(row,  373,  385) AS pkgs_in_shipment,
  (chararray) parseString(row,  385,  396) AS product_offering,
  (chararray) parseString(row,  396,  400) AS service_code,
  (chararray) '$path'                      AS filename,
  (datetime)  CurrentTime()                AS inserttimestamp;

/*
dump stopIdMap;
*/

store stopIdMap into 'hdp_smp.active_package_orc' using org.apache.hive.hcatalog.pig.HCatStorer('file_create_date=$date');