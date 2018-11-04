package com.test.cci;

import java.util.HashSet;
import java.util.Set;

public class IsUnique {
	
	public boolean mySolution(String s){
		
		
		
		Set<Character> hs=new HashSet<Character>();
		char charArray[] =s.toCharArray();
		for(char c:charArray) {
			hs.add(c);
		}
		if(hs.size()==charArray.length)
			return true;
		
		return false;
		
	}
	
	public boolean bookSolution(String s) {
		boolean[] char_set= new boolean[128];
		
		for(int i=0;i<s.length();i++) {
			int val = s.charAt(i);
			if(char_set[val])
				return false;
			char_set[val]=true;
			
		}
		return true;
		
	}
	
		
	public static void main(String[] args) {
		
		IsUnique u = new IsUnique();
		
		System.out.println(u.mySolution("HeloWrd"));
		System.out.println(u.bookSolution("HeloWrd"));
		
	}

}
