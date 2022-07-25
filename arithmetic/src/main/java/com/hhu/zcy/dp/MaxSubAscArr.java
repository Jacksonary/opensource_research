package com.hhu.zcy.dp;

import com.hhu.bilibili.util.ArrUtils;

/**
 * @formatter:off 
 * 给定数组 arr，返回 arr 的最长递增子序列。
 * 【举例】
 * arr=[2,1,5,3,6,4,8,9,7]，返回的最长递增子序列为[1,3,4,8,9]。
 * 【要求】
 * 如果 arr 长度为 N，请实现时间复杂度为 O(NlogN)的方法。
 * @formatter:on 
 * @author jacks
 * @date 2022/6/26
 */
public class MaxSubAscArr {

    public static void main(String[] args) {
        int[] arr = new int[] {2, 1, 5, 3, 6, 4, 8, 9, 7};

        int[] maxLen = getMaxLen(arr);
        ArrUtils.printResult(maxLen);
    }

    /**
     * @formatter:off 
     * 思路: 以最大子序列的尾部为节点进行分析，若尾部为 arr[i] 的最大子序列
     * 为 a2...ai(记作f(i)), 那对于 a[i+1] 为尾部的最大子序列有：
     * 1) a[i+1] > a[i], 则最大子序列为 f(i),a[i+1]
     * 2) a[i+1] <= a[i], 则最大子序列为 f(i)
     * 
     * 获取最大长度
     * @formatter:off 
     */
    private static int[] getMaxLen(int[] arr) {
        // 临界条件
        if (arr == null) {
            return null;
        }

        if (arr.length == 0 || arr.length == 1) {
            return arr;
        }

        // 查找以 arr[i] 结尾的最长子序列的长度
        int[] dp = new int[arr.length];
        dp[0] = 1;
        for (int i = 1; i < arr.length; i++) {
            int maxLen = dp[i-1];

            // 直接找个最大的即可
            for (int j = i - 1; j > 0; j--) {
                // 找不到，长度为 1
                if (arr[j] >= arr[i]) {
                    continue;
                }

                // 找到, len + 1, arr[j] > arr[i]
                maxLen = Math.max(dp[j] + 1, maxLen);
            }

            dp[i] = maxLen;
        }

        return getOriginArr(arr, dp);
    }

    /**
     * 按照 max 位置向前推算
     */
    private static int[] getOriginArr(int[] arr, int[] dp) {
        System.out.print(">> dp[" + arr.length + "] = ");

        // 选举最大子序列的长度和索引位置
        int maxLen = 0;
        int maxIndex = 0;
        for (int i = 0; i < dp.length; i++) {
            System.out.print(dp[i] + " ");
            if (dp[i] > maxLen) {
                maxLen = dp[i];
                maxIndex = i;
            }
        }
        System.out.println();

        int[] res = new int[maxLen];
        res[--maxLen] = arr[maxIndex];
        for (int i = maxIndex; i >= 0; i--) {
            if (arr[i] < arr[maxIndex]) {
                res[--maxLen] = arr[i];
                maxIndex = i;
            }
        }

        return res;
    }

    private static int[] getMaxSubLen(int[] arr) {
        return null;
    }

}
