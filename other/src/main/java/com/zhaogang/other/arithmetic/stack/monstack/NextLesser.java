package com.zhaogang.other.arithmetic.stack.monstack;

import com.zhaogang.other.arithmetic.util.ArrUtils;

/**
 * @author jacks
 * @date 2021/7/3
 * @description 给定一个不含有重复值的数组 arr，找到每一个 i 位置左边和右边离 i 位置最近且值比 arr[i]
 * 小的位置。返回所有位置相应的信息。
 * <p>
 * 输入：arr = {3,4,1,5,6,2,7}
 * 输出：
 * {
 * {-1, 2},
 * { 0, 2},
 * {-1,-1},
 * { 2, 5},
 * { 3, 5},
 * { 2,-1},
 * { 5,-1}
 * }
 */
public class NextLesser {
    private static int[] arr = new int[] {3, 4, 1, 5, 6, 2, 7};

    public static void main(String[] args) {
        int[][] res = rightWay(arr);
        ArrUtils.printResult(res);
    }

    /**
     * 暴力解体，直接左右遍历即可，时间复杂度O(n^2)
     */
    private static int[][] rightWay(int[] arr) {
        int[][] res = new int[arr.length][2];
        for (int i = 0; i < arr.length; i++) {
            int leftLessIndex = -1;
            int rightLessIndex = -1;
            int cur = i - 1;
            while (cur >= 0) {
                if (arr[cur] < arr[i]) {
                    leftLessIndex = cur;
                    break;
                }
                cur--;
            }
            cur = i + 1;
            while (cur < arr.length) {
                if (arr[cur] < arr[i]) {
                    rightLessIndex = cur;
                    break;
                }
                cur++;
            }
            res[i][0] = leftLessIndex;
            res[i][1] = rightLessIndex;
        }
        return res;
    }
}
