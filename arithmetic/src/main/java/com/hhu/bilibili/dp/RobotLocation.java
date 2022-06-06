package com.hhu.bilibili.dp;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

/**
 * @formatter:off 
 * 假设有排成一行的 N 个位置，记为 1~N，N 一定大于或等于 2。开始时机器人在其中的 M 
 * 位置上（M 一定是 1～N 中的一个），机器人可以往左走或者往右走，如果机器人来到 1 位置，
 * 那么下一步只能往右来到 2位置；如果机器人来到 N 位置，那么下一步只能往左来到 N-1 位置。 
 * 
 * 规定机器人必须走 K 步，最终能来到 P 位置（P 也一定是 1～N 中的一个）的方法有多少种。
 * 给定四个参数 N、M、K、P，返回方法数
 * 
 * N=5, M=2, K=3, P=3
 * 上面的参数代表所有位置为 1 2 3 4 5。机器人最开始在 2 位置上，必须经过 3 步，最后到
 * 达 3 位置。走的方法只有如下 3 种：
 * 1）从 2 到 1，从 1 到 2，从 2 到 3
 * 2）从 2 到 3，从 3 到 2，从 2 到 3
 * 3）从 2 到 3，从 3 到 4，从 4 到 3
 * 所以返回方法数 3。
 * N=3, M=1, K=3, P=3
 * 上面的参数代表所有位置为 1 2 3。机器人最开始在 1 位置上，必须经过 3 步，最后到达 3
 * 位置。怎么走也不可能，所以返回方法数 0
 * @formatter:on 
 * 
 * @author jacks
 * @date 2021/12/14
 */
public class RobotLocation {
    public static void main(String[] args) {
        // System.out.println(getStepsNum1(5, 2, 3, 3));
        // System.out.println(ways1(5, 2, 3, 3));
        //
        // System.out.println(getStepsNum2(5, 2, 3, 3));
        System.out.println(isValid("()"));
    }

    public static boolean isValid(String s) {
        if (s == null) {
            return true;
        }

        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (stack.isEmpty()) {
                stack.push(c);
                continue;
            }

            if (isMatch(c, stack.peek())) {
                stack.pop();
            } else {
                stack.push(c);
            }
        }

        return stack.isEmpty();
    }

    private static boolean isMatch(char c, char n) {
        if ('(' == c) {
            return ')' == n;
        }

        if ('[' == c) {
            return ']' == n;
        }

        if ('{' == c) {
            return '}' == n;
        }

        return false;
    }

    /**
     * @formatter:off
     * 机器人每次有 3 种移动的方式：
     * 1. 若在头部，只能向右移动
     * 2. 若在尾部，只能向左移动
     * 3. 若在中间，可以向左或者向右
     *
     * 临界条件为：剩余移动步数为0
     * @formatter:on
     */
    private static int getStepsNum1(int allLocation, int cur, int end, int remain) {
        if (remain == 0) {
            return cur == end ? 1 : 0;
        }

        if (cur == 1) {
            // 头部位置只能向右
            return getStepsNum1(allLocation, cur + 1, end, --remain);
        }
        if (cur == allLocation) {
            // 末尾位置只能向左
            return getStepsNum1(allLocation, cur - 1, end, --remain);
        }

        // 中间位置可以向两头移
        return getStepsNum1(allLocation, cur + 1, end, remain - 1)
            + getStepsNum1(allLocation, cur - 1, end, remain - 1);
    }

    /**
     * 无效性的矩阵划分
     */
    private static int getStepsNum2(int allLocation, int cur, int end, int remain) {
        // allLocation 和 end 是不变的，不影响过程，建立 cur 和 remain 矩阵
        int[][] helperMatrix = new int[remain + 1][allLocation];
        // x: cur | y: remains，remain 为 0 时只有 end 位置唯一的方法
        helperMatrix[0][end - 1] = 1;
        // 从后续行构建矩阵
        for (int i = 1; i < remain + 1; i++) {
            for (int j = 0; j < allLocation; j++) {
                if (j == 0) {
                    helperMatrix[i][j] = helperMatrix[i - 1][j + 1];
                    continue;
                }
                if (j == allLocation - 1) {
                    helperMatrix[i][j] = helperMatrix[i - 1][j - 1];
                    continue;
                }
                helperMatrix[i][j] = helperMatrix[i - 1][j - 1] + helperMatrix[i - 1][j + 1];
            }
        }

        System.out.println(helperMatrix[remain][end - 1]);
        return helperMatrix[remain][end - 1];
    }

    /**
     * N : 位置为 1 ~ N，固定参数 cur : 当前在 cur 位置，可变参数 rest : 还剩 res 步没有走，可变参数 P : 最终目标位置是 P，固定参数
     *
     * 只能在 1~N 这些位置上移动，当前在 cur 位置，走完 rest 步之后，停在 P 位置的方法数作为返回值返回
     */
    public static int walk(int N, int cur, int rest, int P) {
        // 如果没有剩余步数了，当前的 cur 位置就是最后的位置
        // 如果最后的位置停在 P 上，那么之前做的移动是有效的
        // 如果最后的位置没在 P 上，那么之前做的移动是无效的
        if (rest == 0) {
            return cur == P ? 1 : 0;
        }
        // 如果还有 rest 步要走，而当前的 cur 位置在 1 位置上，那么当前这步只能从 1 走向 2
        // 后续的过程就是来到 2 位置上，还剩 rest-1 步要走
        if (cur == 1) {
            return walk(N, 2, rest - 1, P);
        }
        // 如果还有 rest 步要走，而当前的 cur 位置在 N 位置上，那么当前这步只能从 N 走向 N-1
        // 后续的过程就是来到 N-1 位置上，还剩 rest-1 步要走
        if (cur == N) {
            return walk(N, N - 1, rest - 1, P);
        }
        // 如果还有 rest 步要走，而当前的 cur 位置在中间位置上，那么可以走向左，也可以走向右
        // 走向左之后，后续的过程就是，来到 cur-1 位置上，还剩 rest-1 步要走
        // 走向右之后，后续的过程就是，来到 cur+1 位置上，还剩 rest-1 步要走
        // 走向左、走向右是截然不同的方法，所以总方法数都要算上
        return walk(N, cur + 1, rest - 1, P) + walk(N, cur - 1, rest - 1, P);
    }

    public static int ways1(int N, int M, int K, int P) {
        // 参数无效直接返回 0
        if (N < 2 || K < 1 || M < 1 || M > N || P < 1 || P > N) {
            return 0;
        }
        // 总共 N 个位置，从 M 点出发，还剩 K 步，返回最终能达到 P 的方法数
        return walk(N, M, K, P);
    }

    public boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }

        List<Integer> list = new ArrayList<>();
        while (x != 0) {
            list.add(x % 10);
            x = x / 10;
        }

        for (int i = 0; i < list.size() / 2; i++) {
            if (!Objects.equals(list.get(i), list.get(list.size() - 1 - i))) {
                return false;
            }
        }
        return true;
    }

}
