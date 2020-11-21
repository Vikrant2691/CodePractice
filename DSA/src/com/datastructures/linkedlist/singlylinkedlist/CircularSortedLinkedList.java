package com.datastructures.linkedlist.singlylinkedlist;

public class CircularSortedLinkedList {

    Node head;

    public CircularSortedLinkedList() {
        this.head = null;
    }

    public void addElement(int value) {
        Node newNode = new Node(value);


        if (this.head == null) {
            this.head = newNode;
            newNode.setNext(newNode);
        } else if (this.head.getValue() > newNode.getValue()) {
            Node pointer = this.head;
            while (pointer.getNext() != head) {
                pointer = pointer.getNext();
            }
            newNode.setNext(head);
            pointer.setNext(newNode);
            head = newNode;

        } else {
            Node pointer = this.head;
            while (pointer.getNext() != head) {
                if (pointer.getNext().getValue() < newNode.getValue())
                    pointer = pointer.getNext();
                else {
                    break;
                }
            }
            newNode.setNext(pointer.getNext());
            pointer.setNext(newNode);
        }

    }

    public void removeElement(int value) {
        if (this.head == null) {
            System.out.println("List is empty");
        } else if (this.head.getValue() == value) {
            Node pointer = this.head;
            while (pointer.getNext() != head) {
                pointer = pointer.getNext();
            }
            pointer.setNext(head.getNext());
            head=head.getNext();
            System.out.println("Element removed from head");
        } else {
            Node pointer = this.head;
            while (pointer.getNext() != head) {
                if (pointer.getNext().getValue() != value)
                    pointer = pointer.getNext();
                else break;
            }
            if (pointer.getNext().getValue() == value) {
                System.out.println("Element Removed:"+pointer.getNext().getValue());
                pointer.setNext(pointer.getNext().getNext());

            }
            else {
                System.out.println("Element not found");
            }
        }
    }


    public void printElement() {
        Node pointer = this.head;
        do {
            System.out.println(pointer.getValue());
            pointer = pointer.getNext();
        } while (pointer != head);
    }
}
