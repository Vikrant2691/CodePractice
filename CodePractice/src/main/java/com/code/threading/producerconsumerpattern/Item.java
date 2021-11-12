package com.code.threading.producerconsumerpattern;

public class Item {

    private static int count=0;
    String item;

    private Item() {
    }

    public static Item createItem() {
        Item item = new Item();
        item.item = count+ " Item";
        count++;
        System.out.println(item.item+" created");
        return item;

    }
}
