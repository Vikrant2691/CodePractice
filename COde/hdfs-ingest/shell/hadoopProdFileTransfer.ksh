#!/bin/ksh

errorHandle() {

if [ $1 -ne 0 ]
	then
		${HADOOP_SCRIPT_LOCATION}/errorHandle.ksh "$1" "$2" "$3"
		exit $1
fi

}

uploadFiles() {
	${HADOOP_SCRIPT_LOCATION}/uploadFiles.ksh "$prevHour"
	retCode=$?
	errorHandle "$retCode"
}

runReports() {
	${HADOOP_SCRIPT_LOCATION}/runHourlySpark.ksh
	retCode=$?
	errorHandle "$retCode"
}

truncateOracleTables() {
	${HADOOP_SCRIPT_LOCATION}/runTruncate.ksh
	retCode=$?
	errorHandle "$retCode"
}

exportHiveTables() {
	${HADOOP_SCRIPT_LOCATION}/runHiveExport.ksh
	retCode=$?
	errorHandle "$retCode"
}

loadOracleTables() {
	${HADOOP_SCRIPT_LOCATION}/loadOracleTables.ksh
	retCode=$?
	errorHandle "$retCode"
}

cd /opt/fedex/spdw_export/speeds-acq/archive
export HADOOP_SCRIPT_LOCATION=/opt/fedex/spdw_export/hadoop/scripts

uploadFiles
runReports
truncateOracleTables
exportHiveTables
loadOracleTables

exit 0