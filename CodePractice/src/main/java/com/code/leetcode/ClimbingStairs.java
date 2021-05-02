package com.code.leetcode;

public class ClimbingStairs {


    public int climbStairs(int n) {
        int[] memo = new int[n + 1];
        return climb(0, n, memo);

    }


    public int climb(int n){

        if(n==1)
            return 1;
        else {
            int[] p = new int[n+1];
            p[1]=1;
            p[2]=2;

            for(int i=3;i<p.length;i++){
                p[i]=p[i-1]+p[i-2];
            }
            return p[n];
        }
    }

    public int climb(int i, int n, int[] memo) {

        if (i > n)
            return 0;
        else if (i == n)
            return 1;
        else if (memo[i] > 0)
            return memo[i];
        else {
            memo[i] = climb(i + 1, n, memo) + climb(i + 2, n, memo);
            return memo[i];
        }

    }

    public static void main(String[] args) {

        ClimbingStairs cs = new ClimbingStairs();
        System.out.println(cs.climb(45));


    }
}
