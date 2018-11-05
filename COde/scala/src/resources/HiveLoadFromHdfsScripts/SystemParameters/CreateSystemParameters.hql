CREATE EXTERNAL TABLE hdp_smp.system_parameters
(
name                              STRING, 
value		                      STRING,  
desecription                      STRING
)
ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t' 
STORED as TEXTFILE;