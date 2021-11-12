package com.code.designpattern.builder;

public class Main {

    public static void main(String[] args) {
        Student vikrant = Student.builder()
                                .firstName("Vikrant")
                                .lastName("Sonawane")
                                .city("Thane")
                                .build();

        System.out.println(vikrant.toString());
    }
}
