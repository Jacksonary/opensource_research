package com.zhaogang.other.arithmetic.stack;

import java.util.List;

/**
 * @author jacks
 * @date 2021/7/8
 * @description
 * @formatter:off
 * 给定数组 arr 和整数 num，共返回有多少个子数组满足如下情况： max(arr[i..j]) - min(arr[i..j]) <= num
 * max(arr[i..j])表示子数组arr[i..j]中的最大值，min(arr[i..j])表示子数组 arr[i..j]中的最小值。
 *
 * 【要求】若数组长度为N，复杂度需要为O(N)。
 * @formatter:on
 */
public class SubArr {
    private static final int[] arr = new int[] {1, 3, 4, 2, 2, 7, 6, 9, 0};

    public static void main(String[] args) {

    }

    private static List<Integer[]> getAllSubArr(int[] arr) {
        if (arr == null) {
            return null;
        }

        for (int i = 0; i < arr.length; i++) {
            for (int j = 1; j < arr.length - i; j++) {

            }
        }
        return null;
    }
}
