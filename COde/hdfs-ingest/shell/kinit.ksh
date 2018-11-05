#!/bin/ksh

errorHandle() {

if [ $1 -ne 0 ]
	then
		${HADOOP_SCRIPT_LOCATION}/errorHandle.ksh "$1" "$2" "$3"
		exit $1
fi

}

ssh spbown\@prh23100.prod.fedex.com "kinit spbown -k -t spbown.keytab && exit"
# Error handling
retCode=$?
errorHandle "$retCode" " failed to run the kinit!"