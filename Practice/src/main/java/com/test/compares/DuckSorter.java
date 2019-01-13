package com.test.compares;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DuckSorter{

	public static void main(String[] args) {
		
		List<Duck> list=new ArrayList<Duck>();
		
		list.add(new Duck(12,345,"Flappy"));
		list.add(new Duck(10,323,"Flippy"));
		list.add(new Duck(1,325,"Paddles"));
		list.add(new Duck(3,456,"Quackles"));
		list.add(new Duck(8,678,"Donald"));
		
		//Collections.sort(list);
		
		
		
		Comparator<Duck> byName = new Comparator<Duck>() {

			public int compare(Duck o1, Duck o2) {
				
				return o1.name.compareTo(o2.name);
			}
		};
		
		list.sort(byName);
		
		for(Duck l:list) {
			System.out.println(l.name);
		}
		
	}

}
