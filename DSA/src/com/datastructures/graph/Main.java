package com.datastructures.graph;

import com.datastructures.linkedlist.singlylinkedlist.SortedLinkedList;

public class Main {

    public static void main(String[] args) {

        KruskalsAlgorithm ka = new KruskalsAlgorithm(9, 7);

        ka.createEdge(1, 2, 25);
        ka.createEdge(1, 6, 5);
        ka.createEdge(2, 3, 12);
        ka.createEdge(2, 7, 10);
        ka.createEdge(3, 4, 8);
        ka.createEdge(4, 5, 16);
        ka.createEdge(4, 7, 14);
        ka.createEdge(5, 6, 20);
        ka.createEdge(5, 7, 18);

//        ug.printMatrix();

//            System.out.println(ka.find(7));
        ka.kruskalsAlgorithm();

//        SortedLinkedList sl= new SortedLinkedList();
//        sl.addElement(2);
//        sl.addElement(3);
//        sl.addElement(5);
//        sl.addElement(1);
//        sl.addElement(7);
//        sl.addElement(8);
//
//        SortedLinkedList.ListIterator iterator= sl.listIterator(0);
//
//        while (iterator.hasNext()){
//            System.out.println(iterator.next());
//        }

    }
}
