package com.fedex.smartpost.spark.domain

case class GSIEstimatedPayment (
  unvsl_pkg_nbr: Long,
	dest_rate_ind: String,
	count_total: Double,
	tot_est_pstl_chrg_amt: Double, 
	tot_est_ddu_pstg_amt: Double,
	tot_est_scf_pstg_amt: Double,
	tot_est_ndc_pstg_amt: Double
)