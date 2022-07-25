package com.hhu.zcy.dp;

/**
 * 给定数组 arr，arr 中所有的值都为正数且不重复。每个值代表一种面值的货币，每种面值 的货币可以使用任意张，再给定一个整数 aim，代表要找的钱数，求换钱有多少种方法。 </br>
 * 【举例】 </br>
 * arr=[5,10,25,1]，aim=0。 组成 0 元的方法有 1 种，就是所有面值的货币都不用。所以返回 1。</br>
 * arr=[5,10,25,1]，aim=15。 组成 15 元的方法有 6 种，分别为 3 张 5 元、1 张 10 元+1 张 5 元、1 张 10 元+5 张 1 元、10 张 1 元+1 张 5 元、2 张 5 元+5 张 1
 * 元和 15 张 1 元。所以返回 6。 </br>
 * arr=[3,5]，aim=2。 任何方法都无法组成 2 元。所以返回 0。
 *
 * @author jacks
 * @date 2022/6/23
 */
public class ChangeCount {
    public static void main(String[] args) {
        int[] unit = new int[] {5, 10, 25, 1};
        int aim = 2;
        System.out.println(getChangeCount(unit, aim, 0));
    }

    /**
     * 暴力解决
     */
    private static int getChangeCount(int[] unit, int aim, int unitIndex) {
        // 2 个临界条件
        if (aim == 0) {
            return 1;
        }

        if (unit == null || unit.length == 0) {
            return 0;
        }

        if (unitIndex == unit.length - 1) {
            return aim % unit[unitIndex] == 0 ? 1 : 0;
        }

        int result = 0;
        for (int i = 0; unit[unitIndex] * i <= aim; i++) {
            result += getChangeCount(unit, aim - unit[unitIndex] * i, unitIndex + 1);
        }
        return result;
    }

    /**
     * todo: 未解决 </br>
     * 使用二维 dp 表优化</br>
     * 可变因素: aim + changeIndex</br>
     * 普通递归存在大量的重复计算的逻辑，dp 表的存在就是为了解决重复计算的问题
     */
    private static int getChangeCountWithDp(int[] unit, int aim, int changeIndex) {
        return -1;
    }
}
