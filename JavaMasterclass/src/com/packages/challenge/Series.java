package com.packages.challenge;

public class Series {

    static int nSum(int n) {
        int result = 0;
        for (int i = 0; i <= n; i++) {
            result += i;
        }
        return result;
    }

    static int factorial(int n) {
        int result = 1;
        for (int i = 0; i < n; i++) {
            result *= n - i;
        }
        return result;
    }

    static int fibonacci(int n) {
        if (n == 1 || n == 0)
            return n;
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

}
