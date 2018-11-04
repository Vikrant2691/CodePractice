package com.springboot.hbase;

public class Customer {
	
	private String id;
	private String name;
	private String zipcode;
	private String city;
	private String isInternal;
	
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getZipcode() {
		return zipcode;
	}
	public String getCity() {
		return city;
	}
	public String getIsInternal() {
		return isInternal;
	}
	public Customer(String id, String name, String zipcode, String city, String isInternal) {
		super();
		this.id = id;
		this.name = name;
		this.zipcode = zipcode;
		this.city = city;
		this.isInternal = isInternal;
	}
	
		
	
	
	

}
