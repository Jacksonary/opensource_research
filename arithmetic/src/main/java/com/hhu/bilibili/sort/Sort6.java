package com.hhu.bilibili.sort;

import com.hhu.bilibili.util.ArrUtils;

/**
 * @author weiguo.liu
 * @date 2021/4/29
 * @description
 */
public class Sort6 {
    private static Integer[] arr = new Integer[] {7, 9, 2, 3, 3, 3, 1, 0, 9, 2, 4, 3, 9, 8};

    public static void main(String[] args) {
        // bubble();
        // select();
        // insert();
        // shell();
        merge(0, arr.length - 1, arr);
        // fast(0, arr.length - 1, arr);
        ArrUtils.printResult(arr);
    }

    private static void bubble() {
        for (int i = arr.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {
                    ArrUtils.swap(arr, j, j + 1);
                }
            }
        }
        ArrUtils.printResult(arr);
    }

    private static void select() {
        for (int i = 0; i < arr.length; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }

            if (minIndex != i) {
                ArrUtils.swap(arr, minIndex, i);
            }
        }
        ArrUtils.printResult(arr);
    }

    private static void insert() {
        for (int i = 1; i < arr.length; i++) {
            int tarIndex = i;
            int tar = arr[i];
            while (tarIndex > 0 && arr[tarIndex - 1] > tar) {
                arr[tarIndex--] = arr[tarIndex];
            }
            arr[tarIndex] = tar;
        }

        ArrUtils.printResult(arr);
    }

    private static void shell() {
        int h = 1;
        while (h < arr.length / 3) {
            h = h * 3 + 1;
        }

        while (h > 0) {
            for (int i = h; i < arr.length; i++) {
                int tarIndex = i;
                int tar = arr[i];
                while (tarIndex > h - 1 && arr[tarIndex - 1] > tar) {
                    arr[tarIndex--] = arr[tarIndex];
                }
                arr[tarIndex] = tar;
            }
            h = (h - 1) / 3;
        }

        ArrUtils.printResult(arr);
    }

    /**
     * // FIXME: 2021/4/29
     */
    private static void merge(int low, int high, Integer[] arr) {
        int mid = (low + high) / 2;

        if (low < high) {
            merge(low, mid, arr);
            merge(mid + 1, high, arr);
            mergeP(low, mid, high, arr);
        }
    }

    private static void mergeP(int low, int mid, int high, Integer[] arr) {
        int left = low;
        int right = mid;
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

        System.arraycopy(tmp, 0, arr, low, tmpIndex + 1);
    }

    private static void fast(int low, int high, Integer[] arr) {
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
            fast(low, left - 1, arr);
        }

        if (high - right > 1) {
            fast(right + 1, high, arr);
        }
    }

    /**
     * left = 2 * i + 1;
     * right = 2 * i + 2;
     * root = (i - 1) / 2;
     */
    private static void heap(int[] arr) {

    }

}
