package com.fedex.smartpost.spark.domain

import java.sql.Timestamp

case class GSIAnticipatedPath (
  shpr_acct_nbr: Long,
  event_date_dt: Timestamp,
  intnd_dlvr_ntwk_desc: String,
  office_wsc_pkg_count: Double,
  fxg_pkg_count: Double,
  fhd_pkg_count: Double,
  fxsp_pkg_count: Double,
  usps_pkg_count: Double,
  file_create_date: String
)
