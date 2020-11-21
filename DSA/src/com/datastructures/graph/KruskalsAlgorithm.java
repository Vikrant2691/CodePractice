package com.datastructures.graph;

import java.util.Arrays;

public class KruskalsAlgorithm {

    private final int[][] edges;
    private final int[] set;
    private final int[] included;
    private final int[][] t;
    private int edgeCount;

    public KruskalsAlgorithm(int edges, int vertices) {
        this.edges = new int[3][edges];
        set = new int[vertices + 1];
        included = new int[edges];
        t = new int[2][vertices - 1];
        edgeCount = 0;
        Arrays.fill(set, -1);
    }

    public void union(int u, int v) {
        if (set[u] < set[v]) {
            set[u] = set[u] + set[v];
            set[v] = u;
        } else {
            set[v] = set[v] + set[v];
            set[u] = v;
        }
    }

    public int find(int u) {
        int x = u;

        while (set[x] > 0) {
            x = set[x];
        }
        return x;
    }

    public void createEdge(int start, int end, int cost) {
        edges[0][edgeCount] = start;
        edges[1][edgeCount] = end;
        edges[2][edgeCount] = cost;
        edgeCount++;
    }

    public void displayGraph() {
        for (int i = 0; i < edgeCount; i++) {
            System.out.print("start " + edges[0][i]);
            System.out.print(" end " + edges[1][i]);
            System.out.print(" cost " + edges[2][i]);
            System.out.println();
        }
    }

    public void kruskalsAlgorithm() {
        int i, k = 0, u = 0, v = 0, min, n = 7;
        i = 0;
        while (i < n - 1) {
            min = Integer.MAX_VALUE;

            for (int j = 0; j < edgeCount; j++) {
                if (included[j] == 0 && edges[2][j] < min) {
                    min = edges[2][j];
                    k = j;
                    u = edges[0][j];
                    v = edges[1][j];

                }

            }

            if (find(u) != find(v)) {
                t[0][i] = u;
                t[1][i] = v;
                union(find(u), find(v));
                i++;
            }
            included[k] = 1;
        }

        for (int l = 0; l < t.length; l++) {
            for (int j = 0; j < 6; j++) {
                System.out.print(t[l][j] + " ");
            }
            System.out.println();
        }
    }


}
