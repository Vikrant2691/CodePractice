package com.test.compares;

public class Duck implements Comparable<Duck> {
	
	int age;
	int id;
	String name;
	
	Duck(int age,int id,String name){
		
		this.age=age;
		this.id=id;
		this.name=name;
	}

	public int compareTo(Duck o) {
		// TODO Auto-generated method stub
		
		return this.id-o.id;
	}

}
