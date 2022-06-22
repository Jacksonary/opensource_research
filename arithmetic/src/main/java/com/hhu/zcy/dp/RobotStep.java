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
        System.out.println(getTotalCount(3, 1, 3, 3));
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
}
