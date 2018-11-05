#!/bin/ksh

errorHandle() {

if [ $1 -ne 0 ]
	then
		${HADOOP_SCRIPT_LOCATION}/errorHandle.ksh "$1" "$2" "$3"
		exit $1
fi

}

hiveImport() {
	${HADOOP_SCRIPT_LOCATION}/runHiveImport.ksh
	retCode=$?
	errorHandle "$retCode"
}

export HADOOP_SCRIPT_LOCATION=/opt/fedex/spdw_export/hadoop/scripts

hiveImport

reports[0]="com.fedex.smartpost.spark.HubDeterminationMain"
reports[1]="com.fedex.smartpost.spark.epdialerts.CustomerSortPercentage"
reports[2]="com.fedex.smartpost.spark.epdialerts.CustomerWaitModel"

${HADOOP_SCRIPT_LOCATION}/kinit.ksh
retCode=$?
errorHandle "$retCode" "kinit" " failed to run!"

echo 'Start Running Jobs.'
let i=0
while [[ $i -lt ${#reports[*]} ]] ; do
		echo 'Starting ' ${reports[$i]} ' Report'
        ssh spbown\@prh23100.prod.fedex.com "spark-submit --master yarn --class "${reports[$i]}" hadoop-etl.jar"
		# Error handling
		retCode=$?
		errorHandle "$retCode" "${reports[$i]}" " failed to run!"
			
		echo 'Finished ' ${reports[$i]} ' Report'		
        let i+=1
done
echo 'Finished Running Jobs.'