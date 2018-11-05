CREATE EXTERNAL TABLE hdp_smp.whitelist_customers
(
account_number                    STRING
)
ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t' 
STORED as TEXTFILE;