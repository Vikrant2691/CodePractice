package com.datastructures.stack;

public class StackUsingArray {

    private final int size;
    private int top;
    private char[] s;

    public StackUsingArray(int size) {
        this.size = size;
        s = new char[size];
        top = -1;
    }

    public void push(char value) {
        if (this.top == size - 1) {
            System.out.println("Stack overflow");
        } else {
            s[++top] = value;
            System.out.println("Value " + value + " inserted in stack at position " + top);
        }
    }

    public char pop() {
        char value = '\0';
        if (!isEmpty()) {
            value = s[top];
            top--;
        }
        return value;
    }

    public char peek(int index) {
        char value = '\0';
        if (top - index + 1 >= 0) {
            value = s[top - index + 1];
        }
        return value;
    }

    public char stackTop() {
        return !isEmpty() ? s[top] : '\0';
    }

    public boolean isEmpty() {
        return !(top > -1);
    }

    public boolean isFull() {
        return top == size - 1;
    }

}
