package com.datastructures.tree.binarytree;

import com.datastructures.tree.Node;

import java.util.LinkedList;
import java.util.Scanner;

public class BinaryTree {

    private Node root;
    private final LinkedList<Node> q;

    BinaryTree() {
        root = null;
        q = new LinkedList<>();
    }

    void create() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a root value");
        int input = scanner.nextInt();
        this.root = new Node(input);
        this.root.setLeft(null);
        this.root.setRight(null);
        q.add(this.root);

        while (!q.isEmpty()) {
            Node p = q.pollFirst();
            System.out.println("Enter left child for "+p.getValue()+": ");
            input = scanner.nextInt();
            if (input != -1) {
                Node temp = new Node(input);
                temp.setLeft(null);
                temp.setRight(null);
                p.setLeft(temp);
                q.add(temp);
            }
            System.out.println("Enter right child for "+p.getValue()+": ");
            input = scanner.nextInt();
            if (input != -1) {
                Node temp = new Node(input);
                temp.setLeft(null);
                temp.setRight(null);
                p.setRight(temp);
                q.add(temp);
            }
            System.out.println("Q elements");
            for (Node i : q) {
                System.out.println(i + "=>" + i.getValue());
            }
        }

    }



    public void displayElements() {

        Node pointer = this.root;

        if (pointer == null) System.out.println("Tree empty");
        else {
            preOrder(root);
        }

    }

    public void preOrder(Node n) {
        if (n != null) {
            System.out.print(n.getValue()+" ");
            preOrder(n.getLeft());
            preOrder(n.getRight());
        }
    }

    public void inOrder(Node n) {
        if (n != null) {
            preOrder(n.getLeft());
            System.out.print(n.getValue()+" ");
            preOrder(n.getRight());
        }
    }

    public void postOrder(Node n) {
        if (n != null) {
            postOrder(n.getLeft());
            postOrder(n.getRight());
            System.out.print(n.getValue()+" ");
        }
    }

    public int getCount() {
        return countNodes(this.root);
    }

    public int getHeight() {
        return getHeightR(this.root);
    }

    public int getCountOfLeafNodes() {
        return getCountLeafNodesR(this.root);
    }

    public int countNodes(Node n){
        if(n!=null){
            int x,y=0;
            x=countNodes(n.getLeft());
            y=countNodes(n.getRight());
            return x+y+1;
        }
        else
            return 0;
    }

    public int getCountLeafNodesR(Node n) {
        if (n != null) {
            int x, y = 0;
            x = getCountLeafNodesR(n.getLeft());
            y = getCountLeafNodesR(n.getRight());
            if (n.getLeft() == null && n.getRight() == null)
                return x + y + 1;
            else
                return x + y;
        } else
            return 0;
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

}
