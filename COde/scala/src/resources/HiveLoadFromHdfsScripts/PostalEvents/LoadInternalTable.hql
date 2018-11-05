SET hive.enforce.bucketing = true;

INSERT INTO TABLE postal_errors 
PARTITION (usps_file_date=“<date partition to load>”) 
SELECT  *
FROM postal_errors_staging;

ANALYZE TABLE postal_errors PARTITION(uspsFileDate=“<date partition to load>")
COMPUTE STATISTICS for COLUMNS;