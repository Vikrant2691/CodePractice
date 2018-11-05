set hcat.bin /usr/bin/hcat;

/*
sql DROP TABLE IF EXISTS hdp_smp.aging_scan_staging;
*/


fs -rm -f /data/groups/hdp_smp/hive/aging_scan_staging/*

/*
sql CREATE EXTERNAL TABLE 
    IF NOT EXISTS hdp_smp.aging_scan_staging(account_number BIGINT, event_dt STRING, hub_id STRING, scan_records BIGINT, insert_timestamp TIMESTAMP) ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t' STORED as ORC;
*/
oc_all        = LOAD 'hdp_smp.order_create_orc'     USING org.apache.hive.hcatalog.pig.HCatLoader();
oc_filtered   = FILTER oc_all BY file_create_date >= '20151101' AND file_create_date <= '20151231' AND product_type == 'DOM';

oc_projection = FOREACH oc_filtered 
                GENERATE
                upn AS upn,
                account_number AS account_number, 
                ToString(event_time_local_utc,'yyyyMMdd') AS event_dt, 
                hub_id AS hub_id;

oc_data = DISTINCT oc_projection;

scan_all = LOAD 'hdp_smp.first_hub_touch' USING org.apache.hive.hcatalog.pig.HCatLoader();
scan_projection = FOREACH scan_all GENERATE upn as scan_upn, hub_id as first_hub_id;

oc_scan_join   = JOIN oc_data BY upn, scan_projection BY scan_upn;

oc_scan_groups = GROUP   oc_scan_join BY (account_number,  event_dt, first_hub_id);
group_data     = FOREACH oc_scan_groups
{
  GENERATE 
   FLATTEN(group)            AS (account_number, event_dt, hub_id), 
   COUNT(oc_scan_join.$0)    AS scan_records,  
   (datetime)  CurrentTime() AS insert_timestamp;
}

store group_data into 'hdp_smp.aging_scan_staging' using org.apache.hive.hcatalog.pig.HCatStorer();