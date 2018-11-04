package com.test.cci;

import org.apache.derby.tools.sysinfo;

public class OneAway {
	
	public String sortArray(String a) {
		char[] a_arr=a.toCharArray();
		java.util.Arrays.sort(a_arr);
		return a_arr.toString();
	}
	
	public boolean solution(String a,String b) {
		char[] a_arr=a.toCharArray();
		char[] b_arr=b.toCharArray();
		int i=0;
		int count=0;
		int j=0;
		while(i<a.length()&&j<b.length()) {
			if(a_arr[i]!=b_arr[j] &&count==0) {
				
				if(a.length()>b.length()) {
					i++;
					count++;
				}
				else if (a.length()<b.length()) {
					
					j++;
					count++;
				}
				else {
					count++;
					i++;j++;
					
				}
			}
			else if(a_arr[i]==b_arr[j]){
				i++;j++;
				
			}
			else {
				return false;
			}
			
		}
		return true;
		
	}
	
	public static void main(String[] args) {
		OneAway oa= new OneAway();
		System.out.println(oa.solution("hello", "herlo"));
	}
}
