ALTER TABLE hdp_smp.fxg_tracking_staging
ADD COLUMNS
(
	facility_type		STRING,
	facility_id			STRING,
	facility_id_value	STRING,
	facility_company	STRING,
	filename			STRING,
	inserttimestamp		TIMESTAMP
);  
