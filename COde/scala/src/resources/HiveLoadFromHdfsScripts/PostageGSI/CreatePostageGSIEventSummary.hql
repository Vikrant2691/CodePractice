CREATE EXTERNAL TABLE IF NOT EXISTS hdp_smp.pstg_gsi_evnt_smry
(
	shpr_acct_nbr BIGINT,
	event_date_dt TIMESTAMP,	
	mailclass_nbr INTEGER,
	mailclass_desc STRING,	
	ovsz_flg STRING,
	blln_flg STRING,
	loc_fac_co_nm STRING,
	loc_fac_type_cd STRING,
	fxg_rgn_nm STRING,
	fxg_dist_nm STRING,
	loc_fac_id_value_cd STRING,
	customer_nm STRING,
	prod_type_cd STRING,
	ftm_event_cd STRING,
	intnd_dlvr_ntwk_desc STRING,
	gsa_desc STRING,
	ddu_desc STRING,
	scf_desc STRING,
	ndc_desc STRING,
	area_desc STRING,
	dist_desc STRING,
	edas_cd STRING,
	state_cd STRING,
	tot_est_pstg_amt DECIMAL(10, 2),
	tot_actl_pstg_amt DECIMAL(10, 2),
	packagecount_qty INTEGER,
	dest_rate_flg STRING,
	est_net_savings_amt DECIMAL(10, 2),
	est_type_cd STRING,
	est_pstl_chrg_amt DECIMAL(10, 2)
)
PARTITIONED BY ( event_date STRING )
ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t'
STORED AS TEXTFILE
