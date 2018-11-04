package com.test.cci;

import com.test.cci.QuickSort;
public class CheckPermutation {
	
	public String sort(String a) {
		char[] a_arr=a.toCharArray();
		java.util.Arrays.sort(a_arr);
		return new String(a_arr);
	}
	
	public boolean mySolution(String a,String b) {
		if(a.length()==b.length()) {
			return sort(a).equals(b);
			
		}
		else {
			return false;
		}
		
	}
	
	public static void main(String[] args) {
		CheckPermutation c= new CheckPermutation();
		System.out.println(c.mySolution("Hello", "Hello"));
		
	}

}
