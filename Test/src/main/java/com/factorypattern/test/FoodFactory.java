package com.factorypattern.test;

public class FoodFactory {

	public static Food getFood(String animalName) {
		
		switch (animalName) {
		case "zebra" : return new Hay(100);
		case "rabbit" : return new Pellets(100);
			
		}
		throw new UnsupportedOperationException("Unsupported animal: "+animalName);
	}
	
}
