package com.code.practice.functionalprogramming.practice;

public class Hello {

    StringBuilder s= new StringBuilder();

    Runnable r = () -> {
        System.out.println(s.append("1"));
        System.out.println(s.append("2"));
    };


    @Override
    public String toString() {
        return "Hello's customer toString()";
    }

}

class InnerClass {
    public static void main(String[] args) {
        Hello h = new Hello();
        h.r.run();
    }

    @Override
    public String toString() {
        return "hi there";
    }
}


