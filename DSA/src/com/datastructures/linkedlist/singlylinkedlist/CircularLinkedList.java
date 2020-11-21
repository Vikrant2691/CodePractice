package com.datastructures.linkedlist.singlylinkedlist;

public class CircularLinkedList {

    Node head;

    public CircularLinkedList(){
        this.head=null;
    }

    public void addElement(int value){
        Node newNode= new Node(value);

        if(this.head==null){
            this.head=newNode;
            newNode.setNext(newNode);
        }
        else{
            Node pointer=this.head;
            while (pointer.getNext()!=head){
                pointer=pointer.getNext();
            }
            newNode.setNext(head);
            pointer.setNext(newNode);
        }
    }

    public void printElement(){
        Node pointer = this.head;
        do{
            System.out.println(pointer.getValue());
            pointer=pointer.getNext();
        }while (pointer!=head);
    }
}
