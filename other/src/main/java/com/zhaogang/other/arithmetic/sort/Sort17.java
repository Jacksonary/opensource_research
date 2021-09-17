package com.zhaogang.other.arithmetic.sort;

import com.zhaogang.other.arithmetic.util.ArrUtils;

/**
 * @author jacks
 * @date 2021/8/11
 * @description
 */
public class Sort17 {

    private static final Integer[] arr = new Integer[] {4, 3, 6, 7, 1, 0, 9, 9, 8};

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
                ArrUtils.swap(arr, min, i);
            }
        }

        ArrUtils.printResult(arr);
    }

    private static void insert(Integer[] arr) {
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

    private static void shell(Integer[] arr) {
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
            while (arr[right] >= pivot && left < right) {
                right--;
            }
            if (left < right) {
                arr[emptyIndex] = arr[right];
                emptyIndex = right;
            }

            while (arr[left] <= pivot && left < right) {
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
