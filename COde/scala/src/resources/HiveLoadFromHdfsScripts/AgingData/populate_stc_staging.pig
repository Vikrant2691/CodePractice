set hcat.bin /usr/bin/hcat;

fs -rm -f /data/groups/hdp_smp/hive/aging_stc_staging/*

/*
sql 
  CREATE EXTERNAL TABLE IF NOT EXISTS hdp_smp.aging_stc_staging(account_number BIGINT, event_dt STRING, hub_id STRING, stc_records BIGINT, insert_timestamp TIMESTAMP) ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t' STORED as ORC;
*/

oc_all        = LOAD 'hdp_smp.order_create_orc'     USING org.apache.hive.hcatalog.pig.HCatLoader();
oc_filtered   = FILTER oc_all BY file_create_date >= '20151101' AND file_create_date <= '20151231' AND product_type == 'DOM';

oc_projection = FOREACH oc_filtered 
                GENERATE 
                  upn,
                  account_number, 
                  SUBSTRING(destination_sort_code, 0, 5) as destination_sort_code,
                  ToString(event_time_local_utc,'yyyyMMdd') as event_dt,
                  hub_id;

oc_no_dupes = DISTINCT oc_projection;

hub_determination = LOAD 'hdp_smp.hub_determination' USING org.apache.hive.hcatalog.pig.HCatLoader();
hub_lookup_all = FOREACH hub_determination
                  GENERATE 
                    account_number,
                    destination_sort_code,
                    expected_hub as expected_hub,
                    most_likely_hub_id       AS most_likely_hub_id,
                    num_pkgs_most_likely_hub AS num_pkgs;

hub_lookup_data = FOREACH(GROUP hub_lookup_all BY(account_number, destination_sort_code, expected_hub))
{

  elems = ORDER hub_lookup_all BY num_pkgs DESC;
  first_row = LIMIT elems 1;
  GENERATE 
  FLATTEN(group) AS (account_number, destination_sort_code, expected_hub), 
  FLATTEN(first_row.most_likely_hub_id) as most_likely_hub_id;
}

oc_hub_join = JOIN oc_no_dupes     BY (account_number, destination_sort_code, hub_id),
                   hub_lookup_data BY (account_number, destination_sort_code, expected_hub);
oc_hub_lookup = FOREACH oc_hub_join
{
  GENERATE
      oc_no_dupes::upn            AS upn,
      oc_no_dupes::account_number AS account_number,
      hub_lookup_data::most_likely_hub_id AS most_likely_hub_id,
      event_dt;
}


first_hub_touch_table = LOAD 'hdp_smp.first_hub_touch' USING org.apache.hive.hcatalog.pig.HCatLoader();
first_hub = FOREACH first_hub_touch_table GENERATE upn, hub_id;
first_hub_join = FOREACH (JOIN oc_hub_lookup BY upn LEFT, first_hub BY upn)
{
  GENERATE
      FLATTEN(oc_hub_lookup::upn) AS upn,
      FLATTEN(account_number) AS account_number,
      FLATTEN((first_hub::hub_id IS NULL ? oc_hub_lookup::most_likely_hub_id : first_hub::hub_id)) as hub_id,
      FLATTEN(event_dt)       AS event_dt;
}

postal_event_all = LOAD 'hdp_smp.postal_event_orc' USING org.apache.hive.hcatalog.pig.HCatLoader();
stc_filtered     = FILTER postal_event_all BY postal_event_code == '01';
stc_projection   = FOREACH stc_filtered GENERATE upn;
stc_distinct     = DISTINCT stc_projection;

oc_scan_join     = JOIN first_hub_join BY upn, stc_distinct BY upn;
oc_scan_groups   = GROUP oc_scan_join BY (account_number, event_dt, hub_id);

stc_data = FOREACH oc_scan_groups
{
  distinct_upns = DISTINCT oc_scan_join.$0;
  GENERATE 
      FLATTEN(group)                AS (account_number, event_dt, hub_id), 
      FLATTEN(COUNT(distinct_upns)) AS stc_records, 
      (datetime) CurrentTime()      AS insert_timestamp;
}

store stc_data into 'hdp_smp.aging_stc_staging' using org.apache.hive.hcatalog.pig.HCatStorer();