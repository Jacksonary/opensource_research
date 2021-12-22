package com.zhaogang.other.arithmetic.dp;

/**
 * @formatter:off 
 * 给定整数 N，返回斐波那契数列的第 N 项。 
 * 补充问题 1：给定整数 N，代表台阶数，一次可以跨 2 个或者 1 个台阶，返回有多少种走法
 * 1 2 3 4 5 6
 * 1 1 2 3 5 8
 * @formatter:on 
 * @author jacks
 * @date 2021/12/1
 */
public class Fibonacci {
    public static void main(String[] args) {
        // int tar = getF1(6);
        // int tar = getF2(6);
        int tar = getF3(6);
        // int tar = getFF1(3);
        System.out.println(tar);
    }

    /**
     * Fibonacci 数列
     * 
     * @param index
     * @return
     */
    private static int getF1(int index) {
        if (index == 1 || index == 2) {
            return 1;
        }

        return getF1(index - 1) + getF1(index - 2);
    }

    private static int getF2(int index) {
        // 转为实际的下标为 index - 1
        int[] result = new int[index];
        for (int i = 0; i < index; i++) {
            if (i == 0 || i == 1) {
                result[i] = 1;
                continue;
            }
            result[i] = result[i - 1] + result[i - 2];
        }

        return result[index - 1];
    }

    private static int getF3(int index) {
        if (index == 1 || index == 2) {
            return 1;
        }

        int pre = 1;
        int cur = 1;
        int tmp = 2;
        for (int i = 1; i < index; i++) {
            tmp = pre + cur;
            // 前移
            pre = cur;
            cur = tmp;
        }

        return pre;
    }

    /**
     * 变种：青蛙跳台阶
     */
    private static int getFF1(int stepNum) {
        int[] result = new int[stepNum];
        for (int i = 0; i < stepNum; i++) {
            if (i == 0 || i == 1 || i == 2) {
                result[i] = i + 1;
                continue;
            }
            result[i] = result[i - 1] + result[i - 2];
        }

        return result[stepNum - 1];
    }
}
