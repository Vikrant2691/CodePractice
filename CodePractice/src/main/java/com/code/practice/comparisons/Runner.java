package com.code.practice.comparisons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Runner {

    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();

        students.add(new Student("Vikrant", 29, "Thane"));
        students.add(new Student("Snehal", 26, "Borivali"));
        students.add(new Student("Roocha", 23, "Mumbai"));

        Comparator<Student> comparatorByAge = new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o1.getCity().compareTo(o2.getCity());
            }
        };

        students.sort(Comparator.comparing(Student::getCity));

        students.stream().map(Student::getCity)
                .map(String::toUpperCase)
                .forEach(System.out::println);
//        for(Student s : students){
//            System.out.println(s.toString());
//        }
    }

}
