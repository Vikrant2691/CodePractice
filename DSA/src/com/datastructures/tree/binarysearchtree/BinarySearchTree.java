package com.datastructures.tree.binarysearchtree;

import com.datastructures.tree.Node;

import javax.naming.NoPermissionException;
import java.util.logging.SocketHandler;

public class BinarySearchTree {
    private Node root;

    public BinarySearchTree() {
        this.root = null;
    }

    public void lInsert(int value) {
        Node newNode = new Node(value);
        if (root == null) {
            root = newNode;
            System.out.println("Root node added");
        } else {
            Node r = null;
            Node t = root;
            while (t != null) {
                r = t;
                if (value == t.getValue()) {
                    System.out.println("Values equal, not added");
                    return;
                } else if (value < t.getValue()) {
                    t = t.getLeft();
                    //System.out.println(value + " is less than " + t.getValue() + " so going left");
                } else {
                    t = t.getRight();
                    //System.out.println(value + " is greater than " + t.getValue() + " so going right");
                }
            }
            if (newNode.getValue() < r.getValue()) {
                r.setLeft(newNode);
                //System.out.println(value + " is less than " + t.getValue() + " so adding to left");
            } else {
                r.setRight(newNode);
                //System.out.println(value + " is greater than " + t.getValue() + " so adding to right");
            }
        }
    }

    public void search(int key){
        Node value=null;
        if(root==null){
            System.out.println("BST Empty");
        }
        else {
            value=RSearch(root,key);
        }

        if(value == null)
            System.out.println("Value not found");
        else
            System.out.println("Value " + value.getValue() + " found");
    }

    private Node RSearch(Node n, int key){
        if(n==null){
            return  null;
        }
        if(n.getValue()==key){
            return n;
        }
        else if(key<n.getValue()){
            return RSearch(n.getLeft(),key);
        }
        else {
            return RSearch(n.getRight(),key);
        }

    }

    public void insert(int value){
        if(root==null){
            root= new Node(value);
        }
        else {
            rInsert(root,value);
        }
    }

    private Node rInsert(Node n, int key) {

        if (n == null) {
            return new Node(key);
        }
        if (key < n.getValue()) {
            n.setLeft(rInsert(n.getLeft(), key));
        } else if (key > n.getValue()) {
           n.setRight(rInsert(n.getRight(), key));
        }
        return n;

    }

    public void deleteNode(int value) {
        if (root == null) {
            System.out.println("Tree empty");
        } else {
            deleteNode(root, value);
        }

    }

    private Node deleteNode(Node p, int value) {
        Node q = null;

        if(p==null){
            return null;
        }
        if(p.getLeft()==null && p.getRight()==null){
            if(p==root){
                root=null;
            }
            p=null;
            return null;
        }

        if (value < p.getValue()) {
            p.setLeft(deleteNode(p.getLeft(), value));
        } else if (value > p.getValue()) {
            p.setRight(deleteNode(p.getRight(), value));
        } else {
            if (getHeightR(p.getRight()) < getHeightR(p.getLeft())) {
                q = inPre(p.getLeft());
                p.setValue(q.getValue());
                p.setLeft(deleteNode(p.getLeft(), q.getValue()));
            } else {
                q = inSucc(p.getRight());
                p.setValue(q.getValue());
                p.setRight(deleteNode(p.getRight(), q.getValue()));
            }

        }
        return p;
    }

    public Node inPre(Node p){
        while (p!=null && p.getRight()!=null){
            p=p.getRight();
        }
        return p;
    }

    public Node inSucc(Node p){
        while (p!=null && p.getLeft()!=null){
            p=p.getLeft();
        }
        return p;
    }

    public void getHeight(){
        System.out.println(getHeightR(this.root));
    }

    public int getHeightR(Node n) {
        int x, y = 0;
        if (n == null) {
            return 0;
        }
        x = getHeightR(n.getLeft());
        y = getHeightR(n.getRight());
        if (x > y) {
            return x + 1;
        } else {
            return y + 1;
        }
    }

    public void displayElements() {

        Node pointer = this.root;

        if (pointer == null) System.out.println("Tree empty");
        else {
            inOrder(root);
            System.out.println();
        }

    }

    public void inOrder(Node n) {
        if (n != null) {
            inOrder(n.getLeft());
            System.out.print(n.getValue()+" ");
            inOrder(n.getRight());
        }
    }
}
