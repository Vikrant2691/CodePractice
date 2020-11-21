package com.code.code_chef;

import java.util.Scanner;


public class LINCHESS {


    public static void main(String[] args) {

        try {
            Scanner sc = new Scanner(System.in);

            int t = sc.nextInt();
            for (int i = 0; i < t; i++) {
                int n = sc.nextInt();
                long[] p = new long[n];
                long k = sc.nextLong(), q = 1000000001, res = 1000000001;
                for (int j = 0; j < p.length; j++) {
                    p[j]=sc.nextLong();
                    if (k % p[j] == 0) {
                        if (k / p[j] < q) {
                            q = k / p[j];
                            res = p[j];
                        }
                    }
                }
                if (res == 1000000001){
                    System.out.println("-1");
                }
                else{
                    System.out.println(res);
                }
            }

        } catch (Exception e) {
            return;
        }

    }


}
