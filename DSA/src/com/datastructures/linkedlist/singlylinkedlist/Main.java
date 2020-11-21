package com.datastructures.linkedlist.singlylinkedlist;

public class Main {
    public static void main(String[] args) {
        CircularSortedLinkedList linkedList = new CircularSortedLinkedList();

        linkedList.addElement(4);
        linkedList.addElement(5);
        linkedList.addElement(8);
        linkedList.addElement(1);


        linkedList.printElement();
        System.out.println("");
        linkedList.removeElement(1);
        linkedList.removeElement(4);
        linkedList.printElement();

    }
}
