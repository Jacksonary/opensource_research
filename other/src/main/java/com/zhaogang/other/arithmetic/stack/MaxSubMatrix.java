package com.zhaogang.other.arithmetic.stack;

import com.zhaogang.other.arithmetic.util.ArrUtils;

/**
 * @author jacks
 * @date 2021/6/28
 * @description
 * @formatter:off
 * 给定一个整型矩阵 map，其中的值只有 0 和 1 两种，求其中全是 1 的所有矩形区域中，最
 * 大的矩形区域为 1 的数量。
 * 例如：
 * 1 1 1 0
 * 其中，最大的矩形区域有 3 个 1，所以返回 3。
 * 再如：
 * 1 0 1 1
 * 1 1 1 1
 * 1 1 1 0
 * 其中，最大的矩形区域有 6 个 1，所以返回 6
 * @formatter:on
 */
public class MaxSubMatrix {
    private static final int[][] ARR = new int[][] {{1, 0, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 0}};

    public static void main(String[] args) {
        System.out.println(getMaxSubMatrixSize(ARR));
        //        System.out.println(getMaxSize2(new int[] {3, 2, 3, 0}));
    }

    private static int getMaxSubMatrixSize(int[][] arr) {
        if (arr == null) {
            return 0;
        }

        int len = arr[0].length;
        int[] height = new int[len];
        int maxSize = 0;

        // 以每行为基地向上，寻找连续1的个数
        for (int i = 0; i < arr.length; i++) {
            int index = 0;
            for (int j = 0; j < arr[i].length; j++) {
                if (i == 0) {
                    height[index++] = arr[i][j] == 1 ? 1 : 0;
                    continue;
                }

                height[index] = arr[i][j] == 1 ? height[index] + 1 : 0;
                index++;
            }

            ArrUtils.printResult(height);
            //            int singleMaxSize = getMaxSize2(height);
            int singleMaxSize = getMaxSize3(height);
            if (singleMaxSize > maxSize) {
                maxSize = singleMaxSize;
            }
        }

        return maxSize;
    }

    /**
     * 最优解，利用单调栈，复杂度为 O(n)
     */
    private static int getMaxSize(int[] height) {
        if (height == null) {
            return 0;
        }

        return 0;
    }

    /**
     * 笨方法，直接左右遍历
     */
    private static int getMaxSize2(int[] height) {
        int max = 0;
        for (int i = 0; i < height.length; i++) {
            int width = 1;
            // 往左遍历
            for (int j = i - 1; j >= 0; j--) {
                if (height[j] < height[i]) {
                    break;
                }
                width++;
            }

            // 往右遍历
            for (int j = i + 1; j < height.length; j++) {
                if (height[j] < height[i]) {
                    break;
                }
                width++;
            }

            int size = height[i] * width;
            if (size > max) {
                max = size;
            }
        }

        return max;
    }

    /**
     * 2次循环，这个更笨
     */
    private static int getMaxSize3(int[] height) {
        if (height == null) {
            return 0;
        }

        int maxSize = 0;
        for (int i = 0; i < height.length; i++) {
            int minHeight = height[i];
            for (int j = i; j < height.length; j++) {
                minHeight = Math.min(minHeight, height[j]);
                maxSize = Math.max(maxSize, (j - i + 1) * minHeight);
            }
        }

        return maxSize;
    }
}
