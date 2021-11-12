package com.code.threading;

public class ThreadExample {


    static volatile int counter = 0;

    public static void main(String[] args) {


        Thread t1 = new Thread(() -> {
            while (counter < 10) {
                incCounter();
                System.out.println(counter);

                try {
                    System.out.println(Thread.currentThread().getName());
                    Thread.sleep(100);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });

        Thread t2 = new Thread(() -> {
            while (counter < 10) {
                incCounter();
                System.out.println(counter);
                try {
                    System.out.println(Thread.currentThread().getName());
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });


        t1.setName("Thread1");
        t2.setName("Thread2");
        t1.start();
        t2.start();
    }

    public synchronized static void incCounter() {
        counter++;
    }

}
