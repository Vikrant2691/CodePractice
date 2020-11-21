package com.datastructures.graph;

import com.datastructures.graph.Node;

public class SortedLinkedList {

    private Node head;

    public SortedLinkedList() {
        head = null;
    }


    public void addElement(int data,int cost) {
        // create a new Node object to be inserted
        Node newNode = new Node(data,cost);

        // if the list is empty, set the head to the new node
        if (head == null) {
            head = newNode;
            System.out.println("element " + newNode.getValue() + " added to the front");
        }
        // if the new node is less than the current thread (1<4=>true), set it as the head
        // and the new element's next will be the earlier head
        else if (newNode.getValue() < head.getValue()) {
            newNode.setNext(head);
            head = newNode;
            System.out.println("element " + newNode.getValue() + " set as new head");
        }
        // move through the linked list until you get to the end of the list or element greater than the node
        else {
            Node after = head.getNext();
            Node before = head;
            while (after != null && after.getValue() < newNode.getValue()) {
                before = after;
                after = after.getNext();
            }
            // if we reach to the end of the linked list, the after reference will be null
            newNode.setNext(after);
            before.setNext(newNode);
            System.out.println("element added after " + before.getValue());
        }

    }

    public void concatenateLinkedList(SortedLinkedList list){

        Node pointer = this.head;

        while (pointer.getNext()!=null){
            pointer=pointer.getNext();
        }

        pointer.setNext(list.head);
        list.head = null;

    }



    public boolean isLoop(){
        Node p=this.head;
        Node q=this.head;
        if(this.head==null){
            System.out.println("List is empty");
        }
        else{
            do{
                p=p.getNext();
                q=q.getNext();
                if(q!=null)
                    q=q.getNext();

                if(q == p){
                    return true;
                }
            }while (p!=null && q!=null);
        }
        return false;
    }

    public void mergeLinkedList(SortedLinkedList list){

        Node third,first,last,second= null;

        first=this.head;
        second=list.head;

        if(first.getValue()<second.getValue()){
            third=first;
            last=first;
            first=first.getNext();
        }
        else{
            third=second;
            last=second;
            second=second.getNext();
        }

        while (first!=null && second!=null) {
            if (first.getValue() < second.getValue()) {
                last.setNext(first);
                last = first;
                first = first.getNext();
            } else {
                last.setNext(second);
                last = second;
                second = second.getNext();
            }
            last.setNext(null) ;
        }
        if(first!=null){
            last.setNext(first);
        }
        else{
            last.setNext(second);
        }
        this.head=third;
    }

    public boolean isPresent(int element){

        Node pointer= head;

        if(head==null){
            return false;
        }
        else{
         while(pointer!=null){
             if(pointer.getValue()==element){
                 return true;
             }else {
                 pointer=pointer.getNext();
             }
         }
        }
        return false;
    }

    public void removeElement(int value) {

        if (this.head == null) {
            System.out.println("list is empty");
        } else if (this.head.getValue() == value) {
            this.head = this.head.getNext();
        } else {
            Node pointer = this.head;
            while (pointer.getNext() != null && pointer.getNext().getValue() != value) {
                pointer = pointer.getNext();

            }

            if (pointer.getNext() != null) {
                System.out.println("element " + pointer.getNext().getValue() + " removed");
                pointer.setNext(pointer.getNext().getNext());
            } else {
                System.out.println("element not found");
            }

        }
    }

    public void removeDuplicates(){

        if (this.head == null) {
            System.out.println("list is empty");
        } else {
            Node after = this.head.getNext();
            Node before= this.head;
            while (after.getNext() != null) {
                if(before.getValue()!=after.getValue()){
                    before = after;
                    after=after.getNext();
                }
                else{
                    before.setNext(after.getNext());
                    after=before.getNext();
                }

            }


        }

    }

    public boolean isSorted() {
        Node pointer = this.head;

        while (pointer.getNext() != null) {
            if (pointer.getValue() > pointer.getNext().getValue())
                return false;
            pointer = pointer.getNext();
        }
        return true;
    }

    public void printElement() {
        Node pointer = this.head;
        if (pointer == null) {
            System.out.println("Linked List empty");
        }

        while (pointer != null) {
            System.out.print(pointer.getValue()+", "+pointer.getCost()+" | ");
            pointer = pointer.getNext();
        }
    }


}
