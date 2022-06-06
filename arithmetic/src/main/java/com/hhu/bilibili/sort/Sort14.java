package com.hhu.bilibili.sort;

import com.hhu.bilibili.util.ArrUtils;

/**
 * @author jacks
 * @date 2021/7/3
 * @description
 */
public class Sort14 {
    private static final Integer[] ARR = new Integer[] {3, 1, 6, 1, 5, 5, 8, 0, 9, 9, 4};

    public static void main(String[] args) {
        //        bubble();
        //        select();
        //        insert();
        //        shell();
        //        merge(arr, 0, arr.length - 1);
        fast(ARR, 0, ARR.length - 1);
        ArrUtils.printResult(ARR);
    }

    private static void bubble() {
        for (int i = ARR.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (ARR[j] > ARR[j + 1]) {
                    ArrUtils.swap(ARR, j, j + 1);
                }
            }
        }
        ArrUtils.printResult(ARR);
    }

    private static void select() {
        for (int i = 0; i < ARR.length; i++) {
            int min = i;
            for (int j = i + 1; j < ARR.length; j++) {
                if (ARR[j] < ARR[min]) {
                    min = j;
                }
            }
            if (min != i) {
                ArrUtils.swap(ARR, min, i);
            }
        }

        ArrUtils.printResult(ARR);
    }

    private static void insert() {
        for (int i = 1; i < ARR.length; i++) {
            int tarIndex = i;
            int tar = ARR[i];
            while (tarIndex > 0 && ARR[tarIndex - 1] > tar) {
                ARR[tarIndex] = ARR[--tarIndex];
            }

            ARR[tarIndex] = tar;
        }

        ArrUtils.printResult(ARR);
    }

    private static void shell() {
        int h = 1;
        while (h < ARR.length / 3) {
            h = h * 3 + 1;
        }

        while (h > 0) {
            for (int i = h; i < ARR.length; i++) {
                int tarIndex = i;
                int tar = ARR[i];
                while (tarIndex > h - 1 && ARR[tarIndex - 1] > tar) {
                    ARR[tarIndex] = ARR[--tarIndex];
                }
                ARR[tarIndex] = tar;
            }
            h = (h - 1) / 3;
        }

        ArrUtils.printResult(ARR);
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

        System.arraycopy(tmp, 0, arr, low, tmp.length);
    }

    private static void fast(Integer[] arr, int low, int high) {
        int emptyIndex = low;
        int pivot = arr[low];
        int left = low;
        int right = high;

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
