package com.code.practice.functionalprogramming;

import java.util.ArrayList;
import java.util.Arrays;

public class Test {
    String s2 = "Text2";


    void print() {
        s2 = "Text1";
        s2 = "Text3";
        printStringImpl printString = new printStringImpl();

        printString.s = 2;
        printString.s = 3;


        printString printStringObj = str -> printString.s;
        ToUppercase toUppercase = String::toUpperCase;


        System.out.println(printStringObj.printThis(1));
    }


    public static void main(String[] args) {

        Test t = new Test();

        t.print();


    }

}


class printStringImpl {

    int s;

}

interface ToUppercase {
    String change(String s);
}