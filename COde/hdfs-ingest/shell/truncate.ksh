#!/bin/ksh

errorHandle() {

if [ $1 -ne 0 ]
	then
		${HADOOP_SCRIPT_LOCATION}/errorHandle.ksh "$1" "$2" "$3"
		exit $1
fi

}

export ORACLE_HOME=/opt/oracle/product/10.2.0.3

## password cipher variables
## =================================================
JAVA_HOME=/opt/java6
PASSWORD_CIPHER=/home/ssmown/ssm/fxg-security-client-1.0.3.jar
temp=`$JAVA_HOME/jre/bin/java -cp $PASSWORD_CIPHER com.fedex.ground.security.common.util.FxgPasswordCipher -g spbi SPUDS_ETL_APP /home/ssmown/ssm 2> /dev/null`

export SPUDS_ETL_APP_USER=`echo "$temp" | awk '{ print $1 }';`
export SPUDS_ETL_APP_PASSWORD=`echo "$temp" | awk '{ print $2 }';` 
export service_name_jdbc="spuds_edw_svc1_PRD"  
## **********************************************************

table=$1

echo "Starting to truncate: " $table

$ORACLE_HOME/bin/sqlplus -s ${SPUDS_ETL_APP_USER}/${SPUDS_ETL_APP_PASSWORD}@${service_name_jdbc} << EOF
execute truncate_table  ('${table}')
commit
EXIT
EOF

# Error handling
retCode=$?
errorHandle "$retCode" "${table}" "Failed to truncate table!"


oraclecount=`$ORACLE_HOME/bin/sqlplus -s ${SPUDS_ETL_APP_USER}/${SPUDS_ETL_APP_PASSWORD}@${service_name_jdbc} << EOF
set head off
select count(*) from SPUDS_SCHEMA.${table};
EXIT
EOF`

# Error handling
retCode=$?
errorHandle "$retCode" "${table}" "Failed to get count table!"

echo "Count after truncate: " $oraclecount
echo "Finished truncating: " $table

# Error handling
retCode=$?
errorHandle "$retCode" "${table}" "Failed to truncate table!"