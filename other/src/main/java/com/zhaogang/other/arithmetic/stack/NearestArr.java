package com.zhaogang.other.arithmetic.stack;

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
        for (int i = 0; i < arr.length; i++) {
            printNearest(i);
        }
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

}
