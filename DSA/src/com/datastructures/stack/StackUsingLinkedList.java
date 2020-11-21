package com.datastructures.stack;

import com.datastructures.linkedlist.singlylinkedlist.Node;

public class StackUsingLinkedList {

    private Node top;

    public StackUsingLinkedList() {
        top = null;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public void push(int value) {
        Node newNode = new Node(value);
        newNode.setNext(top);
        top = newNode;
    }

    public int pop() {
        if (!isEmpty()) {
            Node node = top;
            top = top.getNext();
            return node.getValue();

        } else {
            return -1;
        }
    }

    public void peek(int position) {
        Node pointer = top;
        for (int i = 1; pointer != null && i <= position; i++) {
            pointer = pointer.getNext();
        }
        if (pointer == null)
            System.out.println("element not found");
        else
            System.out.println("value: " + pointer.getValue());
    }

    public void stackTop() {
        if (!isEmpty())
            System.out.println("top value:" + top.getValue());
        else
            System.out.println("stack empty");
    }
}
