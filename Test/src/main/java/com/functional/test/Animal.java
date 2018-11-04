package com.functional.test;

import java.util.List;

public class Animal {
	
	private boolean species;
	private String name;
	private String age;
	private List<String> food;
	
	public Animal(String age, boolean species, String name, List<String> food) {
		super();
		this.species = species;
		this.name = name;
		this.age = age;
		this.food = food;
	}

	public boolean getSpecies() {
		return species;
	}

	public String getName() {
		return name;
	}

	public String getAge() {
		return age;
	}

	public List<String> getFood() {
		return food;
	}
	
	
	

	
}
