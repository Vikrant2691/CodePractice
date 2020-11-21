package com.code.code_chef;

import java.util.Scanner;

public class HS08TEST {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        String a = null;
        if (in.hasNext()) {
            a = in.nextLine();
        }

        String arr[] = a.split(" ");
        Double x = Double.parseDouble(arr[0]);
        Double y = Double.parseDouble(arr[1]);
        if (x % 5 != 0) {
            System.out.println(y);
        } else {

            if (x + 0.50 < y) {
                System.out.println(y - x - 0.50);
            } else {
                System.out.println(y);
            }
        }

    }
}
