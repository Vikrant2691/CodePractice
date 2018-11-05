CREATE TABLE smartpost.postal_errors
(
type                STRING  COMMENT 'E=Error, W=Warning', 
line_number         STRING,
tracking_bar_code   STRING, 
field_in_error      STRING COMMENT 'Example PM-02',
message             STRING COMMENT 'Reason electronic file field is in error or warning'     
)
PARTITIONED BY (usps_file_date STRING)
CLUSTERED BY (tracking_bar_code)
SORTED BY (tracking_bar_code)
INTO 8 BUCKETS
STORED as ORC tblproperties ("orc.compress" = "SNAPPY");
