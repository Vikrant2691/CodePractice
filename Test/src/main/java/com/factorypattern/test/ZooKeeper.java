package com.factorypattern.test;

public class ZooKeeper {
	
	public String name;
	public static void main(String[] args) {
		Food f=  FoodFactory.getFood("zebra");
		f.consumed();
		
		
	}

}
