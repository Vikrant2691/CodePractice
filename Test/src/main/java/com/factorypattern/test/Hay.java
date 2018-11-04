package com.factorypattern.test;

public class Hay extends Food {

	Hay(int quantity) {
		super(quantity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void consumed() {
		// TODO Auto-generated method stub
		System.out.println("Hay consumed"+getQuantity());
	}
	
	

}
