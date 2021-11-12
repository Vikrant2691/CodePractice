package com.code.practice.streamapi;

import java.util.Comparator;
import java.util.List;


class TempStudent {

    private final Long Id;
    private final String name;
    private final int age;
    private final Address address;
    private final List<MobileNumber> mobileNumbers;
    private final Long marks;

    public TempStudent(Long id, String name, int age, Address address, List<MobileNumber> mobileNumbers, Long marks) {
        Id = id;
        this.name = name;
        this.age = age;
        this.address = address;
        this.mobileNumbers = mobileNumbers;
        this.marks = marks;
    }

    public Long getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Address getAddress() {
        return address;
    }

    public List<MobileNumber> getMobileNumbers() {
        return mobileNumbers;
    }

    public Long getMarks() {
        return marks;
    }
}


public class Student {


    private Long id;
    private String name;
    private int age;
    private Address address;
    private List<MobileNumber> mobileNumbers;
    private Long marks;

    private Student() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Address getAddress() {
        return address;
    }

    public List<MobileNumber> getMobileNumbers() {
        return mobileNumbers;
    }

    public Long getMarks() {
        return marks;
    }


    public static int getByAge(Student s1, Student s2) {
        return s1.getAge() - s2.getAge();
    }

    private Student(Long id, String name, int age, Address address, List<MobileNumber> mobileNumbers, Long marks) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
        this.mobileNumbers = mobileNumbers;
        this.marks = marks;
    }


    static StudentBuilder builder() {
        return new StudentBuilder();
    }


    static class StudentBuilder {

        private Long id;
        private String name;
        private int age;
        private Address address;
        private List<MobileNumber> mobileNumbers;
        private Long marks;

        public StudentBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public StudentBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public StudentBuilder setMarks(Long marks) {
            this.marks = marks;
            return this;
        }

        public StudentBuilder setAge(int age) {
            this.age = age;
            return this;
        }

        public StudentBuilder setAddress(Address address) {
            this.address = address;
            return this;
        }

        public StudentBuilder setMobileNumbers(List<MobileNumber> mobileNumbers) {
            this.mobileNumbers = mobileNumbers;
            return this;
        }

        public Student build() {
            return new Student(id, name, age, address, mobileNumbers, marks);
        }
    }

    @Override
    public String toString() {
        return "Student{" +
                "Id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address=" + address +
                ", mobileNumbers=" + mobileNumbers +
                ", marks=" + marks +
                '}';
    }
}
