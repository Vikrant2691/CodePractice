package com.test.cci;

import org.apache.derby.tools.sysinfo;

public class StringCompression {
	
	public String solution(String a) {
		StringBuffer s= new StringBuffer();
		int count=1;
		for(int i=0;i<a.length();i++) {
			
			if((i<a.length()-1)&&(a.charAt(i)==a.charAt(i+1))) {
				count++;
			}
			else {
				s.append(a.charAt(i)+""+count);
				count=1;
			}
		}
		
		
		return s.toString();
	}
	
	public static void main(String[] args) {
		
		StringCompression sc= new StringCompression();
		System.out.println(sc.solution("Hello"));
	}

}
