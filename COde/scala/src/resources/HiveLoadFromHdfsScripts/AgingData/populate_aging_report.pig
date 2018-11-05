set hcat.bin /usr/bin/hcat;


fs -rm -f /data/groups/hdp_smp/hive/aging_report/*

/*
sql CREATE EXTERNAL TABLE IF NOT EXISTS 
hdp_smp.aging_report(global_entity_id BIGINT, global_entity_nm STRING, account_number BIGINT, hub_id STRING, event_dt STRING, day_of_week STRING, oc_records BIGINT, scan_records BIGINT, aau_records BIGINT, stc_records BIGINT, insert_timestamp TIMESTAMP) 
ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t' STORED as ORC;
*/
oc   = LOAD 'hdp_smp.aging_oc_staging'   USING org.apache.hive.hcatalog.pig.HCatLoader();
scan = LOAD 'hdp_smp.aging_scan_staging' USING org.apache.hive.hcatalog.pig.HCatLoader();
aau  = LOAD 'hdp_smp.aging_aau_staging'  USING org.apache.hive.hcatalog.pig.HCatLoader();
stc  = LOAD 'hdp_smp.aging_stc_staging'  USING org.apache.hive.hcatalog.pig.HCatLoader();


postal_join = JOIN aau BY (account_number, event_dt, hub_id) FULL OUTER, stc BY (account_number, event_dt, hub_id);
postal_data = FOREACH postal_join 
              GENERATE 
                aau::account_number AS account_number, 
                aau::event_dt       AS event_dt,
                aau::hub_id         AS hub_id,
                aau::aau_records   AS aau_records,
                stc::stc_records   AS stc_records;

postal_scan_join = JOIN postal_data BY (account_number, event_dt, hub_id) FULL OUTER, scan BY (account_number, event_dt, hub_id);
postal_scan_data = FOREACH postal_scan_join 
                   GENERATE 
                    postal_data::account_number AS account_number, 
                    postal_data::event_dt       AS event_dt,
                    postal_data::hub_id         AS hub_id,
                    scan::scan_records          AS scan_records,
                    postal_data::aau_records    AS aau_records,
                    postal_data::stc_records    AS stc_records;


oc_join = JOIN oc BY (account_number, event_dt, hub_id) LEFT OUTER, postal_scan_data BY (account_number, event_dt, hub_id);
oc_data = FOREACH oc_join 
          GENERATE 
            oc::global_entity_id           AS global_entity_id,
            oc::global_entity_nm           AS global_entity_nm,                    
            oc::account_number             AS account_number,    
            oc::hub_id                     AS hub_id,               
            oc::event_dt                   AS event_dt, 
            oc::day_of_week                AS day_of_week,
            oc::oc_records                 AS oc_records, 
            (postal_scan_data::scan_records is NULL ? 0 : postal_scan_data::scan_records) AS scan_records,
            (postal_scan_data::aau_records  is NULL ? 0 : postal_scan_data::aau_records)  AS aau_records,
            (postal_scan_data::stc_records  is NULL ? 0 : postal_scan_data::stc_records)  AS stc_records,
                
 (datetime) CurrentTime() AS insert_timestamp;

                                     
store oc_data into 'hdp_smp.aging_report' using org.apache.hive.hcatalog.pig.HCatStorer();