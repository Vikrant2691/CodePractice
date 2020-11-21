package com.code.leetcode;

import java.util.Arrays;

public class RunningSum {


    public static void main(String[] args) {
        int[] nums = new int[4];
        int [] result= new int[4];
        nums[0] = 1;
        nums[1] = 2;
        nums[2] = 3;
        nums[3] = 4;


        numSubarrayBoundedMax(nums,2,3);
        

    }


    public static int numSubarrayBoundedMax(int[] arr, int L, int R) {
        int i = 0, j = 0, pvc = 0, ans = 0;

        for(i=0;i<arr.length;i++){
            if(arr[i]>=L && arr[i]<=R){
                pvc = i - j + 1;
                ans += i - j + 1;
            }else if(arr[i]<L) ans += pvc;
            else{
                pvc = 0;
                j = i + 1;
            }
        }

        return ans;
    }


}
