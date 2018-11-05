#!/bin/bash

#First param index 1 is the pig script
#Second param index 2 is the file path in hadoop
#Third parm index 3 is the execution engine i.e. tez or mapreduce
#Example run would be ./pigloader.sh movement-SP44Parser.pig /data/PROD/SANDBOX/smartpost/SP44/20160624/* mapreduce
fileDate=`echo ${2} | cut -d'_' -f2`
pig -useHCatalog -x ${3} -param path=${2} -param date=$fileDate ${1}