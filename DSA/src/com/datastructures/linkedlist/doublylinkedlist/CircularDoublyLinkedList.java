package com.datastructures.linkedlist.doublylinkedlist;

public class CircularDoublyLinkedList {

    private Node head;
    private int count;

    public CircularDoublyLinkedList() {
        this.head = null;
        count = 0;
    }

    public int getCount() {
        return count;
    }

    public void addElement(int value) {
        Node newNode = new Node(value);
        if (this.head == null) {
            this.head = newNode;
            this.head.setNext(newNode);
            this.head.setPrevious(newNode);
        }else {
            newNode.setNext(this.head);
            newNode.setPrevious(this.head.getPrevious());
            this.head.getPrevious().setNext(newNode);
            this.head.setPrevious(newNode);
        }

        count++;
    }

    public void addElement(int value,int position) {
        Node newNode = new Node(value);
        if(position-1>count){
            System.out.println("Position greater than number of elements");
        }
        else {
            Node pointer= this.head;
            for(int i=1;i<=position-1;i++){
                pointer=pointer.getNext();
            }
            newNode.setNext(pointer);
            newNode.setPrevious(pointer.getPrevious());
            pointer.getPrevious().setNext(newNode);
            pointer.setPrevious(newNode);
            if(position==1){
                this.head=newNode;
            }
            count++;
        }


    }

    public void removeElement(int value){
        Node pointer= this.head;

        if(pointer==null){
            System.out.println("List is empty");
        }else{
            do {
                if (pointer.getValue() != value)
                    pointer = pointer.getNext();
                else
                    break;
            }while (pointer != this.head);

            if(pointer.getValue()==value){
                if(pointer==this.head)
                    this.head=this.head.getNext();
                pointer.getPrevious().setNext(pointer.getNext());
                pointer.getNext().setPrevious(pointer.getPrevious());
            }
            else {
                System.out.println("Element not found");
            }
        }


    }

    public void printElement(){

        if(this.head==null){
            System.out.println("List is empty");
        }else {
            Node pointer= this.head;

            do{
                System.out.println(pointer.getValue());
                pointer=pointer.getNext();
            }while (pointer!=head);
        }

    }

}
