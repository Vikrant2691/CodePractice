package com.datastructures.graph;

import com.datastructures.linkedlist.singlylinkedlist.SortedLinkedList;
import com.datastructures.queue.QueueUsingLinkedList;
import com.sun.deploy.panel.ITreeNode;

import java.util.Iterator;
import java.util.LinkedList;

public class AdjencyList extends Graph {

    SortedLinkedList[] adjencyList;
    int[] visited;

    AdjencyList(int nodes) {
        this.nodes = nodes + 1;
        visited = new int[this.nodes];
        adjencyList = new SortedLinkedList[this.nodes];
        for (int i = 0; i < adjencyList.length; i++) {
            adjencyList[i] = new SortedLinkedList();
        }
    }

    public void createEdge(int fromNode, int toNode) {
        if ((fromNode <= 0 || fromNode > adjencyList.length) && (toNode <= 0 || toNode > adjencyList.length)) {
            System.out.println("Enter valid node");
        } else {
            adjencyList[fromNode].addElement(toNode);
            adjencyList[toNode].addElement(fromNode);
        }
    }

    public void printList() {

        for (int i = 0; i < adjencyList.length; i++) {
            System.out.print(i + " -> ");
            adjencyList[i].printElement();
            System.out.println();
        }
    }

    public boolean hasEdge(int fromNode, int toNode) {
        return adjencyList[fromNode].isPresent(toNode) && adjencyList[toNode].isPresent(fromNode);
    }

    public void removeEdge(int fromNode, int toNode) {
        if ((fromNode <= 0 || fromNode > adjencyList.length) && (toNode <= 0 || toNode > adjencyList.length)) {
            System.out.println("Enter valid node");
        } else {
            adjencyList[fromNode].removeElement(toNode);
            adjencyList[toNode].removeElement(fromNode);
        }
    }


    public void BFS(int start) {
        QueueUsingLinkedList ql = new QueueUsingLinkedList();
        int[] visited = new int[adjencyList.length];
        if (start < adjencyList.length || start > 0) {
            visited[start] = 1;
            ql.enQ(start);
            System.out.print(start + " ");

            while (!ql.isEmpty()) {
                int u = ql.deQ();
                SortedLinkedList.ListIterator iterator = adjencyList[u].listIterator(0);
                while (iterator.hasNext()) {
                    int element = iterator.next();
                    if (visited[element] == 0) {
                        ql.enQ(element);
                        visited[element] = 1;
                        System.out.print(element + " ");
                    }
                }
            }
        }
    }

    public void DFS(int start) {
        DFSCall(adjencyList, start);
    }

    private void DFSCall(SortedLinkedList[] graph, int start) {
        SortedLinkedList.ListIterator iterator = adjencyList[start].listIterator(0);
        visited[start] = 1;
        while (iterator.hasNext()) {
            int element = iterator.next();
            if (visited[element] == 0) {
                DFSCall(graph, element);
            }
        }
        System.out.print(start + " ");
    }

}
