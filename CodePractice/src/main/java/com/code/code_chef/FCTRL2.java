package com.code.code_chef;

import java.util.Scanner;

public class FCTRL2 {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        Integer t=in.nextInt();
        Integer r[] = new Integer[t];



        FCTRL2 f = new FCTRL2();
        System.out.println(f.factorial(5));
    }

    int factorial(int n) {
        if (n == 1) {
            return 1;
        } else {
            return n * factorial(n - 1);
        }
    }

}
