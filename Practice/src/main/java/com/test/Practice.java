package com.test;

import java.text.ParseException;

import java.util.*;

import org.apache.derby.tools.sysinfo;

public class Practice{
	
	
public int binarySearch(List<Integer> list,int x) {
		
		int j= list.size();
		int i=0;
		int m=0;
		while(i<j) {
			System.out.println(i+" "+j+" "+m);
			m=(i+j)/2;
			if(x==list.get(m)) {
				return m;
			}
			if(list.get(m)<x) {
				i=m+1;
			}
			else {
				j=m;
			}
			
			
		}
			
		return 0;
		
	}

public int[] bubbleSort(int list[]) {
	int temp=0;
	for(int i=0;i<(list.length);i++) {
		for(int j=0;j<((list.length-i)-1);j++) {
			System.out.println("i="+i+"j"+j);
			if(list[j]>list[j+1]) {
				temp=list[j];
				list[j]=list[j+1];
				list[j+1]=temp;
			}
		}
		
	}
	return list;
		
}

public void pennyCounter() {
	int countq=88;
	int denomination[]= {25,10,5,1};
	
	for(int i=0;i<denomination.length;i++) {
		int res=pennyDenomCounter(countq, denomination[i]);	
	}
	
	
	int d=pennyDenomCounter(countq, 10);
	int n=pennyDenomCounter(d, 5);
	int p=pennyDenomCounter(n, 1);
	
}
public int pennyDenomCounter(int changeFor,int denom) {
	int res1=0;
	int noOfCoins=0;
	while(changeFor>=res1 && res1+denom<=changeFor) {
		res1= res1+denom;
		noOfCoins++;
	}
	System.out.println(noOfCoins);
	return changeFor-res1;
}
	

	public static void main(String[] args)  {
		
		Practice p = new Practice();
		int l[]= {5,4,3,2,1,0};
		//int res[]=p.bubbleSort(l);
		//List<Integer> list= Arrays.asList(1,2,3,4,5,6,7);
		//System.out.println(p.binarySearch(list, 6));
		//p.pennyCounter();
		String a[]= new String[l.length];
		String s="GGGGGRRRRR";
		String se= "tom jim";
		String[] s1=se.split(" ");
		String r[]=new String[10];
		for(int i=0;i<s1.length;i++) {
			if(s.contains(s1[i])) {
				if(i==s1.length-1) {
					
				}
				
			}
			else {
				
			}
		}
		
		
		System.out.println(s.contains("tom")&&s.contains("tom"));
				
	}
}