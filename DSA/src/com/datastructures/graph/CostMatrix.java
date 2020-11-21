package com.datastructures.graph;

import java.util.Arrays;

public class CostMatrix extends Graph {

    private final int[][] costMatrix;

    public CostMatrix(int nodes) {
        this.nodes = nodes + 1;
        this.costMatrix = new int[this.nodes][this.nodes];
        for (int[] i : costMatrix)
            Arrays.fill(i, Integer.MAX_VALUE);
    }

    public void createEdge(int fromNode, int toNode2, int cost) {
        if ((fromNode > nodes && fromNode <= 0) || (toNode2 > nodes && toNode2 <= 0)) {
            System.out.println("Node does not exist");
        } else {
            if (!hasEdge(fromNode, toNode2)) {
                costMatrix[fromNode][toNode2] = cost;
                costMatrix[toNode2][fromNode] = cost;
            } else {
                System.out.println("Edge exists");
            }
        }
    }

    public void printMatrix() {
        for (int i = 1; i < costMatrix.length; i++) {
            for (int j = 1; j < costMatrix.length; j++) {
                System.out.print(costMatrix[i][j] == Integer.MAX_VALUE ? " - " : costMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public boolean hasEdge(int fromNode, int toNode) {
        return costMatrix[fromNode][toNode] != Integer.MAX_VALUE || costMatrix[toNode][fromNode] != Integer.MAX_VALUE;
    }

    public void removeEdge(int fromNode, int toNode) {
        if ((fromNode > nodes && fromNode <= 0) || (toNode > nodes && toNode <= 0)) {
            System.out.println("Node does not exists");
        } else {
            if (hasEdge(fromNode, toNode)) {
                costMatrix[fromNode][toNode] = 0;
                costMatrix[toNode][fromNode] = 0;
                System.out.println("Edge between " + fromNode + " and " + toNode + " removed");
            } else {
                System.out.println("There is no edge between " + fromNode + " to " + toNode);
            }
        }
    }

    public void primsAlgorithm() {
        int min = Integer.MAX_VALUE, u = 0, v = 0, k = 0;
        int[] near = new int[costMatrix.length];
        Arrays.fill(near, Integer.MAX_VALUE);
        int[][] t = new int[2][costMatrix.length - 1];

        for (int i = 1; i < costMatrix.length; i++) {
            for (int j = i; j < costMatrix.length; j++) {
                if (costMatrix[i][j] < min) {
                    min = costMatrix[i][j];
                    u = i;
                    v = j;
                }
            }
        }
        t[0][0] = u;
        t[1][0] = v;
        near[u] = near[v] = 0;

        for (int i = 1; i < costMatrix.length; i++) {
            if (near[i] != 0) {
                if (costMatrix[i][u] < costMatrix[i][v])
                    near[i] = u;
                else
                    near[i] = v;
            }
        }

        for (int i = 1; i < costMatrix.length-1; i++) {
            min = Integer.MAX_VALUE;

            for (int j = 1; j < near.length; j++) {
                if (near[j] != 0 && costMatrix[j][near[j]] < min) {
                    min = costMatrix[j][near[j]];
                    k = j;
                }
            }
            t[0][i] = near[k];
            t[1][i] = k;
            near[k] = 0;

            for (int j = 1; j < near.length; j++) {
                if (near[j] != 0 && (costMatrix[k][j] < costMatrix[j][near[j]])) {
                    near[j]=k;
                }
            }

        }

        for(int i=0;i<t.length;i++){
            for(int j=0;j<6;j++){
                System.out.print(t[i][j]+" ");
            }
            System.out.println();
        }

    }

}
