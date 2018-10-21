package com.test.accesmod;

import com.test.maven.*;
public class test extends HelloWorld {
	
	String s="Hello";
	
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof HelloWorld) && s==obj;		
	}
	
	public static void main(String[] args) {
	 HelloWorld	h = new HelloWorld();
	 HelloWorld	h1 =null;
	 
	 System.out.println(h1.b);
	 
	 
	 
	 
	}
	

}
