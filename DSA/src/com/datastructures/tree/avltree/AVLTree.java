package com.datastructures.tree.avltree;

import com.datastructures.tree.Node;

public class AVLTree {

    private Node root;

    public AVLTree() {
        this.root = null;
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

    public Node getRoot() {
        return root;
    }

    public void insert(int value) {
        rInsert(root, value);
    }

    private int balanceFactor(Node n){
        return (n.getLeft()==null?0:n.getLeft().getHeight())-(n.getRight()==null?0:n.getRight().getHeight());
    }

    private Node rInsert(Node n, int key) {

        if (n == null) {

            Node newNode=new Node(key);
            if(this.root==null){
                this.root=newNode;
                root.setHeight(0);
                System.out.println(root.getHeight());
            }
            newNode.setHeight(0);
            System.out.println(newNode.getHeight());
            n= newNode;

        }
        if (key < n.getValue()) {
            n.setLeft(rInsert(n.getLeft(), key));
        } else if (key > n.getValue()) {
            n.setRight(rInsert(n.getRight(), key));
        }

        n.setHeight(getHeightR(n));
        System.out.println("height of "+n.getValue()+"= "+n.getHeight());

        if (balanceFactor(n) == 2 && balanceFactor(n.getLeft()) == 1) {
            System.out.println("Calling ll-rotation");
            n = llTransformation(n);
        } else if (balanceFactor(n) == 2 && balanceFactor(n.getLeft()) == -1) {
            System.out.println("Calling lr-rotation");
            n = lrTransformation(n);
        } else if (balanceFactor(n) == -2 && balanceFactor(n.getRight()) == -1) {
            System.out.println("Calling rr-rotation");
            n = rrTransformation(n);
        } else if (balanceFactor(n) == -2 && balanceFactor(n.getRight()) == 1) {
            System.out.println("Calling rl-rotation");
            n = rlTransformation(n);
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

        //Update Height
        p.setHeight(getHeightR(p));

        //Balance Factor and assignment
        if (balanceFactor(p) == 2 && balanceFactor(p.getLeft()) == 1) {
            System.out.println("Calling ll-rotation");
            llTransformation(p);
        } else if (balanceFactor(p) == 2 && balanceFactor(p.getLeft()) == -1) {
            System.out.println("Calling lr-rotation");
            lrTransformation(p);
        } else if (balanceFactor(p) == 2 && balanceFactor(p.getLeft()) == 0) {
            System.out.println("Calling ll-rotation");
            llTransformation(p);
        } else if (balanceFactor(p) == -2 && balanceFactor(p.getRight()) == -1) {
            System.out.println("Calling rr-rotation");
            rrTransformation(p);
        } else if (balanceFactor(p) == -2 && balanceFactor(p.getRight()) == 1) {
            System.out.println("Calling rl-rotation");
            rlTransformation(p);
        } else if (balanceFactor(p) == -2 && balanceFactor(p.getRight()) == 0) {
            System.out.println("Calling rr-rotation");
            rrTransformation(p);
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

    private Node rlTransformation(Node n) {

        Node nr = n.getRight();
        Node nrl = nr.getLeft();
        Node nrll = nrl.getLeft();
        Node nrlr = nrl.getRight();

        nrl.setLeft(n);
        nrl.setRight(nr);
        nr.setLeft(nrlr);
        n.setRight(nrll);

        n.setHeight(getHeightR(n));
        nr.setHeight(getHeightR(nr));
        nrl.setHeight(getHeightR(nrl));

        if(this.root==n){
            this.root=nrl;
        }

        return nrl;
    }


    private Node lrTransformation(Node n) {

        Node nl = n.getLeft();
        Node nlr = nl.getRight();
        Node nlrl = nlr.getLeft();
        Node nlrr = nlr.getRight();

        nlr.setLeft(nl);
        nlr.setRight(n);
        nl.setRight(nlrl);
        n.setLeft(nlrr);

        n.setHeight(getHeightR(n));
        nl.setHeight(getHeightR(nl));
        nlr.setHeight(getHeightR(nlr));

        if(this.root==n){
            this.root=nlr;
        }

        return nlr;

    }

    private Node llTransformation(Node n) {

        //Assign pointers
        Node nl = n.getLeft();
        Node nlr = nl.getRight();

        //Rotate left
        nl.setRight(n);
        n.setLeft(nlr);

        //Recalculate Height
        nl.setHeight(getHeightR(nl));
        n.setHeight(getHeightR(n));

        // if the node to be rotated is root
        if(this.root==n){
            this.root=nl;
        }

        return nl;
    }

    private Node rrTransformation(Node n) {

        //Assign pointers
        Node nr = n.getRight();
        Node nrl=nr.getLeft();

        // Rotate right
        nr.setLeft(n);
        n.setRight(nrl);

        //Recalculate Height
        nr.setHeight(getHeightR(nr));
        n.setHeight(getHeightR(n));

        // if the node to be rotated is root
        if(this.root==n){
            this.root=nr;
        }

        return nr;
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
