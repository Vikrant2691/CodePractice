package com.test.cci;

public class URLify {
	
	public String solution(String a) {
		a.trim();
		return a.trim().replace(" ", "%20");
		
		
	}
	
	public static void main(String[] args) {
		URLify ur=new URLify();
		System.out.println(ur.solution("hi there      "));
	}
}
