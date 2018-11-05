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

## password cipher variables
## =================================================
export FXSP_ETL_USER_APP=fxspetl
export FXSP_ETL_USER_APP_PASSWORD=Z9qav93xLP1Hmd4eUggPACATh
export USERLIBTDCH=/usr/hdp/current/sqoop-client/lib/teradata-connector-1.4.1-hadoop2.jar
export TDCHJAR=/usr/hdp/current/sqoop-client/lib/teradata-connector-1.4.1-hadoop2.jar
export TDCHJARS=$TDCHJAR,/usr/hdp/current/sqoop-client/lib/tdgssconfig.jar,/usr/hdp/current/sqoop-client/lib/terajdbc4.jar
## **********************************************************



hiveTables[0]="fxsp_d_customer"
hiveTables[1]="fxsp_d_location"
hiveTables[2]="fxsp_usps_postal_code"
hiveTables[3]="fedex_ground_customer_master"
edwTables[0]="smartpost_prod_view_db.fxsp_d_customer"
edwTables[1]="smartpost_prod_view_db.fxsp_d_location"
edwTables[2]="smartpost_prod_view_db.fxsp_usps_postal_code"
edwTables[3]="fedex_ground_prod_view_db.fedex_ground_customer_master"

let i=0
while [[ $i -lt ${#hiveTables[*]} ]] ; do
  #The import.ksh will send the email. If re-run individually it will 
	${HADOOP_SCRIPT_LOCATION}/import.ksh ${hiveTables[$i]} ${edwTables[$i]}
	retCode=$?
	errorHandle "$retCode"

	let i+=1
done