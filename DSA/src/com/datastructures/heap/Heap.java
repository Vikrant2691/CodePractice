package com.datastructures.heap;

import com.sun.org.apache.xerces.internal.xinclude.XPointerElementHandler;
import com.sun.org.apache.xerces.internal.xinclude.XPointerFramework;

public class Heap {

    private int[] heap;
    private int last;

    public Heap(int size) {
        this.heap = new int[size];
        this.last = -1;
    }

    void insert(int n) {

        if (last == heap.length) {
            System.out.println("Heap overflow");
        } else {

            heap[++last] = n;
            int pointer = last;

            while (pointer / 2.0 > 0 && heap[pointer] > (pointer % 2 == 0 ? heap[(pointer - 1) / 2] : heap[(pointer) / 2])) {
                int parentPosition = (pointer % 2 == 0 ? (pointer - 1) / 2 : pointer / 2);
                System.out.println("element " + heap[pointer] + " is greater than " + heap[parentPosition]);
                System.out.println("shuffling");
                int temp = heap[pointer];
                heap[pointer] = heap[parentPosition];
                heap[parentPosition] = temp;
                pointer = parentPosition;
            }
            System.out.println("element added at " + pointer + ": " + heap[pointer] + " and " + (pointer / 2.0 > 0 ? "its parent is " + (pointer % 2 == 0 ? heap[(pointer - 1) / 2] : heap[(pointer) / 2]) : "this a root element"));
        }
    }

    void delete() {
        int x, i, j;
        if (last == -1) {
            System.out.println("Heap empty");
        } else {
            x = heap[0];
            System.out.println("element to be deleted " + x + " is exachanged with " + heap[last]);
            heap[0] = heap[last];
            heap[last] = 0;
            System.out.println("now the first element is " + heap[0]);
            last--;
            i = 0;

            while (i < last) {
                j = (i * 2) + 1;
                System.out.println("the lc of " + heap[i] + " is " + heap[j] + " and rc is " + heap[j + 1]);
                if (heap[j] < heap[j + 1]) {
                    System.out.println(" lc  is less than rc");
                    j = j + 1;
                }
                if (heap[i] < heap[j]) {
                    System.out.println(heap[i] + " exchanged with " + heap[j]);
                    int temp = heap[i];
                    heap[i] = heap[j];
                    heap[j] = temp;
                    i = j;
                    System.out.println("i is at " + i);
                } else
                    break;
            }
            System.out.println("element " + x + " removed");
        }
    }

    void sort() {
        int x, i, j;
        if (last == -1) {
            System.out.println("Heap empty");
        } else {
            while (last >= 0) {
                x = heap[0];
                System.out.println("element to be deleted " + x + " is exachanged with " + heap[last]);
                heap[0] = heap[last];
                heap[last] = x;
                System.out.println("now the first element is " + heap[0]);
                last--;
                i = 0;
                j = (i * 2) + 1;
                while (j < last) {
                    System.out.println("the lc of " + heap[i] + " is " + heap[j] + " and rc is " + heap[j + 1]);
                    if (heap[j] < heap[j + 1]) {
                        System.out.println(" lc  is less than rc");
                        j = j + 1;
                    }
                    if (heap[i] < heap[j]) {
                        System.out.println(heap[i] + " exchanged with " + heap[j]);
                        int temp = heap[i];
                        heap[i] = heap[j];
                        heap[j] = temp;
                        i = j;
                        j=(j*2)+1;
                        System.out.println("i is at " + i);
                    } else
                        break;
                }
                System.out.println("element " + x + " sorted");
            }
        }
    }

    void displayElements() {
        int i = 0;
        while (i <= this.last) {
            System.out.print(heap[i] + " ");
            i++;
        }
        System.out.println();
    }

    void displaySortedElements() {
        int i = 0;
        while (i < heap.length - 1) {
            System.out.print(this.heap[i] + " ");
            i++;
        }
        System.out.println();
    }


}
