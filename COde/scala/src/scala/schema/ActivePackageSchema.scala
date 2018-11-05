package com.fedex.smartpost.spark.schema

import org.apache.spark.sql.types.DoubleType
import org.apache.spark.sql.types.TimestampType
import org.apache.spark.sql.types.DateType
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.types.LongType
import org.apache.spark.sql.types.StructField
import org.apache.spark.sql.types.StructType

object ActivePackageSchema {
    val schema = StructType(Array(
        StructField("upn", LongType, true),
        StructField("event_timestamp_utc", TimestampType, true),
        StructField("event_timezone", StringType, true),
        StructField("postal_barcode", StringType, true),
        StructField("fxg_tracking_id", StringType, true),
        StructField("stop_id", StringType, true),
        StructField("stop_id_type_cd", StringType, true),
        StructField("stop_id_vintage_cd", StringType, true),
        StructField("intended_delivery_network", StringType, true),
        StructField("fxg_dest_facility", StringType, true),
        StructField("fhd_dest_facility", StringType, true),
        StructField("fxsp_dest_facility", StringType, true),
        StructField("shipper_num", StringType, true),
        StructField("transaction_flag", StringType, true),
        StructField("pkg_weight", DoubleType, true),
        StructField("oversize_flag", StringType, true),
        StructField("est_devlivery_date", DateType, true),
        StructField("est_facility_date", DateType, true),
        StructField("pickup_date", DateType, true),
        StructField("swak_facility", StringType, true),
        StructField("swak_timestamp", TimestampType, true),
        StructField("original_facility", StringType, true),
        StructField("zip_11_confidence", StringType, true),
        StructField("postal_code", StringType, true),
        StructField("latitude", DoubleType, true),
        StructField("longitude", DoubleType, true),
        StructField("geo_code_score", StringType, true),
        StructField("geo_vendor", StringType, true),
        StructField("geo_code_valid", StringType, true),
        StructField("highrise_indicator", StringType, true),
        StructField("trailer_num", StringType, true),
        StructField("pkg_length", DoubleType, true),
        StructField("pkg_width", DoubleType, true),
        StructField("pkg_height", DoubleType, true),
        StructField("pkgs_in_shipment", LongType, true),
        StructField("product_offering", StringType, true),
        StructField("filename", StringType, true),
        StructField("inserttimestamp", StringType, true)
    ))
}