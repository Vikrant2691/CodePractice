package com.code.practice.interfaces;


public class JavaClass {

    int salary = 10;
    static String name = "Vikrant";


    public static void main(String[] args) {

        JavaClass javaClassObj = new JavaClass();

        JavaClass.InnerClass innerClassObj = javaClassObj.new InnerClass();
        System.out.println(innerClassObj.getSalary());
        System.out.println(javaClassObj.salary);
        System.out.println(JavaClass.name);

        JavaClass.InnerStaticClass innerStaticClass = new InnerStaticClass();
        System.out.println(innerClassObj.id);

    }

    class InnerClass {
//        int salary = 20;
        String id = "1001";

        int getSalary() {
            return salary;
        }

    }

    static class InnerStaticClass {

        static int salary = 100;
        String id = "1001";

        int getSalary() {
            return salary;
        }

    }


    interface InnerInterface{

        int i=0;

        int getI();

        class InnerInterfaceClass{
            int e=0;


            class InnerInnerClass{

            }
        }

        static class InnerInterfaceStaticClass{
            int e=0;


        }
    }


}
