package com.code.project_euler;

public class EvenFibonacci {

    static int result;
    public static void main(String[] args) {

        System.out.println(fib(4));
        System.out.println(result);
    }

    static int fib(int n){
        if(n<=1)
            return 1;
        int r=fib(n-1)+fib(n-2);
        result=r%2==0?result+r:result;
        return r;
    }
}
