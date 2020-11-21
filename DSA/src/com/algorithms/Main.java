package com.algorithms;


public class Main {

    public static void main(String[] args) {
        Sort sort = new Sort();


        int[] a = {8, 243, 43345547, 1234, 9123, 12122, 1216, 125,4};


        sort.radixSort(a);

        for (int value : a) {
            System.out.println(value);
        }
    }
}
