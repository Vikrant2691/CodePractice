package com.datastructures.linkedlist.doublylinkedlist;

public class Main {
    public static void main(String[] args) {
        CircularDoublyLinkedList linkedList = new CircularDoublyLinkedList();

        linkedList.addElement(4);
        linkedList.addElement(5);
        linkedList.addElement(8);
        linkedList.addElement(1);

//
//        System.out.println("count: "+linkedList.getCount());
//        System.out.println("Elements:");
//        linkedList.printElement();

        linkedList.addElement(10,1);
//        System.out.println("");
//        linkedList.removeElement(1);
//        linkedList.removeElement(4);
//        System.out.println("Elements:");

        System.out.println(linkedList.getCount());

        linkedList.removeElement(10);
      linkedList.printElement();
//        System.out.println("count: "+linkedList.getCount());
//        System.out.println(linkedList.getCount());

    }
}
