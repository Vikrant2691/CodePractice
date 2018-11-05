register FxspHadoopUtils.jar;

define parseString     com.fedex.smartpost.etl.types.java.ParseString();
define parseDate       com.fedex.smartpost.etl.types.datetime.ParseNullableDateTime();
define parseBigDecimal com.fedex.smartpost.etl.types.java.ParseNullableBigDecimal();
define parseLong       com.fedex.smartpost.etl.types.java.ParseNullableLong();
define getTimeZoneId   com.fedex.smartpost.etl.types.datetime.GetTimeZoneId();

orderCreateFileWithHeader = LOAD '$path' USING PigStorage() AS (row:bytearray);

orderCreateFile = FILTER orderCreateFileWithHeader BY NOT $0 MATCHES 'SP.*';

myOrderCreate = FOREACH orderCreateFile GENERATE 

  (long)      parseLong(row,    0,   20)   AS upn,
  (chararray) parseString(row,   20,   24) AS product_type,
  (long)      parseLong(row,   24,   34)   AS account_number,                  
  (chararray) parseString(row,   34,   40) AS customer_id,
  (chararray) parseString(row,   40,   76) AS company,
  (chararray) parseString(row,   76,  112) AS contact,
  (chararray) parseString(row,  112,  138) AS shipper_address_share_id,
  (chararray) parseString(row,  138,  157) AS phone,
  (chararray) parseString(row,  157,  208) AS email,
  (chararray) parseString(row,  208,  219) AS pkg_id_type_1,
  (chararray) parseString(row,  219,  250) AS pstl_bar_cd,
  (chararray) parseString(row,  250,  261) AS pkg_id_type_2,
  (chararray) parseString(row,  261,  292) AS customer_pkg_id,
  (chararray) parseString(row,  292,  303) AS pkg_id_type_3,
  (chararray) parseString(row,  303,  334) AS pkg_id_3,
  (chararray) parseString(row,  334,  340) AS event_type_code,

  (datetime)  parseDate(row,  340,  370,'yyyy-MM-dd HH:mm:ss.SSSZ') AS event_time_local_utc,

  (chararray) parseString(row,  370,  401) AS publisher,
  (chararray) parseString(row,  401,  415) AS destination_sort_code,
  (chararray) parseString(row,  415,  418) AS distribution_center_id,

  (datetime)  parseDate(row,  418,  442,'yyyy-MM-dd HH:mm:ss.SSS') AS ship_date_time_utc,
  (datetime)  parseDate(row,  442,  466,'yyyy-MM-dd HH:mm:ss.SSS') AS expected_arrival_date_time_utc,

  (chararray) parseString(row,  466,  492) AS bill_of_lading,
  (chararray) parseString(row,  492,  501) AS manifest_group_text,
  (long)      parseLong(row,  501,  511)   AS meter_number,
  (chararray) parseString(row,  511,  542) AS customer_package_id,
  (chararray) parseString(row,  542,  573) AS invoice_number,
  (chararray) parseString(row,  573,  581) AS weight,
  (chararray) parseString(row,  581,  583) AS dimension_source,
  (chararray) parseString(row,  583,  590) AS length,
  (chararray) parseString(row,  590,  597) AS width,
  (chararray) parseString(row,  597,  604) AS height,
  (chararray) parseString(row,  604,  609) AS hub_id,
  (chararray) parseString(row,  609,  611) AS mail_class,
  (chararray) parseString(row,  611,  613) AS mail_sub_class,
  (chararray) parseString(row,  613,  615) AS delivery_confirmation_flag,
  (chararray) parseString(row,  615,  624) AS cust_manifest_id,
  (chararray) parseString(row,  624,  645) AS transmission_seq,
  (chararray) parseString(row,  645,  666) AS shipping_software_vendor,
  (chararray) parseString(row,  666,  687) AS shipping_software_system,
  (chararray) parseString(row,  687,  698) AS shipping_software_vers_desc,
  (chararray) parseString(row,  698,  703) AS billing_svc_cd,
  (chararray) parseString(row,  703,  734) AS purchase_order_number,
  (chararray) parseString(row,  734,  738) AS packaging_type,
  (chararray) parseString(row,  738,  774) AS recipient_company,
  (chararray) parseString(row,  774,  810) AS recipient_contact,
  (chararray) parseString(row,  810,  836) AS recipient_share_id,
  (chararray) parseString(row,  836,  862) AS recipient_raw_address_id,
  (chararray) parseString(row,  862,  888) AS recipient_normalized_address_id,
  (chararray) parseString(row,  888,  914) AS recipient_standardized_addres_id,
  (chararray) parseString(row,  914,  955) AS recipient_address_line_1,
  (chararray) parseString(row,  955,  996) AS recipient_address_line_2,
  (chararray) parseString(row,  996, 1037) AS recipient_address_line_3,
  (chararray) parseString(row, 1037, 1073) AS recipient_city,
  (chararray) parseString(row, 1073, 1076) AS recipient_state,
  (chararray) parseString(row, 1076, 1088) AS recipient_postal_code,
  (chararray) parseString(row, 1088, 1107) AS recipient_phone,
  (chararray) parseString(row, 1107, 1158) AS recipient_email,

  (bigdecimal) parseBigDecimal(row, 1158, 1170) AS recipient_latitude,
  (bigdecimal) parseBigDecimal(row, 1170, 1182) AS recipient_longitude,

  (chararray) parseString(row, 1182, 1193) AS billing_payor_type,
  (chararray) parseString(row, 1193, 1214) AS billing_payor_account_number,
  (chararray) parseString(row, 1214, 1250) AS billing_company,
  (chararray) parseString(row, 1250, 1252) AS return_type_code,
  (chararray) parseString(row, 1252, 1258) AS returns_label_format,
  (chararray) parseString(row, 1258, 1269) AS returns_alt_id_qualifier,
  (chararray) parseString(row, 1269, 1300) AS returns_alt_id,
  (chararray) parseString(row, 1300, 1310) AS returns_account_number,
  (chararray) parseString(row, 1310, 1341) AS returns_merchandise_auth,
  (chararray) parseString(row, 1341, 1343) AS processing_size,
  (chararray) parseString(row, 1343, 1345) AS processing_category,
  (chararray) parseString(row, 1345, 1349) AS usps_service_code,
  (chararray) parseString(row, 1349, 1354) AS intended_delivery_network,
  (chararray) parseString(row, 1354, 1359) AS fxg_destination_id,
  (chararray) parseString(row, 1359, 1364) AS fhd_destination_id,
  (chararray) parseString(row, 1364, 1375) AS fxsp_destination_id,
  (chararray) parseString(row, 1375, 1401) AS shipper_raw_address_id,
  (chararray) parseString(row, 1401, 1427) AS shipper_normalized_address_id,
  (chararray) parseString(row, 1427, 1453) AS shipper_standard_address_id,
  (long)      parseLong(row, 1453, 1457)   AS fxg_service_code,
  (chararray) parseString(row, 1457, 1468) AS fxsp_dest_zip_group_code,
  (chararray) '$path'                      AS filename,
  (datetime)  CurrentTime()                AS inserttimestamp,
  (chararray) getTimeZoneId(parseString(row, 340, 370), 'yyyy-MM-dd HH:mm:ss.SSSZ') as event_time_tz_offset;


rankedByUpn = RANK myOrderCreate BY upn;

orderCreateRankedByUpn = FOREACH rankedByUpn GENERATE $1 ..;

/*
dump orderCreateRankedByUpn;
*/

store orderCreateRankedByUpn into 'hdp_smp.order_create_orc' using org.apache.hive.hcatalog.pig.HCatStorer('file_create_date=$date');