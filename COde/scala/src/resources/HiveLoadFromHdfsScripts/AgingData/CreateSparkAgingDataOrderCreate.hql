CREATE EXTERNAL TABLE IF NOT EXISTS hdp_smp.aging_oc_staging_spark(
global_entity_id BIGINT, 
global_entity_nm STRING, 
account_number BIGINT, 
hub_id STRING, 
event_dt STRING, 
day_of_week STRING, 
oc_records BIGINT, 
insert_timestamp TIMESTAMP
) 
ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t' 
STORED as TEXTFILE;