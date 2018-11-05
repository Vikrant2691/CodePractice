CREATE TABLE hdp_smp.customer_sort_percentage
(
	account_number		STRING,
	sort_percentage		int,
	insert_timestamp	TIMESTAMP
)
ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t' 
STORED as ORC;