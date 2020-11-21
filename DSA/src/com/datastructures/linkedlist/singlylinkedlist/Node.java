package com.datastructures.linkedlist.singlylinkedlist;

public class Node {

    private int value;
    private Node next;
    private int cost;

    public Node(int value) {
        this.value = value;
        this.next = null;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getValue() {
        return value;
    }

    public Node getNext() {
        return next;
    }

    public Node setNext(Node next) {
        this.next = next;
        return next;
    }
}
