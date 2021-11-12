package com.code.practice;

import java.util.Arrays;

public class demo {


    public static void main(String[] args) {
        String s = "Welcome to hackerrank";


        System.out.println(demo.ucToLc(s));


    }

    private static String ucToLc(String s) {

        StringBuilder sb1 = new StringBuilder();

        char[] s2 = s.toCharArray();


        for (int i = 0; i < s2.length; i++) {
            if (s2[i] >= 'a' && s2[i] >= 'z')
                s2[i] = (char) ((int) s2[i] + 32);
            if (s2[i] >= 'A' && s2[i] >= 'Z')
                s2[i] = (char) ((int) s2[i] - 32);
            sb1.append(s2[i]);

        }

        return sb1.toString();
    }
}
