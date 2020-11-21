package com.datastructures.queue;

import com.datastructures.linkedlist.singlylinkedlist.Node;

public class QueueUsingLinkedList {

    Node f;
    Node r;

    public QueueUsingLinkedList() {
        f = r = null;
    }

    public boolean isEmpty() {
        return f == null;
    }

    public void enQ(int value) {
        Node newNode = new Node(value);
        if (isEmpty()) {
            f = r = newNode;
        } else {
            r.setNext(newNode);
            r = newNode;
        }

    }

    public int deQ() {
        if (!isEmpty()) {
            int x = f.getValue();
            f = f.getNext();
            return x;
        } else {
            return -1;
        }
    }

    public void printValues() {
        if (isEmpty()) {
            System.out.println("Queue is empty");
        } else {
            Node pointer = f;
            while (pointer != r) {
                System.out.println(pointer.getValue());
                pointer = pointer.getNext();
            }
        }
    }

}
