package com.test.accesmod;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;



public class TraditionalSearch {
	
	public static void main(String[] args) {
		
		List<Animal> animals = new ArrayList<Animal>();
		animals.add(new Animal("Lion", false, true, 4));
		animals.add(new Animal("Kangaroo", true, false, 2));	
		
		print(animals,new CheckerTrait());
		//print(animals, r->r.isCanHop);
		//Pattern.compile
	}
	
	public static void print(List<Animal> animals, CheckerTrait r) {
		
		for (Animal animal : animals) {
			System.out.println(r.test(animal));
			System.out.println("hhshhshd");
		}
		
	}

}

class Animal{
	String name;
	boolean canWalk;
	boolean canHop;
	int noOfLegs;
	public String getName() {
		return name;
	}
	public boolean isCanWalk() {
		return canWalk;
	}
	public boolean isCanHop() {
		return canHop;
	}
	public int getNoOfLegs() {
		return noOfLegs;
	}
	
	Animal(String name, boolean canHop, boolean canWalk, int noOfLegs){
		this.name=name;
		this.canHop=canHop;
		this.canWalk=canWalk;
		this.noOfLegs=noOfLegs;
	}
	
}

interface CheckTrait {
	
	public boolean test(Animal a);
	
} 	 


class CheckerTrait implements CheckTrait{

	public boolean test(Animal a) {
		return a.isCanHop();
	}
		
}







