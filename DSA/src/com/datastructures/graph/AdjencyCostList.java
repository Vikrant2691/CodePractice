package com.datastructures.graph;

import com.datastructures.graph.SortedLinkedList;

public class AdjencyCostList extends Graph {

    SortedLinkedList[] adjencyCostList;

    AdjencyCostList(int nodes) {
        this.nodes = nodes + 1;
        adjencyCostList = new SortedLinkedList[this.nodes];
        for (int i = 0; i< adjencyCostList.length; i++) {
            adjencyCostList[i] = new SortedLinkedList();
        }
    }

    public void createEdge(int fromNode, int toNode, int cost) {
        if ((fromNode <= 0 || fromNode > adjencyCostList.length) && (toNode <= 0 || toNode > adjencyCostList.length)) {
            System.out.println("Enter valid node");
        } else {
            adjencyCostList[fromNode].addElement(toNode,cost);
            adjencyCostList[toNode].addElement(fromNode,cost);
        }
    }

    public void printList() {

        for (int i = 0; i< adjencyCostList.length; i++){
            System.out.print(i+" -> ");
            adjencyCostList[i].printElement();
            System.out.println();
        }
    }

    public boolean hasEdge(int fromNode, int toNode) {
        return adjencyCostList[fromNode].isPresent(toNode) && adjencyCostList[toNode].isPresent(fromNode);
    }

    public void removeEdge(int fromNode, int toNode) {
        if ((fromNode <= 0 || fromNode > adjencyCostList.length) && (toNode <= 0 || toNode > adjencyCostList.length)) {
            System.out.println("Enter valid node");
        } else {
            adjencyCostList[fromNode].removeElement(toNode);
            adjencyCostList[toNode].removeElement(fromNode);
        }
    }

}
