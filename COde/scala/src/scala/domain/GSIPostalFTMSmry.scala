package com.fedex.smartpost.spark.domain

case class GSIPostalFTMSmry(
  PackageCount: Double,
  loc_fac_id_value_cd: String,
  loc_fac_type_cd: String,
  loc_fac_co_nm: String,
  dldate: String,
  prodtype: String,
  mailclass: String,
  mailclass_DESC: String,
  intnd_dlvr_ntwk_desc: String,
  shpr_acct_nbr: String,
  custnm: String,
  pstl_cd: String,
  ovsz_flg: String,
  blln_flg: String,
  count_total: Double,
  actl_pstl_chrg_amt: Double,
  tot_actl_pstg_amt: Double,
  dest_rate_ind: String,
  tot_est_pstl_chrg_amt: Double,
  tot_est_ddu_pstg_amt: Double,
  tot_est_scf_pstg_amt: Double,
  tot_est_ndc_pstg_amt: Double)
   