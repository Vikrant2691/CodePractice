package com.code.code_chef;

import java.util.Scanner;

public class INTEST {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int count = 0;
        String a = null;
        if (in.hasNext()) {
            a = in.nextLine();


            String arr[] = a.split(" ");
            Integer n = Integer.parseInt(arr[0]);
            Integer k = Integer.parseInt(arr[1]);

            for (int i = 0; i < n; i++) {

                if (in.nextInt() % k == 0)
                    count++;

            }

            System.out.println(count);

        }
    }
}
