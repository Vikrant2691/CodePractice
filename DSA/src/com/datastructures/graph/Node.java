package com.datastructures.graph;

public class Node {

    private int value;
    private Node next;
    private int cost;

    public Node(int value,int cost) {
        this.value = value;
        this.next = null;
        this.cost=cost;
    }

    public int getCost() {
        return cost;
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
