package com.datastructures.linkedlist.doublylinkedlist;


public class DoublyLinkedList {

    private Node head;
    private int count;

    public DoublyLinkedList() {
        head = null;
        count = 0;
    }

    public int getCount() {
        return count;
    }

    public void addElement(int value) {
        Node newNode = new Node(value);

        if (this.head == null) {
            this.head = newNode;
        } else {
            Node pointer = this.head;
            while (pointer.getNext() != null)
                pointer = pointer.getNext();
            pointer.setNext(newNode);
            newNode.setPrevious(pointer);
        }
        this.count++;
    }

    public void addElement(int value, int position) {
        Node newNode = new Node(value);

        if (count < position) {
            System.out.println("position does not exist");
        } else if (position == 1) {
            newNode.setNext(this.head);
            this.head.setPrevious(newNode);
            this.head = newNode;
            count++;
        } else {
            Node pointer = this.head;
            for (int i = 1; i < position - 1; i++)
                pointer = pointer.getNext();
            newNode.setPrevious(pointer);
            newNode.setNext(pointer.getNext());
            if (pointer.getNext() != null) {
                pointer.getNext().setPrevious(newNode);
            }
            pointer.setNext(newNode);
            count++;
        }
    }

    public void removeElement(int value) {

        if (this.head == null)
            System.out.println("List is Empty");
        else {
            if (this.head.getValue() == value) {
                if (this.head.getNext() == null)
                    this.head = null;
                else {
                    head = head.getNext();
                    head.setPrevious(null);
                }
            } else {
                Node pointer = this.head;
                while (pointer != null) {
                    if (pointer.getValue() == value)
                        break;
                    else
                        pointer = pointer.getNext();
                }
                if (pointer == null)
                    System.out.println("Element not present in the List");
                else {
                    pointer.getPrevious().setNext(pointer.getNext());
                    if (pointer.getNext() != null)
                        pointer.getNext().setPrevious(pointer.getPrevious());
                }
            }
        }
    }

    public void reverseLinkedList(){
        if(this.head==null){
            System.out.println("List is empty");
        }
        else{
            Node pointer= this.head;
            while(pointer!=null){
                Node temp= pointer.getNext();
                pointer.setNext(pointer.getPrevious());
                pointer.setPrevious(temp);
                pointer = pointer.getPrevious();

                if (pointer!=null && pointer.getNext() == null) {
                    head=pointer;
                }
            }

        }
    }

    public void printElement() {
        Node pointer = this.head;

        while (pointer != null) {
            System.out.println(pointer.getValue());
            pointer = pointer.getNext();
        }

    }
}
