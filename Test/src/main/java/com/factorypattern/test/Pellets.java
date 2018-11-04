package com.factorypattern.test;

public class Pellets extends Food {

	Pellets(int quantity) {
		super(quantity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void consumed() {
		// TODO Auto-generated method stub
		System.out.println("Pellets consumed"+getQuantity());
	}
	
	

}
