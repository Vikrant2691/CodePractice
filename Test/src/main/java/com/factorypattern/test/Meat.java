package com.factorypattern.test;

public class Meat extends Food {


	Meat(int quantity) {
		super(quantity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void consumed() {
		// TODO Auto-generated method stub
		System.out.println("Meat consumed"+getQuantity());
	}
	
	

}
