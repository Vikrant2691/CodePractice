package com.datastructures.heap;

public class Main {


    public static void main(String[] args) {
        Heap heap = new Heap(10);

        heap.insert(40);
        heap.insert(35);
        heap.insert(30);
        heap.insert(15);
        heap.insert(10);
        heap.insert(25);
        heap.insert(5);

        heap.sort();
        heap.displaySortedElements();

    }

}
