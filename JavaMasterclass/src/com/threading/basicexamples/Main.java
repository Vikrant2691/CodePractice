package com.threading.basicexamples;

import static com.threading.basicexamples.ThreadColor.*;

public class Main {
    public static void main(String[] args) {
        System.out.println(ANSI_PURPLE + "Hello from the main thread");

        AnotherThread anotherThread = new AnotherThread();
        anotherThread.setName("== Another Thread ==");
        anotherThread.start();

        Thread myRunnableThread = new Thread(new MyRunnable() {
            @Override
            public void run() {
                System.out.println(ANSI_RED +"Hello from anonymous class implementation of run");
            }
        });
        myRunnableThread.start();
        //anotherThread.interrupt();
        System.out.println(ANSI_PURPLE + "Hello again from the main thread");


        new Thread() {
            @Override
            public void run() {
                System.out.println(ANSI_GREEN + "Hello from the anonymous class thread.");
            }
        }.start();
    }

}
