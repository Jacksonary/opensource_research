package com.hhu.bilibili.sort;

import com.hhu.bilibili.util.ArrUtils;

/**
 * @author jacks
 * @date 2022/6/15
 */
public class Sort31 {
    private static final int[] origin = new int[] {3, 2, 5, 9, 6, 1, 0, 1, 4, 8, 7};

    public static void main(String[] args) {
        bubble(ArrUtils.getNewArr(origin));
        select(ArrUtils.getNewArr(origin));
        insert(ArrUtils.getNewArr(origin));
        shell(ArrUtils.getNewArr(origin));

        merge(ArrUtils.getNewArr(origin), 0, origin.length - 1);
        fast(ArrUtils.getNewArr(origin), 0, origin.length - 1);
    }

    /**
     * T - O(n^2) S - O(1)
     * 
     * @param arr
     */
    private static void bubble(int[] arr) {
        for (int i = arr.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                }
            }
        }

        ArrUtils.printResult(arr);
    }

    private static void select(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int min = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[min]) {
                    min = j;
                }
            }
            swap(arr, i, min);
        }

        ArrUtils.printResult(arr);
    }

    private static void insert(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int tar = arr[i];
            int emptyIndex = i;
            while (emptyIndex > 0 && arr[emptyIndex - 1] > tar) {
                arr[emptyIndex--] = arr[emptyIndex];
            }
            arr[emptyIndex] = tar;
        }
        ArrUtils.printResult(arr);
    }

    private static void shell(int[] arr) {
        int h = 1;
        while (h < arr.length / 3) {
            h = h * 3 + 1;
        }

        while (h > 0) {
            for (int i = h; i < arr.length; i++) {
                int tar = arr[i];
                int emptyIndex = i;
                while (emptyIndex > h - 1 && arr[emptyIndex - 1] > tar) {
                    arr[emptyIndex--] = arr[emptyIndex];
                }
                arr[emptyIndex] = tar;
            }
            h = (h - 1) / 3;
        }

        ArrUtils.printResult(arr);
    }

    /**
     * T - O(n log n) | S - O(n)
     * 
     * @param arr
     * @param low
     * @param high
     */
    private static void merge(int[] arr, int low, int high) {
        if (low >= high) {
            return;
        }

        int mid = low + ((high - low) >> 1);
        // int mid = (high + low) / 2;
        merge(arr, low, mid);
        merge(arr, mid + 1, high);
        processMerge(arr, low, mid, high);
        ArrUtils.printResult(arr);
    }

    private static void processMerge(int[] arr, int low, int mid, int high) {
        int[] tmp = new int[high - low + 1];
        int tmpIndex = 0;

        int left = low;
        int right = mid + 1;

        while (left <= mid && right <= high) {
            tmp[tmpIndex++] = arr[arr[left] < arr[right] ? left++ : right++];
        }

        while (left <= mid) {
            tmp[tmpIndex++] = arr[left++];
        }

        while (right <= high) {
            tmp[tmpIndex++] = arr[right++];
        }

        System.arraycopy(tmp, 0, arr, low, tmpIndex);
    }

    private static void fast(int[] arr, int low, int high) {
        int left = low;
        int right = high;

        int emptyIndex = low;
        int pivot = arr[low];

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
            fast(arr, low, left - 1);
        }

        if (high - right > 1) {
            fast(arr, right + 1, high);
        }

        ArrUtils.printResult(arr);
    }

    private static void swap(int[] arr, int from, int to) {
        if (from == to) {
            // 保证内存中不是一个地址，否则异或为0
            return;
        }

        arr[from] = arr[from] ^ arr[to];
        arr[to] = arr[from] ^ arr[to];
        arr[from] = arr[from] ^ arr[to];
    }
}
