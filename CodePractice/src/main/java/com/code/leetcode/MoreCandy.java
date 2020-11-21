package com.code.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MoreCandy {

    public List<Boolean> kidsWithCandies(int[] candies, int extraCandies) {

        List<Boolean> result = new ArrayList<Boolean>();
        int max_value = 0;

        for (int candy : candies) {
            if (candy > max_value) {
                max_value = candy;
            }
        }

        for (int candy : candies) {
            if (max_value <= (candy + extraCandies)) {
                result.add(true);
            } else {
                result.add(false);
            }
        }


        return result;
    }

    public static void main(String[] args) {

        MoreCandy mc = new MoreCandy();

        int[] candies = {4, 2, 1, 1, 2};

        List<Boolean> op = mc.kidsWithCandies(candies, 1);

        for (Boolean i : op) {
            System.out.print(i);
        }

    }


}
