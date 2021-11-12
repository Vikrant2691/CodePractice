package com.code.practice.comparisons;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Runner {

    public static void main(String[] args) {

        int i=15;

        BigInteger b =new BigInteger(String.valueOf(i));
        b.isProbablePrime(1);
        List<Student> students = new ArrayList<>();

        students.add(new Student("Vikrant", 29, "Thane"));
        students.add(new Student("Snehal", 26, "Borivali"));
        students.add(new Student("Roocha", 23, "Mumbai"));

        Comparator<Student> comparatorByAge = (o1, o2) -> o1.getCity().compareTo(o2.getCity());

        students.sort(Comparator.comparing(Student::getCity));

        students.stream().map(Student::getCity)
                .map(String::toUpperCase)
                .forEach(System.out::println);
//        for(Student s : students){
//            System.out.println(s.toString());
//        }
    }

}
