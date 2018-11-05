customers = LOAD '/user/f793344/fxsp_d_customer.txt' AS
(
cust_nbr:long,
cust_char_nbr:chararray,
custn_nm:chararray,
cust_acct_stat_flg:chararray,
cust_acct_stat_cd:chararray,
rtn_flg:chararray,
impb_cmpn_flg:chararray,
del_cnfrm_flg:chararray,
ce_se_fac_id:long,
ce_se_fac_nm:chararray,
cs_se_fac_city_nm:chararray,
ce_se_fac_st_cd:chararray,
ce_se_sub_group_id:long,
ce_se_sub_group_nm:chararray,
ce_se_sub_group_city_nm:chararray,
ce_se_sub_group_st_cd:chararray,
ce_se_group_id:long,
ce_se_group_nm:chararray,
ce_se_group_city_nm:chararray,
ce_se_group_st_cd:chararray,
ce_se_cntry_id:long,
ce_se_cntry_nm:chararray,
ce_se_div_id:long,
ce_se_div_nm:chararray,
ce_se_div_city_nm:chararray,
ce_se_div_st_cd:chararray,
ce_se_globl_enti_id:long,
ce_se_globl_enti_nm:chararray,
ce_se_globl_enti_city_nm:chararray,
ce_se_globl_enti_st_cd:chararray,
sp_cust_id:chararray,
natl_acct_nbr:long,
natl_acct_nm:chararray,
cust_group_nbr:long,
cust_group_nm :chararray,
sls_terr_nbr:chararray
);

withTime = FOREACH customers GENERATE *, (datetime) CurrentTime() as inserttimestamp;

/*
dump withTime;

*/

STORE withTime into 'hdp_smp.customer_orc' using org.apache.hive.hcatalog.pig.HCatStorer();