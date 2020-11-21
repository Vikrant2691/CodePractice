package com.datastructures.linkedlist.singlylinkedlist;

public class LinkedList {

    private Node head;
    private Node last;

    public LinkedList() {
        this.head = null;
        this.last = null;
    }

    public LinkedList(Node head) {
        this.head = head;
        this.last = head;
    }

    public void addElement(int data) {
        Node node = new Node(data);
        if (head == null) {
            head = node;
            last = node;
            System.out.println("Head set");
            return;
        }
        Node pointer = head;
        while (pointer.getNext() != null) {
            pointer = pointer.getNext();
        }
        last = pointer.setNext(node);

        System.out.println("Element added");
    }

    public void callRec() {
        printElementRecursive(this.head);

    }

    public int getElement() {
        int value;
        if (head == null) {
            return  -1;
        } else {
            value = head.getValue();
            head = head.getNext();
            return value;
        }

    }

    public void printElementRecursive(Node node) {

        if (node != null) {
            System.out.println(node.getValue());
            printElementRecursive(node.getNext());

        }
    }

    public Node search(int searchValue) {
        Node node = this.head;
        Node previousNode = null;
        while (node != null) {

            if (node.getValue() == searchValue) {

                if (previousNode != null) {
                    previousNode.setNext(node.getNext());
                    node.setNext(this.head);
                    this.head = node;


                }
                return head;
            }
            previousNode = node;
            node = node.getNext();


        }
        return null;
    }


    public Integer count() {
        Node node = this.head;
        Integer count = 0;
        if (node == null) {
            return count;
        } else {
            while (node != null) {
                count++;
                node = node.getNext();
            }
        }
        return count;
    }


    public Integer callCountRec() {
        return countRecursive(this.head);
    }

    public Integer countRecursive(Node node) {

        if (node == null)
            return 0;
        else {
            return countRecursive(node.getNext()) + 1;
        }

    }

    public Integer sumCountRec() {
        return sumRecursive(this.head);
    }

    public Integer sumRecursive(Node node) {

        if (node == null)
            return 0;
        else {
            return sumRecursive(node.getNext()) + node.getValue();
        }
    }


    public Integer sum() {
        Integer sum = 0;
        Node node = this.head;
        while (node != null) {
            sum += node.getValue();
            node = node.getNext();
        }
        return sum;
    }

    public void callRevRec() {
        reverseLinkedListByReversingLinksRec(null, this.head);
    }

    public void reverseLinkedListByReversingLinksRec(Node previous, Node current) {

        if (current != null) {
            reverseLinkedListByReversingLinksRec(current, current.getNext());
            current.setNext(previous);
        } else {
            this.head = previous;
        }

    }

    public void reverseLinkedListByReversingLinks() {
        Node p = this.head;
        Node q = null;
        Node r;

        while (p != null) {
            r = q;
            q = p;
            p = p.getNext();
            q.setNext(r);
        }
        this.head = q;
    }


    public Integer max() {
        Integer max = 0;
        Node node = this.head;
        while (node != null) {
            if (max < node.getValue()) {
                max = node.getValue();
            }
            node = node.getNext();
        }
        return max;
    }


    public void printElement() {
        Node pointer = this.head;
        if (pointer == null) {
            System.out.println("Linked List empty");
        }

        while (pointer != null) {
            System.out.println(pointer.getValue());
            pointer = pointer.getNext();
        }
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void peek() {
        if (!isEmpty())
            System.out.println("top value:" + head.getValue());
        else
            System.out.println("stack empty");

    }
}
