package com.datastructures.queue;

public class Main {

    public static void main(String[] args) {
        QueueUsingArray qa = new QueueUsingArray(7);

        System.out.println(qa.isEmpty());

        qa.deQ();

        qa.enQ(2);
        qa.enQ(3);
        qa.enQ(4);
        qa.enQ(1);
        qa.enQ(5);
        qa.enQ(6);
        qa.enQ(7);

        System.out.println(qa.isEmpty());
        qa.printValues();

//        qa.deQ();
//        qa.deQ();
//        qa.deQ();
//        qa.deQ();
//        qa.deQ();
        qa.enQ(8);
        qa.printValues();

    }

}
