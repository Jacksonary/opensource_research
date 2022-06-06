package com.hhu.bilibili.sort;

import com.hhu.bilibili.util.ArrUtils;

/**
 * @author jacks
 * @date 2021/9/6
 * @description
 */
public class Sort19 {
    private static final Integer[] arr = new Integer[] {1, 9, 0, 0, 5, 2, 1, 5, 7, 8, 8, 1, 3, 6, 6, 9, 4};

    public static void main(String[] args) {
        // bubble(arr);
        // select(arr);
        // insert(arr);
        // shell(arr);
        // merge(arr, 0, arr.length - 1);
        fast(arr, 0, arr.length - 1);
        ArrUtils.printResult(arr);
    }

    private static void bubble(Integer[] arr) {
        for (int i = arr.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {
                    ArrUtils.swap(arr, j, j + 1);
                }
            }
        }

        ArrUtils.printResult(arr);
    }

    private static void select(Integer[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int min = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[min]) {
                    min = j;
                }
            }
            if (min != i) {
                ArrUtils.swap(arr, i, min);
            }
        }
        ArrUtils.printResult(arr);
    }

    private static void insert(Integer[] arr) {
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

    private static void shell(Integer[] arr) {
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

    private static void merge(Integer[] arr, int low, int high) {
        if (low >= high) {
            return;
        }

        int mid = (low + high) / 2;
        merge(arr, low, mid);
        merge(arr, mid + 1, high);
        mergeP(arr, low, mid, high);
    }

    private static void mergeP(Integer[] arr, int low, int mid, int high) {
        int left = low;
        int right = mid + 1;
        Integer[] tmp = new Integer[high - low + 1];
        int tmpIndex = 0;

        while (left <= mid && right <= high) {
            tmp[tmpIndex++] = arr[left] < arr[right] ? arr[left++] : arr[right++];
        }

        while (left <= mid) {
            tmp[tmpIndex++] = arr[left++];
        }

        while (right <= high) {
            tmp[tmpIndex++] = arr[right++];
        }

        System.arraycopy(tmp, 0, arr, low, tmpIndex);
    }

    private static void fast(Integer[] arr, int low, int high) {
        int left = low;
        int right = high;
        int pivot = arr[low];
        int emptyIndex = low;

        while (left < right) {
            while (left < right && arr[right] >= pivot) {
                right--;
            }

            if (left <= right) {
                arr[emptyIndex] = arr[right];
                emptyIndex = right;
            }

            while (left < right && arr[left] <= pivot) {
                left++;
            }

            if (left <= right) {
                arr[emptyIndex] = arr[left];
                emptyIndex = left;
            }
        }

        // 左右哨兵相遇
        arr[emptyIndex] = pivot;

        if (left - low > 1) {
            fast(arr, low, left - 1);
        }

        if (high - right > 1) {
            fast(arr, right + 1, high);
        }
    }
}
