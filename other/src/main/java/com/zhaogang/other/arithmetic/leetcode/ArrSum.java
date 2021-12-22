package com.zhaogang.other.arithmetic.leetcode;

/**
 * @author jacks
 * @date 2021/12/17
 */
public class ArrSum {

    public static void main(String[] args) {
        System.out.println(maxSubArray(new int[] {-2, 1}));
    }

    public static int maxSubArray(int[] nums) {
        int[] helper = new int[nums.length];
        int max = nums[0];
        for (int i = 0; i < nums.length; i++) {
            helper[i] = nums[i];
            max = Math.max(max, helper[i]);
            for (int j = i + 1; j < nums.length; j++) {
                helper[j] = helper[j - 1] + nums[j];
                max = Math.max(max, helper[j]);
            }
        }
        return max;
    }
}
