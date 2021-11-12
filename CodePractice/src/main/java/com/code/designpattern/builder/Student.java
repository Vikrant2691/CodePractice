package com.code.designpattern.builder;

public class Student {

    private String firstName;
    private String lastName;
    private Integer age;
    private String city;

    public Student(String firstName, String lastName, Integer age, String city) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.city = city;
    }

    public static StudentBuilder builder() {
        return new StudentBuilder();
    }


    @Override
    public String toString() {
        return "Student{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", city='" + city + '\'' +
                '}';
    }

    static class StudentBuilder {


        private String firstName;
        private String lastName;
        private Integer age;
        private String city;

        public StudentBuilder() {
        }

        public StudentBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }


        public StudentBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public StudentBuilder age(Integer age) {
            this.age = age;
            return this;
        }

        public StudentBuilder city(String city) {
            this.city = city;
            return this;
        }

        public Student build() {
            return new Student(firstName, lastName, age, city);
        }
    }
}
