package com.hhu.bilibili.leetcode;

/**
 * @author jacks
 * @date 2021/12/17
 */
public class BinarySearch {

    public static void main(String[] args) {
        System.out.println(searchInsert(new int[] {1, 3, 5}, 1));
    }

    public static int searchInsert(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = (left + right) / 2;
            if (nums[mid] == target) {
                return mid;
            }

            if (mid == left || mid == right) {
                break;
            }

            if (nums[mid] > target) {
                right = mid;
            }

            if (nums[mid] < target) {
                left = mid;
            }
        }

        if (nums[right] < target) {
            return right + 1;
        }
        if (nums[left] > target) {
            return 0;
        }
        return left + 1;
    }
}
