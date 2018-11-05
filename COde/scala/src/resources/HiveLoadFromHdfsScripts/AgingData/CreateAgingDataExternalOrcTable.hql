CREATE EXTERNAL TABLE IF NOT EXISTS hdp_smp.aging_data_text
(
ce_se_globl_enti_id	BIGINT,
ce_se_globl_enti_nm	STRING,
shipper_acct_nbr        BIGINT,
shipper_name                      STRING,
expected_sort_hub                 STRING,
event_dt                          STRING,
day                               STRING,
total_records                     BIGINT,
total_sort                        BIGINT,
total_aau                         BIGINT,
total_stc                         BIGINT)
ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t' 
STORED as TEXTFILE;
