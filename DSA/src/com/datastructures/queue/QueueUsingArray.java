package com.datastructures.queue;

import javax.swing.*;

public class QueueUsingArray {

    int f;
    int r;
    int[] q;

    public QueueUsingArray(int size) {
        this.q = new int[size];
        f = r = 0;
    }

    public boolean isEmpty() {
        return f == r;
    }

    public boolean isFull() {
        return f == (r + 1) % (q.length);
    }

    public void enQ(int value) {
        if (!isFull()) {
            r = (r + 1) % (q.length);
            q[r] = value;
        } else {
            System.out.println("Queue is full");
        }
    }

    public void deQ() {
        if (!isEmpty()) {
            f = (f + 1) % (q.length);
            int x = q[f];
            q[f]=0;
            System.out.println("Value: " + x);
        } else {
            System.out.println("Queue empty");
        }
    }

    public void printValues() {
        for (int value : q) {
            System.out.println(value);
        }
    }
}
