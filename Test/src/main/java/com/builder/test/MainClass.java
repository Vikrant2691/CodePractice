package com.builder.test;

import java.util.Arrays;

public class MainClass {

public static void main(String[] args) {
	AnimalBuilder duckBuilder= new AnimalBuilder().setAge("12").setFood(Arrays.asList("algea","fish")).setName("Flippy").setSpecies("bird");
	Animal duck = duckBuilder.build();
	System.out.println(duck.getAge());
}
	
	
}
