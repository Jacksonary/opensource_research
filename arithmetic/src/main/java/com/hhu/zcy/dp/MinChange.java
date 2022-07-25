package com.hhu.zcy.dp;

/**
 * todo: 待解决 | 丫的看不懂 </br>
 * 给定数组 arr，arr 中所有的值都为正数且不重复。每个值代表一种面值的货币， 每种面值 的货币可以使用任意张，再给定一个整数 aim，代表要找的钱数， 求组成 aim 的最少货币数。
 * 
 * arr=[5,2,3]，aim=20。 4 张 5 元可以组成 20 元，其他的找钱方案都要使用更多张的 货币，所以返回 4。 arr=[5,2,3]，aim=0。 不用任何货币就可以组成 0 元，返回 0。
 * arr=[3,5]，aim=2。 根本无法组成 2 元，钱不能找开的情况下默认返回-1
 * 
 * @author jacks
 * @date 2022/6/21
 */
public class MinChange {

    public static void main(String[] args) {
        int[] unit = new int[] {5, 2, 3};
        int aim = 20;

        System.out.println(getMinChange(unit, aim));
        System.out.println(minCoins1(unit, aim));
    }

    private static int getMinChange(int[] unit, int aim) {
        // 兑换的货币无或最小货币大于目标货币 | 兑换货币小于 0 则直接返回
        if (unit == null || unit.length == 0 || aim < 0) {
            return -1;
        }

        if (aim == 0) {
            return 0;
        }

        // 排序，如果最小的大于 aim 就没得选了
        // sort(unit, 0, unit.length - 1);
        // if (aim < unit[0]) {
        // return -1;
        // }

        return getCount(unit, 0, aim);
    }

    /**
     * 考虑在使用 changeIndex 去兑换 aim, 所需要的货币数量，最大除数 和背包问题类似
     * 
     * @param changeIndex
     *            标识从 0 到 changeIndex 上可以兑换成功的货币
     * @param aim
     *            找零的总数
     */
    private static int getCount(int[] unit, int changeIndex, int aim) {
        // 已经到最后一个了，也是临界条件
        if (changeIndex == unit.length) {
            return aim == 0 ? 1 : -1;
        }

        // 初始为 -1，默认还没找到
        int result = -1;
        // 对 changeIndex 尝试使用多张，每次都去尝试找一个是否可以完整的兑换剩余货币
        for (int i = 0; i * unit[changeIndex] <= aim && i < unit.length - 1; i++) {
            int next = getCount(unit, i + 1, result - i * unit[changeIndex]);

            // 找到了
            if (next != -1) {
                result = result == -1 ? next + i : Math.min(result, i * unit[changeIndex]);
            }
        }

        return result;
    }

    public static int minCoins1(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return -1;
        }
        return process(arr, 0, aim);
    }

    // 当前考虑的面值是 arr[i]，还剩 rest 的钱需要找零
    // 如果返回-1，说明自由使用 arr[i..N-1]面值的情况下，无论如何也无法找零 rest
    // 如果返回不是-1，代表自由使用 arr[i..N-1]面值的情况下，找零 rest 需要的最少张数
    public static int process(int[] arr, int i, int rest) {
        // base case：
        // 已经没有面值能够考虑了
        // 如果此时剩余的钱为 0，返回 0 张
        // 如果此时剩余的钱不是 0，返回-1
        if (i == arr.length) {
            return rest == 0 ? 0 : -1;
        }

        // 最少张数，初始时为-1，因为还没找到有效解
        int res = -1;
        // 依次尝试使用当前面值(arr[i])0 张、1 张、k 张，但不能超过 rest
        for (int k = 0; k * arr[i] <= rest; k++) {
            // 使用了 k 张 arr[i]，剩下的钱为 rest - k * arr[i]
            // 交给剩下的面值去搞定(arr[i+1..N-1])
            int next = process(arr, i + 1, rest - k * arr[i]);
            if (next != -1) {
                // 说明这个后续过程有效
                res = res == -1 ? next + k : Math.min(res, next + k);
            }
        }
        return res;
    }

    private static void sort(int[] arr, int low, int high) {
        int left = low;
        int right = high;

        int pivot = arr[low];
        int emptyIndex = low;
        while (left < right) {
            while (left < right && arr[right] >= pivot) {
                right--;
            }

            if (left < right) {
                arr[emptyIndex] = arr[right];
                emptyIndex = right;
            }

            while (left < right && arr[left] <= pivot) {
                left++;
            }

            if (left < right) {
                arr[emptyIndex] = arr[left];
                emptyIndex = left;
            }
        }

        arr[emptyIndex] = pivot;

        if (left - low > 1) {
            sort(arr, low, left - 1);
        }

        if (high - right > 1) {
            sort(arr, right + 1, high);
        }
    }
}
