package com.hhu.zcy.dp;

/**
 * 题目:</br>
 * 假设有排成一行的 N 个位置，记为 1~N，N 一定大于或等于 2。开始时机器人在其中的 M 位置上（M 一定是 1～N 中的一个），机器人可以往左走或者往右走，如果机器人来到 1 位置， 那么下一步只能往右来到 2
 * 位置；如果机器人来到 N 位置，那么下一步只能往左来到 N-1 位置。 规定机器人必须走 K 步，最终能来到 P 位置（P 也一定是 1～N 中的一个）的方法有多少种。给 定四个参数 N、M、K、P，返回方法数
 * 
 * 举例:</br>
 * N=5,M=2,K=3,P=3 上面的参数代表所有位置为 1 2 3 4 5。机器人最开始在 2 位置上，必须经过 3 步，最后到 达 3 位置。走的方法只有如下 3 种：</br>
 * 1）从 2 到 1，从 1 到 2，从 2 到 3 </br>
 * 2）从2 到 3，从 3 到 2，从 2 到 3 </br>
 * 3）从 2 到 3，从 3 到 4，从 4 到 3 </br>
 * 所以返回方法数 3。 </br>
 * </br>
 * N=3,M=1,K=3,P=3 上面的参数代表所有位置为 1 2 3。机器人最开始在 1 位置上，必须经过 3 步，最后到达 3 位置。怎么走也不可能，所以返回方法数 0。
 * 
 * @author jacks
 * @date 2022/6/22
 */
public class RobotStep {
    public static void main(String[] args) {
        System.out.println(getTotalCount(7, 4, 9, 5));
        System.out.println(getTotalCount(7, 4, 9, 5));
        System.out.println(getTotalCountWithDp(7, 4, 9, 5));
        System.out.println(getTotalCountWithDp2(7, 4, 9, 5));

        System.out.println(getStep(5, 2, 3, 3));
        System.out.println(getSepWithDP(5, 2, 3, 3));
        System.out.println(getSepWithDP2(5, 2, 3, 3));
    }

    private static int getStep(int n, int m, int p, int k) {
        if (k == 0) {
            return m == p ? 1 : 0;
        }

        if (m == 1) {
            return getStep(n, m + 1, p, k - 1);
        }

        if (m == n) {
            return getStep(n, m - 1, p, k - 1);
        }

        return getStep(n, m - 1, p, k - 1) + getStep(n, m + 1, p, k - 1);
    }

    /**
     * m k
     */
    private static int getSepWithDP(int n, int m, int p, int k) {
        int[][] dp = new int[k + 1][n];
        dp[0][m - 1] = 1;

        for (int i = 1; i < k + 1; i++) {
            dp[i][0] = dp[i - 1][1];
            dp[i][n - 1] = dp[i - 1][n - 2];
            for (int j = 1; j < n - 1; j++) {
                dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j + 1];
            }
        }

        return dp[k][p - 1];
    }

    private static int getSepWithDP2(int n, int m, int p, int k) {
        int[] dp = new int[n];
        dp[m - 1] = 1;

        // 空间压缩
        for (int j = 1; j < k + 1; j++) {
            int pre;
            for (int i = 0; i < n; i++) {
                pre = 0;
                if (i == 0) {
                    dp[i] = pre + dp[i + 1];
                    continue;
                }

                if (i == n - 1) {
                    dp[i] = pre;
                    continue;
                }

                dp[i] = pre + dp[i + 1];
            }
        }

        return dp[p - 1];
    }

    /**
     * 暴力递归法，注意边界
     */
    private static int getTotalCount(int n, int m, int k, int p) {
        if (k <= 0) {
            return m == p ? 1 : 0;
        }

        if (m == 1) {
            return getTotalCount(n, m + 1, k - 1, p);
        }
        if (m == n) {
            return getTotalCount(n, m - 1, k - 1, p);
        }

        return getTotalCount(n, m + 1, k - 1, p) + getTotalCount(n, m - 1, k - 1, p);
    }

    /**
     * 借助二维 dp 表优化 </br>
     * n 位置数 </br>
     * m 初始位置(当前位置) | 可变 </br>
     * k 剩余步数 | 可变 </br>
     * p 目的地 </br>
     */
    private static int getTotalCountWithDp(int n, int m, int k, int p) {
        int[][] result = new int[k + 1][n];
        for (int i = 0; i < k + 1; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0) {
                    if (j == p - 1) {
                        result[i][j] = 1;
                    }
                    continue;
                }

                if (j == 0) {
                    result[i][j] = result[i - 1][j + 1];
                    continue;
                }

                if (j == n - 1) {
                    result[i][j] = result[i - 1][j - 1];
                    continue;
                }

                result[i][j] = result[i - 1][j - 1] + result[i - 1][j + 1];
            }
        }

        return result[k][m - 1];
    }

    /**
     * 利用滚动数组优化二维 dp 数组
     */
    private static int getTotalCountWithDp2(int n, int m, int k, int p) {
        int[] result = new int[n];
        result[p - 1] = 1;
        for (int i = 1; i < k + 1; i++) {
            int tmp = 0;
            for (int j = 0; j < n; j++) {
                if (j == 0) {
                    tmp = result[j];
                    result[j] = result[j + 1];
                    continue;
                }

                if (j == n - 1) {
                    result[j] = tmp;
                    continue;
                }

                // 记录之前的值
                int cur = result[j];
                // 注意这里滚动时需要使用额外的变量来记录上个位置的值，否则会被覆盖
                // result[j] = result[j - 1] + result[j + 1];
                result[j] = tmp + result[j + 1];
                tmp = cur;
            }
        }

        return result[m - 1];
    }
}
