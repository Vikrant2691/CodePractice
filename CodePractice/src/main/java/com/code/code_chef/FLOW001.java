package com.code.code_chef;

import java.util.Scanner;

public class FLOW001 {

    public static void main(String[] args) {

        try {
            Scanner sc = new Scanner(System.in);

            int t = sc.nextInt();
            while (t-- > 0) {
                Integer x = sc.nextInt();
                Integer y = sc.nextInt();
                System.out.println(x + y);
            }
        } catch (Exception e) {
            return;
        }
    }
}
