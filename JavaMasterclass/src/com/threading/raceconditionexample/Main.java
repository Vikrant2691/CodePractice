package com.threading.raceconditionexample;

public class Main {

    public static void main(String[] args) {

        CountdownThread t1 = new CountdownThread(new Countdown());
        t1.setName("Thread 1");
        CountdownThread t2 = new CountdownThread(new Countdown());
        t2.setName("Thread 2");

        t1.start();
        t2.start();

    }
}
