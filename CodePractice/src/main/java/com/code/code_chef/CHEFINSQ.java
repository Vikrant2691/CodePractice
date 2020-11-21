package com.code.code_chef;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class CHEFINSQ {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer t = in.nextInt();
        String knn = in.nextLine();
        String arr[] = knn.split(" ");
        Integer k = in.nextInt();
        Integer n = in.nextInt();

        List<Integer> a = new ArrayList<Integer>();
        for (int i = 0; i < k; i++)
            a.add(in.nextInt());



        Collections.sort(a);

        for (int i=0;i<k;i++){
            System.out.print(i+" ");
        }

        
    }
}
