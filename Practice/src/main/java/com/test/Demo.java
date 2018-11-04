package com.test;

import org.apache.derby.tools.sysinfo;

public class Demo {
	
	static int divisibleSumPairs(int n, int k, int[] ar) {
        int count=0;
        for(int i=0;i<ar.length;i++){
            for(int j=0;j<ar.length;j++){
                if((ar[i]+ar[j] % k==0)&&(ar[i] < ar[j]))
                    count++;
            }
        }
    
        return count;

    }
	
	public static void main(String[] args) {

		StringBuilder sb=new StringBuilder("*");
		
			System.out.print(sb.append("*", 0, 1));
		
		 
	}

}
