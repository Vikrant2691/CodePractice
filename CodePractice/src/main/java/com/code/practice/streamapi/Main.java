package com.code.practice.streamapi;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

 class A{
    static int add(int i, int j) {
        return i+j;
    }
///*sdd****** */
}

public class Main extends A{


    public static void main(String[] args) {
short s=9;
        System.out.println(add(s,9));

        Student vikrant = Student.builder()
                .setId(1L)
                .setName("Vikrant")
                .setAddress(Address.builder().setCity("Thane").setStreet("Naupada").build())
                .setAge(14)
                .setMobileNumbers(Arrays.asList(MobileNumber.builder().setNumber("94757765444").build()
                        , MobileNumber.builder().setNumber("947577784674").build()))
                .setMarks(1234L)
                .build();

        Student snehal = Student.builder()
                .setId(2L)
                .setName("Snehal")
                .setAddress(Address.builder().setCity("Borivali").setStreet("Peddar Rd").build())
                .setAge(11)
                .setMobileNumbers(Arrays.asList(MobileNumber.builder().setNumber("9874765444").build()
                        , MobileNumber.builder().setNumber("947577784674").build()))
                .setMarks(3456L)
                .build();


        Student chetan = Student.builder()
                .setId(3L)
                .setName("Chetan")
                .setAddress(Address.builder().setCity("Borivali").setStreet("Cheese Block").build())
                .setAge(17)
                .setMobileNumbers(Collections.singletonList(MobileNumber.builder().setNumber("89374655342").build()))
                .setMarks(9867L)
                .build();

        Student roocha = Student.builder()
                .setId(4L)
                .setName("Roocha")
                .setAddress(Address.builder().setCity("Borivali").setStreet("Cheese Block").build())
                .setAge(17)
                .setMobileNumbers(Collections.singletonList(MobileNumber.builder().setNumber("89374655342").build()))
                .setMarks(5646L)
                .build();

        TempStudent rachel = new TempStudent(5L, "Rachel", 43, Address.builder().setCity("US").build(), List.of(MobileNumber.builder().setNumber("89374655342").build()), 5423L);

        List<Student> students = new ArrayList<>(List.of(vikrant, snehal, chetan, roocha));

        List<TempStudent> tempStudents = List.of(rachel);


//
////        Get student with exact match name
//        System.out.println(students.stream()
//                .filter(s -> s.getName()
//                        .equals("vikrant"))
//                .findFirst()
//                .get().getName());
////                .orElseThrow(NoSuchElementException::new));
//
////        Get student with matching address
//        System.out.println(students.stream()
//                .filter(student -> student.getAddress().getCity().equals("Borivali"))
//                .findFirst()
//                .get().toString());
//
//        Predicate<MobileNumber> mobileNumberEquals = m -> m.getNumber().equals("89374655342");
//        Predicate<MobileNumber> mobileNumberEqualsthis = m -> m.getNumber().equals("9874765444");
//        Predicate<MobileNumber> combinedPredicate = mobileNumberEquals.or(mobileNumberEqualsthis);
////        Get all student having mobile numbers
//        students.stream()
//                .filter(student -> student.getMobileNumbers().stream()
//                        .anyMatch(combinedPredicate))
//                .collect(Collectors.toList())
//                .forEach(System.out::println);
//
//
////        Create a List<Student> from the List<TempStudent>
//        students.addAll(
//                tempStudents.stream()
//                        .map(tempStudent -> Student.builder()
//                                .setName(tempStudent.getName())
//                                .setAddress(tempStudent.getAddress())
//                                .setMobileNumbers(tempStudent.getMobileNumbers())
//                                .setAge(tempStudent.getAge())
//                                .setMarks(tempStudent.getMarks())
//                                .setId(tempStudent.getId())
//                                .build())
//                        .collect(Collectors.toList()));
//
//        students.forEach(System.out::println);
//
////        Convert List<Student> to List<String> of student name
//
//        List<String> studentNames = students.stream()
//                .map(Student::getName)
//                .collect(Collectors.toList());

//        Change the case of List<String>

//        System.out.println(studentNames.stream()
//                //changing case
//                .map(String::toUpperCase)
//                .peek(System.out::print)
//                //sorting
//                .sorted(String::compareTo)
//                .collect(Collectors.joining(",", "[", "]")));


//        Stream<Student> studentStream = students.stream().dropWhile(student -> student.getName().equals("Vikrant"));


//        studentStream.forEach(System.out::println);

//        System.out.println(String.join(",", studentNames));


//         students.forEach(student -> System.out.println(student.getName().toUpperCase()));

//        Long[] studentIds= {1L,2L,3L};

//        students.stream().map(Student::getId).forEach(System.out::println);

//        List<Student> list = Stream.of(studentIds)
//                .map(id-> students.stream()
//                        .filter(student -> student.getId().equals(id))
//                        .collect(Collectors.toList()))
//                .flatMap(Collection::stream)
//                .collect(Collectors.toList());
////

//        list.forEach(System.out::println);

//        System.out.println(students.stream()
//                .peek(student -> System.out.println(student.getMarks()+1L))
//                .map(student -> student.getMarks())
//                .collect(Collectors.toList()));

//        Long avg =
//                students.stream()
//                        .mapToLong(Student::getMarks)
//                        .reduce(Long.MAX_VALUE,Long::min);
//        System.out.println(avg);

//        Predicate<Student> predicate = p -> p.getMarks() > 2000;
//
//        Map<Boolean, List<Student>> studentByMarks = students.parallelStream()
////                .map(Student::getMarks)
//                .peek(s->System.out.println(Thread.currentThread().getName()))
//                .collect(Collectors.partitioningBy(predicate));
//
//        for (boolean key:studentByMarks.keySet()){
//            System.out.println("key: "+key+"" +
//                    studentByMarks.get(key).toString());
//        }


//        Map<Character, List<Long>> res =
//                students.stream()
//                        .collect(Collectors.groupingBy(s -> s.getName().charAt(0),
//                                Collectors.mapping(Student::getId,Collectors.toList())));
//        for (Character key : res.keySet()) {
//            System.out.println("key: " + key + "" +
//                    res.get(key).toString());
//        }

//        students.stream()
//                .collect(Collectors.groupingBy(Student::getAge));


//        Map<String, Set<String>> mobileNumbers =
//                students.stream()
//                        .collect(
//                                Collectors.groupingBy(Student::getName,
//                                        Collectors.flatMapping(student -> student
//                                                .getMobileNumbers().stream()
//                                                .map(MobileNumber::getNumber), Collectors.toSet())));
//
//        for (String key : mobileNumbers.keySet()) {
//            System.out.println("key: " + key + "" +
//                    mobileNumbers.get(key).toString());
//        }

//        Map<String, Integer> mobileNumbers =
//                students.stream()
//                        .collect(
//                                Collectors.groupingBy(student->student.getName().charAt(0),
//                                        Collectors.reducing(Integer::sum)));


//        students.sort(Comparator.comparing(Student::getName).thenComparing(Student::getAge));
//
//        students.forEach(student -> System.out.printf("Student Name: %s | Student Age: %s \n",student.getName(),student.getAge()));
    }
}
