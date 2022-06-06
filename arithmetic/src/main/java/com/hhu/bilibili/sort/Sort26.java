package com.hhu.bilibili.sort;

import com.hhu.bilibili.util.ArrUtils;

/**
 * @author jacks
 * @date 2021/11/22
 */
public class Sort26 {
    public static void main(String[] args) {
        int[] arr = new int[] {3, 2, 6, 7, 7, 1, 9, 0};
        //        bubble(arr);
        //        select(arr);
        //        insert(arr);
        //        shell(arr);
        //        merge(0, arr.length - 1, arr);
        fast(0, arr.length - 1, arr);
        ArrUtils.printResult(arr);
    }

    private static void bubble(int[] arr) {
        for (int i = arr.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
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
            if (min != i) {
                int tmp = arr[i];
                arr[i] = arr[min];
                arr[min] = tmp;
            }
        }

        ArrUtils.printResult(arr);
    }

    private static void insert(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int empty = i;
            int tar = arr[i];
            while (empty > 0 && arr[empty - 1] > tar) {
                arr[empty--] = arr[empty];
            }
            arr[empty] = tar;
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
                int empty = i;
                int tar = arr[i];
                while (empty > h - 1 && arr[empty - 1] > tar) {
                    arr[empty--] = arr[empty];
                }
                arr[empty] = tar;
            }
            h = (h - 1) / 3;
        }
        ArrUtils.printResult(arr);
    }

    private static void merge(int low, int high, int[] arr) {
        if (low < high) {
            int mid = (low + high) / 2;
            merge(low, mid, arr);
            merge(mid + 1, high, arr);
            mergeP(low, mid, high, arr);
        }
    }

    private static void mergeP(int low, int mid, int high, int[] arr) {
        int left = low;
        int right = mid + 1;
        int tmpIndex = 0;
        int[] tmp = new int[high - low + 1];

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

    private static void fast(int low, int high, int[] arr) {
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
            fast(low, left - 1, arr);
        }

        if (high - right > 1) {
            fast(right + 1, high, arr);
        }
    }
}
