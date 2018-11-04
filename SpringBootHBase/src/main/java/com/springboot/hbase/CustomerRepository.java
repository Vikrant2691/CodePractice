package com.springboot.hbase;

import java.util.List;

import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerRepository {
	
	@Autowired
	private HbaseTemplate hbaseTemplate;

	private String tableName = "customer";

	public static byte[] CF_INFO = Bytes.toBytes("cfInfo");

	private byte[] qId = Bytes.toBytes("id");
	private byte[] qName = Bytes.toBytes("name");
	private byte[] qZipCode= Bytes.toBytes("zipcode");
	private byte[] qCity = Bytes.toBytes("city");
	private byte[] qIsInternal = Bytes.toBytes("isInternal");

	public List<Customer> findAll() {
		return hbaseTemplate.find(tableName, "cfInfo", new RowMapper<Customer>() {
			@Override
			public Customer mapRow(Result result, int rowNum) throws Exception {
				return new Customer(Bytes.toString(result.getValue(CF_INFO, qId)),
								Bytes.toString(result.getValue(CF_INFO, qName)), 
							    Bytes.toString(result.getValue(CF_INFO, qZipCode)),
							    Bytes.toString(result.getValue(CF_INFO, qCity)),
							    Bytes.toString(result.getValue(CF_INFO, qIsInternal)));
			}
		});

	}

	public Customer save(final String id,final String name,final String zipcode,final String city,final String isInternal) {
		return hbaseTemplate.execute(tableName, new TableCallback<Customer>() {
			public Customer doInTable(HTableInterface table) throws Throwable {
				Customer customer = new Customer(id, name, zipcode, city, isInternal);
				Put p = new Put(Bytes.toBytes(customer.getId()));
				p.add(CF_INFO, qId, Bytes.toBytes(customer.getName()));
				p.add(CF_INFO, qName, Bytes.toBytes(customer.getZipcode()));
				p.add(CF_INFO, qZipCode, Bytes.toBytes(customer.getCity()));
				p.add(CF_INFO, qIsInternal, Bytes.toBytes(customer.getIsInternal()));
				table.put(p);
				return customer;
				
			}
		});
}

}
