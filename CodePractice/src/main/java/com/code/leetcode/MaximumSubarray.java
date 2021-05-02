package com.code.leetcode;

public class MaximumSubarray {


    public int maxSubArray(int[] nums) {

        int max=Integer.MIN_VALUE;

        for(int i=0;i<nums.length;i++){
            int sum=nums[i];
            if(sum>max)
                max=sum;
            for(int j=i;j<nums.length;j++){
                if(i!=j){
                  sum+=nums[j];
                  if(sum>max)
                      max=sum;
                }
            }
        }

        return max;
    }

    public static void main(String[] args) {
        MaximumSubarray msa= new MaximumSubarray();

        System.out.println(msa.maxSubArray(new int[] {-1}));
    }

}
