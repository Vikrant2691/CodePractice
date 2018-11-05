#!/bin/ksh

errorHandle() {

if [ $1 -ne 0 ]
	then
		${HADOOP_SCRIPT_LOCATION}/errorHandle.ksh "$1" "$2" "$3"
		exit $1
fi

}

purgeFile() {
	tail -4000 $1 > "${1}.tmp"
	rm "$1"
	mv "${1}.tmp" "$1"
}

echo 'File names are being set.'
# File names created for previous hour file move
spFiles[0]='SP40_*.dat.gz'
spFiles[1]='SP44_*.dat.gz'
spFiles[2]='SP46_*.dat.gz'
spFiles[3]='SPB3_*.dat.gz'
spFiles[4]='SP47_*.dat.gz'
spFiles[5]='SPB5_*.dat.gz'

hadoopFilePath[0]='ordercreate'
hadoopFilePath[1]='movement'
hadoopFilePath[2]='postalevent'
hadoopFilePath[3]='ftm'
hadoopFilePath[4]='postagepayment'
hadoopFilePath[5]='activepackage'

pigRootDirectory='/opt/fedex/spbown/pigscripts'
spPigFiles[0]="${pigRootDirectory}/orderCreate-SP40Parser.pig"
spPigFiles[1]="${pigRootDirectory}/movement-SP44Parser.pig"
spPigFiles[2]="${pigRootDirectory}/PostalEvent16_SP46_Parser.pig"
spPigFiles[3]="${pigRootDirectory}/ftm-SPB3Parser.pig"
spPigFiles[4]="${pigRootDirectory}/postagepayment-SP47Parser.pig"
spPigFiles[5]="${pigRootDirectory}/activePackage-SPB5Parser.pig"

hadoopFileRootDirectory='/data/PROD/smartpost'

${HADOOP_SCRIPT_LOCATION}/kinit.ksh
retCode=$?
errorHandle "$retCode" "kinit" " failed to run!"

#echo 'File names were created.'
let i=0
while [[ $i -lt ${#spFiles[*]} ]] ; do
        if [ -f ${spFiles[$i]} ]
		then
			echo "Uploading files to staging directory"
			
			ls -1 ${spFiles[$i]} | sort > tmp.txt	
			
			sort processedUploadStagingProdFiles.txt > tmpStaging.txt
			# Error handling
			retCode=$?
			errorHandle "$retCode" "processedUploadStagingProdFiles" " failed to determine what files to transfer due to missing file."
			
			fileNotUploaded=$(comm -23 tmp.txt tmpStaging.txt)
			rm tmpStaging.txt
			set -A stagingFilesArray $fileNotUploaded
									
			
			for j in ${stagingFilesArray[@]}
			do
				scp $j spbown\@prh23100.prod.fedex.com:/opt/fedex/eda/staging/hdp_smp/
				
				# Error handling
				retCode=$?
				errorHandle "$retCode" "$j" " failed to copy to Hadoop staging table in hadoopFileTransfer.ksh"
				
				echo $j >> processedUploadStagingProdFiles.txt
			done
			
			echo "Finished uploading files to staging directory"
			
			echo "Moving files to Hadoop"
			
			sort processedMoveProdFiles.txt > tmpUpload.txt
			# Error handling
			retCode=$?
			errorHandle "$retCode" "processedMoveProdFiles" " failed to determine what files to transfer from stating to hadoop due to missing file."
			
			fileNotMoved=$(comm -2 -3 tmp.txt tmpUpload.txt)
			rm tmpUpload.txt
			set -A movefilesArray $fileNotMoved
			
			for j in ${movefilesArray[@]}
			do
				fileDate=$(echo $j} | cut -c6-13)
				spName=$(echo $j | cut -c1-4)
				fileLoadDirectory=${hadoopFileRootDirectory}/${hadoopFilePath[i]}/${fileDate}/
				
				echo 'Moving ' $j ' to Hadoop.'
				ssh spbown\@prh23100.prod.fedex.com "hadoop fs -mkdir -p "${fileLoadDirectory}" && hadoop fs -put -f "/opt/fedex/eda/staging/hdp_smp/"${j}"  "${fileLoadDirectory} && rm /opt/fedex/eda/staging/hdp_smp/"${j}" && exit"
		
				# Error handling
				retCode=$?
				errorHandle "$retCode" "${j}" " failed to move to Hadoop directory in hadoopFileTransfer.ksh"
				
				echo $j >> processedMoveProdFiles.txt
			done
			
			echo "Finished Moving files to Hadoop"
			
			echo "Started Pig Processing to move into Hive"
			sort processedPigProdFiles.txt > tmpPig.txt
			# Error handling
			retCode=$?
			errorHandle "$retCode" "processedPigProdFiles" " failed to determine what files to run pig on due to missing file."
			
			fileNotPigged=$(comm -2 -3 tmp.txt tmpPig.txt)
			rm tmpPig.txt
			set -A pigfilesArray $fileNotPigged
			
			for j in ${pigfilesArray[@]}
			do
			  fileDate=$(echo $j} | cut -c6-13)
			  spName=$(echo $j | cut -c1-4)
			  fileLoadDirectory=${hadoopFileRootDirectory}/${hadoopFilePath[i]}/${fileDate}/
			  
			  ssh spbown\@prh23100.prod.fedex.com "${pigRootDirectory}/pigloader.sh ${spPigFiles[$i]} ${fileLoadDirectory}/$j tez"
			  
			  # Error handling
			  retCode=$?
			  errorHandle "$retCode" "$j" " failed to load data into hive via pig."
			  
			  echo $j >> processedPigProdFiles.txt
			done
			
			echo "Finished Pig Processing to move into Hive"
	
			echo ${spFiles[$i]} " uploaded at " $(date +%x_%r) >> uploadedProdFiles.txt
			
			rm tmp.txt
		fi
						
        let i+=1
done

#purge file contents
purgeFile uploadedProdFiles.txt
purgeFile processedPigProdFiles.txt
purgeFile processedUploadStagingProdFiles.txt
purgeFile processedMoveProdFiles.txt

# Text file documenting which files were uploaded sent to staging directory.
echo "Files done uploading, sending log to the staging directory."
scp uploadedProdFiles.txt spbown\@prh23100.prod.fedex.com:/opt/fedex/eda/staging/hdp_smp/
scp processedPigProdFiles.txt spbown\@prh23100.prod.fedex.com:/opt/fedex/eda/staging/hdp_smp/
scp processedUploadStagingProdFiles.txt spbown\@prh23100.prod.fedex.com:/opt/fedex/eda/staging/hdp_smp/
scp processedMoveProdFiles.txt spbown\@prh23100.prod.fedex.com:/opt/fedex/eda/staging/hdp_smp/