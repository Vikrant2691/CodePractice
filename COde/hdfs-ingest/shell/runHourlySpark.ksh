errorHandle() {

if [ $1 -ne 0 ]
	then
		${HADOOP_SCRIPT_LOCATION}/errorHandle.ksh "$1" "$2" "$3"
		exit $1
fi

}

echo 'Start Running Reports.'
reports[0]="com.fedex.smartpost.spark.FirstHubTouchMain"
reports[1]="com.fedex.smartpost.spark.AgingDataMain"
reports[2]="com.fedex.smartpost.spark.epdialerts.EpdiAlerts"

${HADOOP_SCRIPT_LOCATION}/kinit.ksh
retCode=$?
errorHandle "$retCode" "kinit" " failed to run!"

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
echo 'Finished Running Reports.'