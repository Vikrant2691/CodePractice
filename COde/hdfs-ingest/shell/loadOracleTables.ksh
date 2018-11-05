#!/bin/ksh

errorHandle() {

if [ $1 -ne 0 ]
	then
		${HADOOP_SCRIPT_LOCATION}/errorHandle.ksh "$1" "$2" "$3"
		exit $1
fi

}

tables[0]="BI_AGING"
tables[1]="BI_EPDI_NOTIF_FLAGGED"
tables[2]="BI_EPDI_NOTIF_ALERTS"
tables[3]="BI_EPDI_NOTIF_SUPP"

let i=0
while [[ $i -lt ${#tables[*]} ]] ; do
	${HADOOP_SCRIPT_LOCATION}/loadTable.ksh "${tables[$i]}"
	retCode=$?
	errorHandle "$retCode"
    
	let i+=1
done