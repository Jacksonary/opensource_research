package com.zhaogang.other.arithmetic.dp;

/**
 * @formatter:off 
 * 给定数组 arr，arr 中所有的值都为正数且不重复。每个值代表一种面值的货币，每种面值
 * 的货币可以使用任意张，再给定一个整数 aim，代表要找的钱数，求组成 aim 的最少货币数。
 * 
 * arr=[5,2,3]，aim=20。
 * 4 张 5 元可以组成 20 元，其他的找钱方案都要使用更多张的货币，所以返回 4。
 * arr=[5,2,3]，aim=0。
 * 不用任何货币就可以组成 0 元，返回 0。
 * arr=[3,5]，aim=2。
 * 根本无法组成 2 元，钱不能找开的情况下默认返回-1。
 * @formatter:on 
 * 
 * @author jacks
 * @date 2021/12/14
 */
public class MoneyChange {
    public static void main(String[] args) {
        int[] toChanges = new int[] {5, 2, 3};
        getMinNum(toChanges, 20);
    }

    private static void sort(int[] nums, int low, int high) {
        if (nums == null) {
            return;
        }

        int left = low;
        int right = high;
        int pivot = nums[low];
        int emptyIndex = low;

        while (left < right) {
            while (left < right && nums[right] >= pivot) {
                right--;
            }

            if (left < right) {
                nums[emptyIndex] = nums[right];
                emptyIndex = right;
            }

            while (left < right && nums[left] <= pivot) {
                left++;
            }

            if (left < right) {
                nums[emptyIndex] = nums[left];
                emptyIndex = left;
            }
        }

        nums[emptyIndex] = pivot;

        if (high - right >= 1) {
            sort(nums, right + 1, high);
        }

        if (left - low >= 1) {
            sort(nums, low, left - 1);
        }
    }

    private static int getMinNum(int[] toChanges, int target) {
        if (toChanges == null) {
            return -1;
        }

        // 排序
        sort(toChanges, 0, toChanges.length - 1);

        // 贪心，从大的开始
        int maxItem = toChanges.length - 1;
        int maxNum = getMaxNum(target, toChanges[maxItem]);
        target = target - maxNum * toChanges[maxItem];

        return -1;
    }

    private static int getMaxNum(int target, int item) {
        return target / item;
    }
}
