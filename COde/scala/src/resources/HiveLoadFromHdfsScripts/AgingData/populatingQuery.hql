INSERT OVERWRITE TABLE hdp_smp.aging_data_text
SELECT
  ce_se_globl_enti_id, 
  ce_se_globl_enti_nm, 
  shipper_acct_nbr,
  shipper_name,
  hub_id, 
  event_dt,  
  day_of_week,
  count(*),
  sum(case when (movement_upn IS NOT NULL OR fxg_upn IS NOT NULL) then 1 else 0 end) movement_cnt,
  sum(case when aau_upn is not null then 1 else 0 end) aau_cnt,
  sum(case when stc_upn is not null then 1 else 0 end) stc_cnt

FROM (
   SELECT 
     ce_se_globl_enti_id,
     ce_se_globl_enti_nm,
     order_create_orc.account_number AS shipper_acct_nbr, 
     order_create_orc.company        AS shipper_name,
     hub_id as hub_id,
     to_date(event_time_local_utc)   AS event_dt,
     from_unixtime(unix_timestamp(event_time_local_utc), 'E')  AS day_of_week,
     movement.upn as movement_upn,
     fxg.upn as fxg_upn,
     aau.upn as aau_upn,
     stc.upn as stc_upn
   
   FROM 
     hdp_smp.order_create_orc                                                                       AS  order_create_orc
     inner join hdp_smp.customer_orc on order_create_orc.account_number = customer_orc.cust_nbr
     left join (select distinct upn from hdp_smp.movement_staging)                                  AS movement on order_create_orc.upn = movement.upn
     left join (select distinct upn from hdp_smp.fxg_tracking_orc)                                  AS fxg on order_create_orc.upn = fxg.upn
     left join (select distinct upn from hdp_smp.postal_event_staging where postal_event_code='07') AS aau on order_create_orc.upn = aau.upn
     left join (select distinct upn from hdp_smp.postal_event_staging where postal_event_code='01') AS stc on order_create_orc.upn = stc.upn
   WHERE 
     order_create_orc.file_create_date > '20151120' AND 
     order_create_orc.file_create_date < '20151215' AND
     order_create_orc.product_type='DOM'
) AS custData
GROUP BY 
  ce_se_globl_enti_id, ce_se_globl_enti_nm, shipper_acct_nbr, shipper_name, hub_id, event_dt,day_of_week;