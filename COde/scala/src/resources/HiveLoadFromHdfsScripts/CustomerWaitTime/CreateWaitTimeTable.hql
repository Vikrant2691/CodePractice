CREATE TABLE hdp_smp.customer_wait_time
(
	hub_id				STRING,
	account_number		STRING,
	day_of_week			STRING,
	percentile_10		BIGINT,
	percentile_20		BIGINT,
	percentile_30		BIGINT,
	percentile_40		BIGINT,
	percentile_50		BIGINT,
	percentile_60		BIGINT,
	percentile_70		BIGINT,
	percentile_80		BIGINT,
	percentile_90		BIGINT,
	percentile_91		BIGINT,
	percentile_92		BIGINT,
	percentile_93		BIGINT,
	percentile_94		BIGINT,
	percentile_95		BIGINT,
	percentile_96		BIGINT,
	percentile_97		BIGINT,
	percentile_98		BIGINT,
	percentile_99		BIGINT,
	average				BIGINT,
	count				BIGINT,
	insert_timestamp	TIMESTAMP
)
ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t' 
STORED as ORC;