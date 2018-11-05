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

$ORACLE_HOME/bin/sqlplus -s ${SPUDS_ETL_APP_USER}/${SPUDS_ETL_APP_PASSWORD}@${service_name_jdbc} << EOF
set serveroutput on
execute LOAD_BI_EPDI_TABLE  ('${table}')
commit
EXIT
EOF

# Error handling
retCode=$?
errorHandle "$retCode" "${table}" "Failed to Load table!"