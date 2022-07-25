package com.hhu.bilibili.sort;

import com.hhu.bilibili.util.ArrUtils;

/**
 * @author jacks
 * @date 2022/6/29
 */
public class Sort34 {
    public static void main(String[] args) {
        int[] arr = new int[] {4, 6, 1, 9, 0, 2, 1};

        bubble(ArrUtils.getNewArr(arr));
        select(ArrUtils.getNewArr(arr));
        insert(ArrUtils.getNewArr(arr));
        shell(ArrUtils.getNewArr(arr));
        merge(ArrUtils.getNewArr(arr), 0, arr.length - 1);
        fast(ArrUtils.getNewArr(arr), 0, arr.length - 1);
    }

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
            if (min != i) {
                swap(arr, min, i);
            }
        }

        ArrUtils.printResult(arr);
    }

    private static void insert(int[] arr) {
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

    private static void shell(int[] arr) {
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

    private static void merge(int[] arr, int low, int high) {
        if (low < high) {
            // int mid = low + ((high - low) >> 1);
            int mid = (low + high) / 2;
            merge(arr, low, mid);
            merge(arr, mid + 1, high);
            mergeP(arr, low, mid, high);
            ArrUtils.printResult(arr);
        }
    }

    private static void mergeP(int[] arr, int low, int mid, int high) {
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

    private static void fast(int[] arr, int low, int high) {
        int left = low;
        int right = high;

        int tarIndex = low;
        int pivot = arr[low];

        while (left < right) {
            while (left < right && arr[right] >= pivot) {
                right--;
            }

            if (left < right) {
                arr[tarIndex] = arr[right];
                tarIndex = right;
            }

            while (left < right && arr[left] <= pivot) {
                left++;
            }

            if (left < right) {
                arr[tarIndex] = arr[left];
                tarIndex = left;
            }
        }

        arr[tarIndex] = pivot;

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
            return;
        }

        arr[from] = arr[from] ^ arr[to];
        arr[to] = arr[from] ^ arr[to];
        arr[from] = arr[from] ^ arr[to];
    }
}
