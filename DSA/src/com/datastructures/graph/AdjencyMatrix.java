package com.datastructures.graph;

import com.datastructures.queue.QueueUsingArray;
import com.datastructures.queue.QueueUsingLinkedList;

public class AdjencyMatrix extends Graph {

    private final int[][] adjencyMatrix;
    int[] visited;


    public AdjencyMatrix(int nodes) {
        this.nodes = nodes + 1;
        this.adjencyMatrix = new int[this.nodes][this.nodes];
        this.visited = new int[this.nodes];
    }

    public void createEdge(int fromNode, int toNode) {
        if ((fromNode > nodes && fromNode <= 0) || (toNode > nodes && toNode <= 0)) {
            System.out.println("Node does not exist");
        } else {
            if (!hasEdge(fromNode, toNode)) {
                adjencyMatrix[fromNode][toNode] = 1;
                adjencyMatrix[toNode][fromNode] = 1;
            } else {
                System.out.println("Edge exists");
            }
        }
    }

    public void printMatrix() {
        for (int i = 1; i < adjencyMatrix.length; i++) {
            for (int j = 1; j < adjencyMatrix.length; j++) {
                System.out.print(adjencyMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public boolean hasEdge(int fromNode, int toNode) {
        return adjencyMatrix[fromNode][toNode] == 1 || adjencyMatrix[toNode][fromNode] == 1;
    }

    public void removeEdge(int fromNode, int toNode) {
        if ((fromNode > nodes && fromNode <= 0) || (toNode > nodes && toNode <= 0)) {
            System.out.println("Node does not exists");
        } else {
            if (hasEdge(fromNode, toNode)) {
                adjencyMatrix[fromNode][toNode] = 0;
                adjencyMatrix[toNode][fromNode] = 0;
                System.out.println("Edge between " + fromNode + " and " + toNode + " removed");
            } else {
                System.out.println("There is no edge between " + fromNode + " to " + toNode);
            }
        }
    }

    public void BFS(int start) {
        int[] visited = new int[this.nodes];
        int i = start;
        QueueUsingLinkedList ql = new QueueUsingLinkedList();
        if (i > 0 && i < this.nodes) {
            System.out.print(i + " ");
            ql.enQ(i);
            visited[i] = 1;
            while (!ql.isEmpty()) {
                int u = ql.deQ();
                for (int v = 1; v < visited.length; v++) {
                    if (adjencyMatrix[u][v] == 1 && visited[v] == 0) {
                        System.out.print(v + " ");
                        visited[v] = 1;
                        ql.enQ(v);
                    }
                }
            }
        }
    }

    public void DFS(int start) {
        DFSCall(adjencyMatrix, start);
    }

    private void DFSCall(int[][] graph, int start) {
        if (visited[start] == 0) {
            visited[start] = 1;
            for (int j = 1; j < graph.length; j++) {
                if (graph[start][j] == 1 && visited[j] == 0) {
                    DFSCall(graph, j);
                }
            }
        }
        System.out.print(start + " ");


    }
}
