package com.hhu.bilibili.leetcode;

import java.util.Random;

/**
 * @author jacks
 * @date 2021/9/11
 * @description
 */
public class TargetNum {

    private static Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        int[] ints = {3, 2, 3, 5, 1, 8, 0, 9};
        // System.out.println(findKthLargest1(ints, 2));
        // System.out.println(findKthLargest2(ints, 200));
        System.out.println(findMaxValue(ints, 1));
    }

    private static void fst(int low, int high, int[] arr, int k) {
        int left = low;
        int right = high;
        int pivot = arr[low];
        int empty = low;

        while (left < right) {
            while (left < right && arr[right] >= pivot) {
                right--;
            }

            if (left < right) {
                arr[empty] = arr[right];
                empty = right;
            }

            while (left < right && arr[left] <= pivot) {
                left++;
            }

            if (left < right) {
                arr[empty] = arr[left];
                empty = left;
            }
        }

        // 第一次分开
        arr[empty] = pivot;
        // if (high - empty + 1 == k) {
        // // 枢纽元素就是第k大元素
        //
        // } else if (high - empty + 1 > k) {
        // // 目标元素在右半区
        // if (high - right > 1) {
        // fst(right + 1, high, arr，);
        // }
        // } else {
        // // 目标元素在左半区
        // if (left - low > 1) {
        // fst(low, left - 1, arr);
        // }
        // }

    }

    public static int findKthLargest1(int[] nums, int k) {
        if (k > nums.length) {
            throw new RuntimeException(">> found null");
        }
        int tar = 1;
        for (int i = nums.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (nums[j] > nums[j + 1]) {
                    int tmp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = tmp;
                }
            }
            if (tar == k) {
                return nums[i];
            }
            tar++;
        }
        return nums[nums.length - k];
    }

    public static int findKthLargest2(int[] nums, int k) {
        int len = nums.length;
        int target = len - k;
        int left = 0;
        int right = len - 1;
        while (true) {
            int index = partition(nums, left, right);
            if (index < target) {
                left = index + 1;
            } else if (index > target) {
                right = index - 1;
            } else {
                return nums[index];
            }
        }
    }

    // 在区间 nums[left..right] 区间执行 partition 操作
    private static int partition(int[] nums, int left, int right) {
        // 在区间随机选择一个元素作为标定点
        if (right > left) {
            // 这里做一下随机化特殊处理，防止出现正序或逆序时时间复杂度退到 O(n^2)
            int randomIndex = left + 1 + random.nextInt(right - left);
            swap(nums, left, randomIndex);
        }

        int pivot = nums[left];
        int j = left;
        for (int i = left + 1; i <= right; i++) {
            if (nums[i] < pivot) {
                j++;
                swap(nums, j, i);
            }
        }
        swap(nums, left, j);
        return j;
    }

    private static void swap(int[] nums, int index1, int index2) {
        int temp = nums[index1];
        nums[index1] = nums[index2];
        nums[index2] = temp;
    }

    private static int findMaxValue(int[] arr, int k) {
        if (arr.length < k) {
            throw new RuntimeException(">> not found");
        }

        int low = 0;
        int high = arr.length - 1;
        // 排好序应该所处的位置
        int tar = arr.length - k;
        while (true) {
            int partitionIndex = getPartitionIndex(arr, low, high);
            if (partitionIndex > tar) {
                high = partitionIndex - 1;
            } else if (partitionIndex < tar) {
                low = partitionIndex + 1;
            } else {
                return arr[partitionIndex];
            }
        }
    }

    private static int getPartitionIndex(int[] arr, int low, int high) {
        int pivot = arr[low];
        int left = low;
        int right = high;
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

        return emptyIndex;
    }

    private static void swapIndex(int[] arr, int from, int to) {
        int tmp = arr[from];
        arr[from] = arr[to];
        arr[to] = tmp;
    }
}
