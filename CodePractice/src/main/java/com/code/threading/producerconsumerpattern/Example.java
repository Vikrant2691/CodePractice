package com.code.threading.producerconsumerpattern;

import java.util.concurrent.ArrayBlockingQueue;

public class Example {


    static void process(Item i) throws InterruptedException {
        System.out.println(i.item+" consumed");
        Thread.sleep(1000);
    }

    public static void main(String[] args) {
        ArrayBlockingQueue<Item> items = new ArrayBlockingQueue<>(10);



        Runnable producer = () -> {
            try {
                while (true) {
                    items.put(Item.createItem());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Runnable consumer = () -> {
            try {
                while (true){
                    Item i = items.take();

                    process(i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };


        new Thread(producer).start();
//        new Thread(producer).start();

        new Thread(consumer).start();
        new Thread(consumer).start();

    }

}
