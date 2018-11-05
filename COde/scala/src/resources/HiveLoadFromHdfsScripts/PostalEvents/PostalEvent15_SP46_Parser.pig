--SET hive.exec.dynamic.partition.mode nonstrict;
--SET hive.exec.dynamic.partition true;

register FxspHadoopUtils.jar;

define parseString     com.fedex.smartpost.etl.types.java.ParseString();
define parseDate       com.fedex.smartpost.etl.types.datetime.ParseNullableDateTime();
define parseBigDecimal com.fedex.smartpost.etl.types.java.ParseNullableBigDecimal();
define parseLong       com.fedex.smartpost.etl.types.java.ParseNullableLong();
define getTimezone     com.fedex.smartpost.etl.types.datetime.GetTimeZoneId();
define convertToTimezone     com.fedex.smartpost.etl.types.datetime.ConvertToTimeZoneId;

postalEventFileWithHeader = LOAD '$path' USING PigStorage('\t','-tagFile') AS (input_file:chararray, row:bytearray);

postalEventFileNoHeader = FILTER postalEventFileWithHeader BY NOT row MATCHES 'SP.*';

postalEvents = foreach postalEventFileNoHeader GENERATE

  (long) parseLong(row, 0, 20) AS upn,
  (datetime) parseDate(row, 124, 148,'yyyy-MM-dd HH:mm:ss.SSS') AS event_timestamp_utc,
  (chararray) convertToTimezone(parseString(row, 148, 153)) as event_timezone_offset,
  (chararray) parseString(row, 20, 24) AS smartpst_product_code,
  (chararray) parseString(row, 24, 34) AS mailer_code,
  (chararray) parseString(row, 34, 45) AS pkg_type_code,
  (chararray) parseString(row, 45, 76) AS pkg_barcode,
  (chararray) parseString(row, 118, 124) AS postal_event_type_code,
  (chararray) parseString(row, 195, 206) AS postal_event_code,
  (chararray) parseString(row, 206, 209) AS country_code,
  (chararray) parseString(row, 209, 242) AS city_name,
  (chararray) parseString(row, 242, 245) AS state_code,
  (chararray) parseString(row, 245, 259) AS postal_code,
  (chararray) parseString(row, 259, 282) AS electronic_file_number,
  (bigdecimal) parseBigDecimal(row, 293, 302) AS pkg_weight,
  (chararray) parseString(row, 302, 304) AS weight_unit_of_measure,
  (datetime) parseDate(row, 304, 334,'yyyy-MM-dd HH:mm:ss.SSSZ') AS usps_svc_guar_dlvr_tmstp_utc,
  (chararray) getTimezone(parseString(row, 334, 364), 'yyyy-MM-dd HH:mm:ss.SSSZ') as usps_svc_guar_dlvr_tz_offset,

  -- Postal 1.6 fields
  (datetime) null AS usps_sched_dlvr_dt,
  (datetime) null AS usps_pred_est_dlvr_dt,
  (chararray) '' AS version,
  (chararray) '' AS origin_package_barcode,
  (chararray) '' AS mailer_name,
  (chararray) '' AS mail_owner_mailer_code,
  (chararray) '' AS logistics_manager_mailer_id,
  (chararray) '' AS scan_facility_zip_code,
  (chararray) '' AS scan_facility_name,
  (chararray) '' AS customer_reference_1,
  (chararray) '' AS customer_reference_2,
  (chararray) '' AS recipient_name,
  (chararray) '' AS delivery_date_mod_indicator,
  (datetime) null AS clock_start_tmstp,
  (chararray) '' AS clock_start_mod_inddicator,
  (chararray) '' AS container_1_name,
  (chararray) '' AS container_1_type,
  (chararray) '' AS container_2_name,
  (chararray) '' AS container_2_type,
  (chararray) '' AS container_3_name,
  (chararray) '' AS container_3_type,
  (chararray) '' AS foreign_postal_code,
  (chararray) '' AS delivery_address_descrip,
  (chararray) '' AS ancilary_service_endorsement,
  (chararray) '' AS address_svc_participant_cd,
  (chararray) '' AS pkg_status_code,
  (chararray) '' AS facility_code,
  (chararray) '' AS facility_type_code,
  (chararray) '' AS gmt_time_descrip,
  (chararray) '' AS gmt_offset_descrip,
  (chararray) '' AS gps_latitude,
  (chararray) '' AS gps_longitude,
  (chararray) '' AS impb_compliance_code,
  (chararray) '' AS address_standard_indicator,
  (datetime) null AS posting_tmstp,
  (chararray) '' AS barcode_input_method,
  (chararray) input_file                       AS filename,
  (datetime)  CurrentTime()                    AS insert_timestamp;

--dump postalEvents;

store postalEvents into 'hdp_smp.postal_event_orc' using org.apache.hive.hcatalog.pig.HCatStorer('file_create_date=$date');
