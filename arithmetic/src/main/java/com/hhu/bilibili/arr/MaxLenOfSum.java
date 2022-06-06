package com.hhu.bilibili.arr;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jacks
 * @date 2021/10/22
 * @description
 * @formatter:off
 * 给定一个无序数组 arr，其中元素可正、可负、可 0。给定一个整数 k，求 arr 所有的子数组
 * 中累加和为 k 的最长子数组长度。
 * 补充问题 1：给定一个无序数组 arr，其中元素可正、可负、可 0。求 arr 所有的子数组中
 * 正数与负数个数相等的最长子数组长度。
 * 补充问题 2：给定一个无序数组 arr，其中元素只是 1 或 0。求 arr 所有的子数组中 0 和 1
 * 个数相等的最长子数组长度
 * @formatter:on
 */
public class MaxLenOfSum {
    public static void main(String[] args) {
        int[] arr = new int[] {1, 2, 3, 3};
        System.out.println(getMaxLen(3, arr));
    }

    private static int getMaxLen(int target, int[] arr) {
        Map<Integer, Integer> map = new HashMap<>(arr.length);
        // 重要，为了解决从第一个元素开始的子序列
        map.put(0, -1);
        int sum = 0;
        int len = -1;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];

            // s(j-1) = s(i) - k
            Integer index = map.get(sum - target);
            if (index != null) {
                // start: j(index-1) , end: i()
                System.out.println(index + " " + i);
                len = Math.max(i - index, len);
            }

            // 只记录最早出现的那个位置
            if (!map.containsKey(sum)) {
                map.put(sum, i);
            }
        }

        return len;
    }
}
