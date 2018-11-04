package com.factorypattern.test;

public abstract class Food {
	
	private int quantity;
	
	Food(int quantity){
		this.quantity=quantity;
	}

	public int getQuantity() {
		return quantity;
	}

	public abstract void consumed();
	
}
