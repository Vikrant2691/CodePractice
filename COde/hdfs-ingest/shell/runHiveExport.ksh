#!/bin/ksh

errorHandle() {

if [ $1 -ne 0 ]
	then
		${HADOOP_SCRIPT_LOCATION}/errorHandle.ksh "$1" "$2" "$3"
		exit $1
fi

}

${HADOOP_SCRIPT_LOCATION}/kinit.ksh
retCode=$?
errorHandle "$retCode" "kinit" " failed to run!"

export ORACLE_HOME=/opt/oracle/product/10.2.0.3

## password cipher variables
## =================================================
JAVA_HOME=/opt/java6
PASSWORD_CIPHER=/home/ssmown/ssm/fxg-security-client-1.0.3.jar
temp=`$JAVA_HOME/jre/bin/java -cp $PASSWORD_CIPHER com.fedex.ground.security.common.util.FxgPasswordCipher -g spbi SPUDS_ETL_APP /home/ssmown/ssm 2> /dev/null`

export SPUDS_ETL_APP_USER=`echo "$temp" | awk '{ print $1 }';`
export SPUDS_ETL_APP_PASSWORD=`echo "$temp" | awk '{ print $2 }';`
export service_name_jdbc="SPUDS_EDW_SVC1_PRD"  
## **********************************************************



hiveTables[0]="aging_report"
hiveTables[1]="filtered_epdi_notifications_flagged"
hiveTables[2]="filtered_epdi_notifications_alerts"
hiveTables[3]="filtered_epdi_notifications_supplemental"
oracleTables[0]="BI_AGING_S_VW"
oracleTables[1]="BI_EPDI_NOTIF_FLAGGED_S_VW"
oracleTables[2]="BI_EPDI_NOTIF_ALERTS_S_VW"
oracleTables[3]="BI_EPDI_NOTIF_SUPP_S_VW"

let i=0
while [[ $i -lt ${#hiveTables[*]} ]] ; do
  #The sqoop.ksh will send the email. If re-run individually it will 
	${HADOOP_SCRIPT_LOCATION}/sqoop.ksh ${hiveTables[$i]} ${oracleTables[$i]}
	retCode=$?
	errorHandle "$retCode"

	let i+=1
done