package com.datastructures.hashTables;

import com.datastructures.linkedlist.singlylinkedlist.SortedLinkedList;

public class Chains {

    private final SortedLinkedList[] hashTable;

    Chains(){
        this(11);
        System.out.println("Hash Table initialized to 10 by default");
    }
    Chains(int size){
        hashTable= new SortedLinkedList[size];
        for(int i=0;i<hashTable.length;i++){
            hashTable[i]=new SortedLinkedList();
        }
    }

    int hashValue(int key){
        return key%10;
    }

    public void add(int key){

        hashTable[hashValue(key)].addElement(key);
        System.out.println("Element added");
    }

    public void removeElement(int key){
        hashTable[hashValue(key)].removeElement(key);
    }



    public void display(){
        for(int i=0;i<hashTable.length;i++){
            System.out.println(i);
            hashTable[i].printElement();
            System.out.println();

        }

    }

}
