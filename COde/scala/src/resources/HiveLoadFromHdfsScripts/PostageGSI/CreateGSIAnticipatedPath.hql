CREATE EXTERNAL TABLE IF NOT EXISTS hdp_smp.pstg_gsi_ap_counts
(
       shpr_acct_nbr BIGINT,
       event_date_dt TIMESTAMP,  
       intnd_dlvr_ntwk_desc STRING,
       office_wsc_pkg_count DECIMAL(10, 2),   
       fxg_pkg_count DECIMAL(10, 2),    
       fhd_pkg_count DECIMAL(10, 2),
       fxsp_pkg_count DECIMAL(10, 2),
       usps_pkg_count DECIMAL(10, 2)
)
PARTITIONED BY ( file_create_date STRING )
ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t'
STORED AS TEXTFILE;