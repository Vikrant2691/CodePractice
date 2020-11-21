package com.datastructures.stack;

public class Main {

    public static void main(String[] args) {

//        StackUsingLinkedList s = new StackUsingLinkedList();
//
//        s.push(10);
//        s.push(20);
//        s.push(30);
//        //System.out.println(s.isFull());
//        System.out.println(s.pop());
//        System.out.println(s.pop());
//
//        System.out.println(s.isEmpty());
//        s.push(100);
//        System.out.println(s.pop());
//        System.out.println(s.pop());
//        System.out.println(s.pop());
//        s.stackTop();



////
        InfixToPostfix itp= new InfixToPostfix();

        int x='*';
        int y='-';

//        System.out.println(itp.priority((char) x)<itp.priority('-'));
        System.out.println(itp.convert("a+b*c-d/e"));

    }

}
