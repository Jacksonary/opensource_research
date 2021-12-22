package com.zhaogang.other.arithmetic.stack;

import java.util.Stack;

/**
 * @author jacks
 * @date 2021/6/25
 * @description todo: 单调栈的升级
 * @formatter:off
 * 给定一个不含有重复值的数组 arr，找到每一个 i 位置左边和右边离 i 位置最近且值比 arr[i]
 * 小的位置。返回所有位置相应的信息。
 *
 * arr = {3,4,1,5,6,2,7}
 *
 * 返回如下二维数组作为结果(-1标示没有)：
 * {-1, 2},
 * { 0, 2},
 * {-1,-1},
 * { 2, 5},
 * { 3, 5},
 * { 2,-1},
 * { 5,-1}
 * @formatter:on
 */
public class NearestArr {

    private static final int[] arr = new int[] {3, 4, 1, 5, 6, 2, 7};

    public static void main(String[] args) {
        // 普通方式，分别向两侧遍历，复杂度为O（n^2）
        // for (int i = 0; i < arr.length; i++) {
        // printNearest(i);
        // }

        getArrWithFool(arr);
    }

    private static void printNearest(int startIndex) {
        int left = -1, right = -1;
        int target = arr[startIndex];
        for (int i = startIndex + 1; i < arr.length; i++) {
            if (target > arr[i]) {
                right = i;
                break;
            }
        }

        for (int i = startIndex - 1; i >= 0; i--) {
            if (target > arr[i]) {
                left = i;
                break;
            }
        }

        System.out.println(">> [" + target + "] -> {" + left + ", " + right + "}");
    }

    private static int[][] getArrWithFool(int[] arr) {
        int[][] res = new int[arr.length][2];
        for (int i = 0; i < arr.length; i++) {
            res[i][0] = getLessIndex(arr, i - 1, arr[i]);
            res[i][1] = getLessIndex2(arr, i + 1, arr[i]);

            System.out.printf("[%d] -> {%d, %d}%n", i, res[i][0], res[i][1]);
        }

        return res;
    }

    private static int getLessIndex(int[] arr, int endIndex, int tar) {
        if (endIndex < 0 || arr == null) {
            return -1;
        }

        for (int i = endIndex; i >= 0; i--) {
            if (arr[i] < tar) {
                return i;
            }
        }

        return -1;
    }

    private static int getLessIndex2(int[] arr, int startIndex, int tar) {
        if (arr == null || startIndex > arr.length - 1) {
            return -1;
        }

        for (int i = startIndex; i < arr.length; i++) {
            if (arr[i] < tar) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 单调栈（递增）
     */
    private static void getWithStack(int[] arr) {
        int[][] res = new int[arr.length][2];

        // 存放索引
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < arr.length; i++) {
            int tar = arr[i];
            // 比栈顶大直接入栈
            if (tar < arr[stack.peek()]) {
                stack.add(i);
                continue;
            }

            // 弹栈
            // 比栈顶小
            while (!stack.isEmpty() && tar < arr[stack.peek()]) {
                Integer pop = stack.pop();
                res[pop][0] = stack.isEmpty() ? -1 : stack.peek();
                res[pop][1] = i;
            }

            stack.add(i);
        }

        // 清算
        while (!stack.isEmpty()) {
            Integer pop = stack.pop();
            res[pop][0] = pop;
            res[pop][1] = -1;
        }
    }

}
