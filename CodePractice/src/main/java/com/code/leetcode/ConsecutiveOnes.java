package com.code.leetcode;

public class ConsecutiveOnes {

    public static void main(String[] args) {

        int[] nums = {1,1,0,1,1,1};

        System.out.println(findMaxConsecutiveOnes(nums));

    }

    public static int findMaxConsecutiveOnes(int[] nums) {

        int counter=0;
        int currentMaxCount=Integer.MIN_VALUE;
        for (int num : nums) {
            if (num == 1) {
                counter++;
            } else {
                if (currentMaxCount < counter) {
                    currentMaxCount = counter;
                }
                counter = 0;
            }
        }
        if(currentMaxCount<counter){
            currentMaxCount=counter;
        }

        return currentMaxCount;
    }

}
