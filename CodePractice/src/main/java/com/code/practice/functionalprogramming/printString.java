package com.code.practice.functionalprogramming;

@FunctionalInterface
public interface printString {

    int printThis(int str);

    static String printThis2(String str) {
        return "yoyoyo";
    }

    default String printThis1(String str) {
        return null;
    }


}
