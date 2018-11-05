#!/bin/ksh

errorHandle() {

if [ $1 -ne 0 ]
	then
		${HADOOP_SCRIPT_LOCATION}/errorHandle.ksh "$1" "$2" "$3"
		exit $1
fi

}

#First parameter is hive table. Second Parameter is Oracle Table

hiveTable=$1
edwTable=$2

echo "Transfer  data from edw table: " $edwTable " to hive table: " $hiveTable

ssh spbown\@prh23100.prod.fedex.com "
sqoop import \
-libjars ${USERLIBTDCH},${TDCHJARS} \
--driver com.teradata.jdbc.TeraDriver \
--connect jdbc:teradata://edwapps.prod.fedex.com/DATABASE=smartpost_prod_view_db \
--username ${FXSP_ETL_USER_APP} \
--password ${FXSP_ETL_USER_APP_PASSWORD} \
--hive-table hdp_smp.${hiveTable} \
--query 'select * from ${edwTable} where \$CONDITIONS' \
--target-dir /user/spbown/${edwTable} \
--hive-import \
--num-mappers 1 \
--hive-overwrite \
--delete-target-dir \
--fields-terminated-by '\t'"
retCode=$?
errorHandle "$retCode"

echo "Finished Transfering data from edw table: " $edwTable " to hive table: " $hiveTable
