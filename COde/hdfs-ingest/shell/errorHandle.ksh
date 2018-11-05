email() {

	echo "$1 $3" | mailx -s "$1 $3 on error code $2." fxsp-edw-etl-notification@corp.ds.fedex.com, spdatawarehouse@ground.fedex.com

}


if [ $1 -ne 0 ]
	then
		email "$2" "$1" "$3"
		echo "$2 $3"
		exit $1
fi