/*
 * File: LoadPostalErrorsExternalTable.pig
 * 
 * 
 * The following is an example of the multiple types of rows that the file will contain: 
 *
 * 128212375,46518949,20151004,135902,16845,20151004,11,0,11,5,5
 * W,1,,128212375,THE MAILER ID IN THE EFN IS NOT A CONFORMING MAILER ID
 * W,6,9261299992247623720614,,DEST DPV CONFIRMATION: ADDRESS WAS NOT SUBMITTED FOR CONFIRMATION
 * W,6,9261299992247623720614,A1,DPV NON COMPLIANCE: ZIP+4 NOT MATCHED
 * W,8,9261299994603120191056,A1,DPV NON COMPLIANCE: ZIP+4 NOT MATCHED
 * W,8,9261299994603120191056,,DEST DPV CONFIRMATION: ADDRESS WAS NOT SUBMITTED FOR CONFIRMATION
 * W,8,9261299994603120191056,LW,INVALID CLASS OF MAIL LW; DEFAULT TO PS
 * W,8,9261299994603120191056,PS-DF-D-3-00-1,INVALID COMBO: CLASS PS RATE DF DRI D PC 3 ZONE 00 RBI 1
 * W,8,9261299994603120191056,DF,INVALID RATE INDICATOR DF FOR PARCEL SELECT LIGHTWEIGHT
 * 128212375,46519854,20151004,135902,44883,20151004,63,0,63,31,31,
 * W,1,,128212375,THE MAILER ID IN THE EFN IS NOT A CONFORMING MAILER ID
 * W,14,9261292701035711030667,,DEST DPV CONFIRMATION: ADDRESS WAS NOT SUBMITTED FOR CONFIRMATION
 * W,14,9261292701035711030667,A1,DPV NON COMPLIANCE: ZIP+4 NOT MATCHED
 * W,24,9261297641774052082150,LW,INVALID CLASS OF MAIL LW; DEFAULT TO PS 
 */


/*
 * There are two line types.  We will check to see if the first column is "E" or W" and throw the other lines out.
 */
raw_file = LOAD '<hdfs location of postal error file>' using PigStorage(',') AS (error_type:chararray, line_number:chararray, postal_bar_code:chararray, field_in_error:chararray, message:chararray); 
pack
/*
 *  Toss out any lines that do not start with "E" or "W" because they are the mailer id lines.
 */
no_header_records = FILTER raw_file BY (error_type == 'E') OR (error_type == 'W');

/*
 * We still need to get out records that do not have package ids like:
 * W,1,,128212375,THE MAILER ID IN THE EFN IS NOT A CONFORMING MAILER ID
 */

pkg_errors_and_warnings = FILTER no_header_records postal_bar_code not empty string

store pkg_errors_and_warnings into ‘<fxsp schema>.Postal_Errors_Staging' 
USING org.apache.hive.hcatalog.pig.HCatStorer(‘usps_file_date=<partition to load>');