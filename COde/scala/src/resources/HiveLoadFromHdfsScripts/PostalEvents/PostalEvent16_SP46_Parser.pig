register FxspHadoopUtils.jar;

define parseString     com.fedex.smartpost.etl.types.java.ParseString();
define parseDate       com.fedex.smartpost.etl.types.datetime.ParseNullableDateTime();
define parseBigDecimal com.fedex.smartpost.etl.types.java.ParseNullableBigDecimal();
define parseLong       com.fedex.smartpost.etl.types.java.ParseNullableLong();
define getTimezone     com.fedex.smartpost.etl.types.datetime.GetTimeZoneId();

postalEventFileWithHeader = LOAD '$path' USING PigStorage('\t','-tagFile') AS (input_file:chararray, row:bytearray);

postalEventFileNoHeader = FILTER postalEventFileWithHeader BY NOT row MATCHES 'SP.*';

postalEvents = foreach postalEventFileNoHeader GENERATE

  (long) parseLong(row, 0, 20) AS upn,
  (datetime) parseDate(row, 20, 50,'yyyy-MM-dd HH:mm:ss.SSSZ') AS event_timestamp_utc,
  (chararray) getTimezone(parseString(row, 20, 50), 'yyyy-MM-dd HH:mm:ss.SSSZ') as event_timezone_offset,
  (chararray) parseString(row, 50, 54) AS smartpst_product_code,
  (chararray) parseString(row, 54, 64) AS mailer_code,
  (chararray) parseString(row, 64, 75) AS pkg_type_code,
  (chararray) parseString(row, 75, 110) AS pkg_barcode,
  (chararray) parseString(row, 110, 116) AS postal_event_type_code,
  (chararray) parseString(row, 116, 127) AS postal_event_code,
  (chararray) parseString(row, 127, 130) AS country_code,
  (chararray) parseString(row, 130, 163) AS city_name,
  (chararray) parseString(row, 163, 166) AS state_code,
  (chararray) parseString(row, 166, 180) AS postal_code,
  (chararray) parseString(row, 180, 215) AS electronic_file_number,
  (bigdecimal) parseBigDecimal(row, 215, 224) AS pkg_weight,
  (chararray) parseString(row, 224, 226) AS weight_unit_of_measure,
  (datetime) parseDate(row, 226, 256,'yyyy-MM-dd HH:mm:ss.SSSZ') AS usps_svc_guar_dlvr_tmstp_utc,
  (chararray) getTimezone(parseString(row, 226, 256), 'yyyy-MM-dd HH:mm:ss.SSSZ') as usps_svc_guar_dlvr_tz_offset,
  (datetime) parseDate(row, 256, 267,'yyyy-MM-dd') AS usps_sched_dlvr_dt,
  (datetime) parseDate(row, 267, 278,'yyyy-MM-dd') AS usps_pred_est_dlvr_dt,
  (chararray) parseString(row, 278, 282) AS version,
  (chararray) parseString(row, 282, 317) AS origin_package_barcode,
  (chararray) parseString(row, 317, 338) AS mailer_name,
  (chararray) parseString(row, 338, 348) AS mail_owner_mailer_code,
  (chararray) parseString(row, 348, 358) AS logistics_manager_mailer_id,
  (chararray) parseString(row, 358, 364) AS scan_facility_zip_code,
  (chararray) parseString(row, 364, 396) AS scan_facility_name,
  (chararray) parseString(row, 396, 427) AS customer_reference_1,
  (chararray) parseString(row, 427, 458) AS customer_reference_2,
  (chararray) parseString(row, 458, 479) AS recipient_name,
  (chararray) parseString(row, 479, 481) AS delivery_date_mod_indicator,
  (datetime) parseDate(row, 481, 501, 'yyyy-MM-dd HH:mm:ss') AS clock_start_tmstp,
  (chararray) parseString(row, 501, 503) AS clock_start_mod_inddicator,
  (chararray) parseString(row, 503, 538) AS container_1_name,
  (chararray) parseString(row, 538, 541) AS container_1_type,
  (chararray) parseString(row, 541, 576) AS container_2_name,
  (chararray) parseString(row, 576, 579) AS container_2_type,
  (chararray) parseString(row, 579, 614) AS container_3_name,
  (chararray) parseString(row, 614, 617) AS container_3_type,
  (chararray) parseString(row, 617, 629) AS foreign_postal_code,
  (chararray) parseString(row, 629, 678) AS delivery_address_descrip,
  (chararray) parseString(row, 678, 682) AS ancilary_service_endorsement,
  (chararray) parseString(row, 682, 692) AS address_svc_participant_cd,
  (chararray) parseString(row, 692, 703) AS pkg_status_code,
  (chararray) parseString(row, 703, 720) AS facility_code,
  (chararray) parseString(row, 720, 741) AS facility_type_code,
  (chararray) parseString(row, 741, 750) AS gmt_time_descrip,
  (chararray) parseString(row, 750, 759) AS gmt_offset_descrip,
  (chararray) parseString(row, 759, 772) AS gps_latitude,
  (chararray) parseString(row, 772, 785) AS gps_longitude,
  (chararray) parseString(row, 785, 788) AS impb_compliance_code,
  (chararray) parseString(row, 788, 791) AS address_standard_indicator,
  (datetime) parseDate(row, 791, 811,'yyyy-MM-dd HH:mm:ss') AS posting_tmstp,
  (chararray) parseString(row, 811, 813) AS barcode_input_method,
  (chararray) input_file                       AS filename,
  (datetime)  CurrentTime()                    AS insert_timestamp;

--dump postalEvents;

store postalEvents into 'hdp_smp.postal_event_orc' using org.apache.hive.hcatalog.pig.HCatStorer('file_create_date=$date');