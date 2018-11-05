#!/bin/ksh

errorHandle() {

if [ $1 -ne 0 ]
	then
		${HADOOP_SCRIPT_LOCATION}/errorHandle.ksh "$1" "$2" "$3"
		exit $1
fi

}

#First parameter is hive table. Second Parameter is Oracle Table

hiveStagingTable=$1
oracleTable=$2

echo "Transfer data from " $hiveStagingTable " to " $oracleTable

ssh spbown\@prh23100.prod.fedex.com "
sqoop export \
--connect jdbc:oracle:thin:@ldap://oidprd.gss.ground.fedex.com:389/${service_name_jdbc},cn=OracleContext,dc=ground,dc=fedex,dc=com \
--username ${SPUDS_ETL_APP_USER} \
--password ${SPUDS_ETL_APP_PASSWORD} \
--hcatalog-database hdp_smp \
--hcatalog-table ${hiveStagingTable} \
--table SPUDS_SCHEMA.${oracleTable} \
--fields-terminated-by '\t'"

# Error handling
retCode=$?
errorHandle "$retCode" "Failed to export Hive Table: ${hiveStagingTable} to Oracle Staging Table: ${oracleTable}!"

hivecount=$(ssh spbown\@prh23100.prod.fedex.com "hive -e 'SELECT COUNT(*) FROM hdp_smp.${hiveStagingTable}'")

# Error handling
retCode=$?
errorHandle "$retCode" "${table}" "Failed to get count"

oraclecount=`$ORACLE_HOME/bin/sqlplus -s ${SPUDS_ETL_APP_USER}/${SPUDS_ETL_APP_PASSWORD}@${service_name_jdbc} << EOF
set head off
select count(*) from SPUDS_SCHEMA.${oracleTable};
EXIT
EOF`

# Error handling
retCode=$?
errorHandle "$retCode" "${table}" "Failed to get count"

echo "Hive Count: " $hivecount
echo "Oracle Count: " $oraclecount

if [ $hivecount != $oraclecount ]
	then
		errorHandle "-1" "${hiveStagingTable}->${oracleTable}" "Count for tables are different. Sqoop failed to properly export"
fi

echo "Finished Transfering data from " $hiveStagingTable " to " $oracleTable
