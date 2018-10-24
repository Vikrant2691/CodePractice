package com.builder.test;

import java.util.List;

public class AnimalBuilder {
	
	private String species;
	private String name;
	private String age;
	private List<String> food;
	
	
	public AnimalBuilder setSpecies(String species) {
		this.species = species;
		return this;
	}
	public AnimalBuilder setName(String name) {
		this.name = name;
		return this;
	}
	public AnimalBuilder setAge(String age) {
		this.age = age;
		return this;
	}
	public AnimalBuilder setFood(List<String> food) {
		this.food = food;
		return this;
	}
	
	public Animal build() {
		return new Animal(age,species,name,food);
	}
	
	

}
