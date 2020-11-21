package com.datastructures.linkedlist.doublylinkedlist;

public class Node {
    private int value;
    private Node previous;
    private Node next;

    public Node(int value){
        this.value = value;
        next=previous=null;
    }

    public Node getPrevious() {
        return previous;
    }

    public void setPrevious(Node previous) {
        this.previous = previous;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public int getValue() {
        return value;
    }
}
