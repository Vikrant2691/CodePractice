package com.code.practice.functionalprogramming.practice;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CollectionsTest {

    public static void main(String[] args) {

        List<String> students= new ArrayList<>( List.of("Vikrant", "Roocha","Snehal"));

        Collections.sort(students, (o1, o2) -> {
            return o1.compareTo(o2);
        });

        students.forEach(System.out::println);
    }
}

