package com.algorithms;

import com.datastructures.linkedlist.singlylinkedlist.LinkedList;

public class Sort {

    public void bubbleSort(int a[]) {
        boolean flag = true;

        for (int i = 0; i < a.length - 1; i++) {
            for (int j = 0; j < a.length - 1 - i; j++) {
                if (a[j] > a[j + 1]) {
                    flag = false;
                    int temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                }
            }
            if (flag) {
                break;
            }
        }
    }

    public void insertionSort(int a[]) {

        for (int i = 1; i < a.length; i++) {
            int j = i - 1;
            int x = a[i];
            while (j > -1 && a[j] > x) {
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = x;
        }

    }

    public void selectionSort(int a[]) {

        for (int i = 0; i < a.length - 1; i++) {
            int kVal = 0;
            for (int j = i, k = i; j < a.length; j++) {
                if (a[j] < a[k]) {
                    k = j;
                }
                kVal = k;
            }
            int temp = a[i];
            a[i] = a[kVal];
            a[kVal] = temp;
        }

    }

    void mergeTwoArray(int[] a, int[] b) {
        int m = a.length;
        int n = b.length;
        int i = 0, j = 0, k = 0;
        int[] c = new int[a.length + b.length];

        while (i < m && j < n) {
            if (a[i] <= b[j]) {
                c[k++] = a[i++];
            } else {
                c[k++] = b[j++];
            }
        }
        for (; i < m; i++) {
            c[k++] = a[i];
        }
        for (; j < n; j++) {
            c[k++] = b[j];
        }

        for (int value : c) {
            System.out.println(value);
        }
    }

    void merge(int[] a, int l, int mid, int h) {
        int i = l, j = mid + 1, k = l;
        int[] c = new int[h + 1];

        while (i <= mid && j <= h) {
            if (a[i] < a[j]) {
                c[k++] = a[i++];
            } else {
                c[k++] = a[j++];
            }
        }
        for (; i <= mid; i++) {
            c[k++] = a[i];
        }
        for (; j <= h; j++) {
            c[k++] = a[j];
        }

        for (int m = l; m <= h; m++) {
            a[m] = c[m];
        }
    }

    // Iterative 2 way merge sort
    public void mergeSortIterative(int[] a) {
        int n = a.length;
        int p, l, h, mid, i;

        for (p = 2; p <= n; p *= 2) {
            for (i = 0; i + p - 1 < n; i += p) {
                l = i;
                h = i + p - 1;
                mid = (l + h) / 2;
                merge(a, l, mid, h);
            }
        }
        if ((p / 2) < n) {
            merge(a, 0, (p / 2) - 1, n - 1);
        }
    }

    public void mergeSortR(int[] a) {
        mergeSortRecursive(a, 0, a.length - 1);
    }

    // Recursive 2 way merge sort
    public void mergeSortRecursive(int[] a, int l, int h) {

        if (l < h) {
            int mid = (l + h) / 2;
            mergeSortRecursive(a, l, mid);
            mergeSortRecursive(a, mid + 1, h);
            merge(a, l, mid, h);
        }

    }

    public int findMax(int[] a) {
        int max = 0;

        for (int value : a) {
            if (max < value) {
                max = value;
            }
        }
        return max;
    }

    public void countSort(int[] a) {

        int[] c = new int[findMax(a) + 1];
        for (int value : a) {
            c[value]++;
        }
        int i = 0, j = 0;
        while (i < c.length) {
            if (c[i] > 0) {
                a[j++] = i;
                c[i]--;
            } else {
                i++;
            }
        }

    }

    public void binSort(int[] a) {

        LinkedList[] c = new LinkedList[findMax(a) + 1];

        for (int i = 0; i < c.length; i++) {
            c[i] = new LinkedList();
        }

        for (int value : a) {
            c[value].addElement(value);
        }

        int i = 0, j = 0;

        while (i < c.length) {
            while (!c[i].isEmpty()) {
                a[j++] = c[i].getElement();
            }
            i++;
        }
    }

    public void radixSort(int[] a) {

        // get maximum value
        int max = findMax(a), i = 1;
        // set the value of i with the digits of maximum number
        while (max != 0) {
            max /= 10;
            i *= 10;
        }

        LinkedList[] radix = new LinkedList[10];

        for (int j = 0; j < radix.length; j++) {
            radix[j] = new LinkedList();
        }

        for (int j = 1; j < i; j *= 10) {
            for (int value : a) {
                radix[(value / j) % 10].addElement(value);
            }
            int h = 0, k = 0;
            while (h < radix.length) {
                while (!radix[h].isEmpty()) {
                    a[k++] = radix[h].getElement();
                }
                h++;
            }
        }


    }


}
