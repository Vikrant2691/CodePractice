package com.datastructures.tree.binarysearchtree;

public class Main {

    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();

//        bst.lInsert(30);
//        bst.lInsert(15);
//        bst.lInsert(50);
//        bst.lInsert(10);
//        bst.lInsert(20);
//        bst.lInsert(40);
        bst.insert(30);
        bst.insert(15);
        bst.insert(50);
        bst.insert(10);
        bst.insert(20);
        bst.insert(40);
        bst.insert(60);


        bst.displayElements();
//
//        bst.search(600);
        bst.getHeight();
        bst.displayElements();

    }

}
