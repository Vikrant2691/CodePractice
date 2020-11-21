package com.code.project_euler;

public class MutiplesOf3and5 {

    public static void main(String[] args) {
        int result = 0;
        int i = 1;
        while (i++ < 999) {
            if (i % 3 == 0 || i % 5 == 0) {
                result += i;
            }
        }
        System.out.println(result);
    }
}
