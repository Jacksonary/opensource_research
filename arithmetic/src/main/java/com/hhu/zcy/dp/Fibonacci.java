package com.hhu.zcy.dp;

/**
 * 给定整数 N，返回斐波那契数列的第 N 项。
 *
 * @author jacks
 * @date 2022/6/16
 */
public class Fibonacci {

    public static void main(String[] args) {
        // fibonacci: 1 1 2 3 5
        // for (int i = 1; i < 5; i++) {
        // System.out.println("getN: " + getN(i));
        // System.out.println("getN2: " + getN2(i));
        // }

        System.out.println("getN: " + getN(8));
        System.out.println("getN2: " + getN2(8));
        System.out.println(getMethodCount(4));

        System.out.println(getMoCount(6));
        System.out.println(getMoCount2(6));
    }

    /**
     * 暴力方法: O(n^2)
     * 
     * @param n
     * @return
     */
    // 4 = 3 + 2 = 2 + 1 + 2
    private static int getN(int n) {
        return n < 3 ? 1 : getN(n - 1) + getN(n - 2);
    }

    /**
     * 利用数组记录每次计算的结果，避免每次都去算</br>
     * O(n)
     */
    private static int getN2(int n) {
        int target = n - 1;
        if (target == 0 || target == 1) {
            return 1;
        }
        int[] resultArr = new int[target];
        for (int i = 0; i < target; i++) {
            resultArr[i] = i < 2 ? 1 : resultArr[i - 1] + resultArr[i - 2];
        }
        return resultArr[target - 1] + resultArr[target - 2];
    }

    /**
     * 补充问题 ：给定整数 N，代表台阶数，一次可以跨 2 个或者 1 个台阶，返回有多少种走法。
     *
     * 思路: 前面补1构成 fibonacci 即可
     */
    private static int getMethodCount(int n) {
        if (n == 1) {
            return 1;
        }

        int[] resultArr = new int[n];
        for (int i = 0; i < n; i++) {
            resultArr[i] = i < 2 ? 1 : resultArr[i - 1] + resultArr[i - 2];
        }
        return resultArr[n - 1] + resultArr[n - 2];
    }

    /**
     * 假设农场中成熟的母牛每年只会生 1 头小母牛，并且永远不会死。第一年农 场有 1 只成熟的母牛，从第二年开始，母牛开始生小母牛。每只小母牛 3 年之后成熟又可以生 小母牛。给定整数 N，求出 N 年后牛的数量。
     *
     * 分析: 第 1 年 1 头成熟母牛记为 a；第 2 年 a 生了新的小母牛，记为 b，总牛数为 2；第 3 年 a 生了新的小母牛，记为 c，总牛数为 3；第 4 年 a 生了新的小母牛，记为 d，总牛数为 4。 第 5 年
     * b 成熟了，a 和 b 分别生了新的小母牛，总牛数为 6；第 6 年 c 也成熟了，a、b 和 c 分 别生了新的小母牛，总牛数为 9，返回 9
     * 
     * 实际和 fibonacci 类似，只是中间跳了一个
     * 
     * @param n
     * @return
     */
    private static int getMoCount(int n) {
        if (n <= 3) {
            return n;
        }

        return getMoCount(n - 1) + getMoCount(n - 3);
    }

    /**
     * 在原先基础上以 fibonacci 数组方式进行优化
     * 
     * @param n
     * @return
     */
    private static int getMoCount2(int n) {
        if (n <= 3) {
            return n;
        }

        int[] resultArr = new int[n];
        for (int i = 0; i < n; i++) {
            resultArr[i] = i <= 3 ? i : resultArr[i - 1] + resultArr[i - 3];
        }

        return resultArr[n - 1] + resultArr[n - 3];
    }
}
