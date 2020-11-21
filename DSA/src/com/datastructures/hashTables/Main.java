package com.datastructures.hashTables;

public class Main {

    public static void main(String[] args) {

        LinearProbing lp= new LinearProbing();

        lp.insert(10);
        lp.insert(9);
        lp.insert(23);
        lp.insert(45);
        lp.insert(48);
        lp.insert(2);
        lp.insert(3);
        lp.insert(8);

        System.out.println(lp.search(56));
        lp.display();

    }

}