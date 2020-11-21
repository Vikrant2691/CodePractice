package com.datastructures.graph;

import java.util.ArrayList;
import java.util.List;

public class test {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<Integer>();

        list.add(2);
        list.add(5);
        list.add(4);
        list.add(6);
        list.add(8);
int k=3;
        int max = -1;
        for (int i = 0; i < list.size()-(k-1); i++) {
            int minima = Integer.MAX_VALUE;
            for (int j = i; j < k + i; j++) {
                if (list.get(j) < minima) {
                    minima = list.get(j);
                }
            }
            if (max < minima) {
                max = minima;
            }
        }
        System.out.println(max);

    }

}



