package com.builder.test;

import java.util.List;

public class Animal {
	
	private String species;
	private String name;
	private String age;
	private List<String> food;
	
	public Animal(String age, String species, String name, List<String> food) {
		super();
		this.species = species;
		this.name = name;
		this.age = age;
		this.food = food;
	}

	public String getSpecies() {
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
