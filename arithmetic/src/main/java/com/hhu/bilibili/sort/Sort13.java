package com.hhu.bilibili.sort;

import com.hhu.bilibili.util.ArrUtils;

/**
 * @author jacks
 * @date 2021/6/25
 * @description
 */
public class Sort13 {
    private static final Integer[] arr = new Integer[] {4, 5, 6, 1, 2, 7, 7, 0, 9};

    public static void main(String[] args) {
        //        bubble();
        //        select();
        //        insert();
        //        shell();
        //        merge(arr, 0, arr.length - 1);
        fast(arr, 0, arr.length - 1);
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
                ArrUtils.swap(arr, i, minIndex);
            }
        }

        ArrUtils.printResult(arr);
    }

    private static void insert() {
        for (int i = 1; i < arr.length; i++) {
            int targetIndex = i;
            int tar = arr[i];
            while (targetIndex > 0 && arr[targetIndex - 1] > tar) {
                arr[targetIndex--] = arr[targetIndex];
            }
            arr[targetIndex] = tar;
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
                int tar = arr[i];
                int tarIndex = i;
                while (tarIndex > h - 1 && arr[tarIndex - 1] > tar) {
                    arr[tarIndex--] = arr[tarIndex];
                }
                arr[tarIndex] = tar;
            }

            h = (h - 1) / 3;
        }

        ArrUtils.printResult(arr);
    }

    private static void merge(Integer[] arr, int low, int high) {
        int mid = (low + high) / 2;
        if (low < high) {
            merge(arr, low, mid);
            merge(arr, mid + 1, high);
            mergeP(arr, low, mid, high);
        }
    }

    private static void mergeP(Integer[] arr, int low, int mid, int high) {
        Integer[] tmp = new Integer[high - low + 1];
        int tmpIndex = 0;
        int left = low;
        int right = mid + 1;
        while (left <= mid && right <= high) {
            tmp[tmpIndex++] = arr[left] < arr[right] ? arr[left++] : arr[right++];
        }

        while (left <= mid) {
            tmp[tmpIndex++] = arr[left++];
        }

        while (right <= high) {
            tmp[tmpIndex++] = arr[right++];
        }

        System.arraycopy(tmp, 0, arr, low, tmp.length);
    }

    private static void fast(Integer[] arr, int low, int high) {
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
    }
}
