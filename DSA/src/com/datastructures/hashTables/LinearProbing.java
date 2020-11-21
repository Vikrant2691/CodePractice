package com.datastructures.hashTables;

public class LinearProbing {

    private final int[] hash;

    LinearProbing() {
        this(10);
        System.out.println("Initialized the Hash Table to 10 default");
    }

    LinearProbing(int size) {
        hash = new int[size];
    }

    private int hashCode(int key) {
        return key % hash.length;
    }

    private int probe(int key) {
        int index = hashCode(key);
        int i = 0;
        while ((hash[(index + i)%hash.length]) != 0) {
            i++;
        }
        return (index + i) % hash.length;
    }

    public void insert(int key) {
        int index = hashCode(key);

        if (hash[index] != 0)
            index = probe(key);
        hash[index] = key;
    }

    public int search(int key) {
        int index = hashCode(key);
        int i = 0;
        while (hash[(index + i) % key] != key) {
            i++;
        }
        return (index+i)%hash.length;
    }

    public void display(){
        for(int i=0;i<hash.length;i++){
            System.out.println(i+" "+hash[i]);
        }
    }


}
