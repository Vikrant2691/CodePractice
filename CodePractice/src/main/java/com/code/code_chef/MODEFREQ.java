package com.code.code_chef;

import java.util.Scanner;

public class MODEFREQ {

    public static void main(String[] args) throws java.lang.Exception {

        try {
            Scanner sc = new Scanner(System.in);
            int t = sc.nextInt(), n;

            while (t-- > 0) {
                n = sc.nextInt();
                int a, maxFrequency = 0, result = 0;
                int[] frequency = new int[11];
                int[] modeFrequency = new int[n + 1];

                for (int i = 0; i < n; i++) {
                    a = sc.nextInt();
                    frequency[a]++;
                }

                for (int i = 0; i < 11; i++) {
                    modeFrequency[frequency[i]]++;
                }

                for (int i = 1; i < modeFrequency.length; i++) {
                    if (maxFrequency < modeFrequency[i]) {
                        maxFrequency = modeFrequency[i];
                        result = i;
                    }
                }
                System.out.println(result);
            }
        }
        catch (Exception e){
            return;
        }
    }
}