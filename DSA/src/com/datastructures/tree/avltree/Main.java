package com.datastructures.tree.avltree;

public class Main {

    public static void main(String[] args) {
        AVLTree avlT= new AVLTree();

        avlT.insert(30);
        avlT.insert(20);
        avlT.insert(40);
        avlT.insert(35);
        avlT.insert(48);

        avlT.displayElements();

        avlT.deleteNode(20);
        avlT.displayElements();

        System.out.println(avlT.getRoot().getValue());

    }
}
