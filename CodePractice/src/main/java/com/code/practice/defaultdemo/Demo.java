package com.code.practice.defaultdemo;

@FunctionalInterface
interface Test1 {

    void test();

    static void test1Static() {
        System.out.println("this is static method" + Test1.class.getName());
    }

    default void defaultTest() {
        System.out.println("this is " + Test1.class.getName());
    }

}

@FunctionalInterface
interface Test2 {

    void test();

    static void test2Static() {
        System.out.println("this is static method" + Test2.class.getName());
    }

    default void defaultTest() {
        System.out.println("this is " + Test2.class.getName());
    }
}

class Test3 implements Test1 {

    Test3(Test1 target) {
target.test();
    }

    @Override
    public void test() {
        System.out.println(":h");
    }
}

public class Demo implements Test1, Test2 {


    @Override
    public void test() {
        System.out.println("This is Test1.test");
    }

    @Override
    public void defaultTest() {
        Test1.super.defaultTest();
        Test2.super.defaultTest();
    }

    public static void main(String[] args) {
//        Demo demo = new Demo();
//        Test1.test1Static();
//        demo.defaultTest();
//        Test2.test2Static();
/*


        Test1 test1 =()->{
            System.out.println("Hi there");
        };
test1.test();*/
        new Test3(() -> {
            System.out.println("This is target lambda");
        });


    }
}
