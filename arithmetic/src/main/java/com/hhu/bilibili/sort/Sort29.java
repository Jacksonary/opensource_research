package com.hhu.bilibili.sort;

import com.hhu.bilibili.util.ArrUtils;

/**
 * @author jacks
 * @date 2021/12/14
 */
public class Sort29 {

    public static void main(String[] args) {
        int[] arr = new int[] {1, 5, 3, 7, 0, 9, 2, 3, 2};

        bubble(ArrUtils.getNewArr(arr));
        select(ArrUtils.getNewArr(arr));
        insert(ArrUtils.getNewArr(arr));
        shell(ArrUtils.getNewArr(arr));
        merge(ArrUtils.getNewArr(arr), 0, arr.length - 1);
        fast(ArrUtils.getNewArr(arr), 0, arr.length - 1);
    }

    /**
     * T - O(n^2) </br>
     * S - O(1)
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

    /**
     * T - O(n^2)</br>
     * S - O(1)
     * 
     * @param arr
     */
    private static void select(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int min = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[min] > arr[j]) {
                    min = j;
                }
            }

            if (min != i) {
                swap(arr, min, i);
            }
        }

        ArrUtils.printResult(arr);
    }

    /**
     * T - O(n^2)</br>
     * S - O(1)
     * 
     * @param arr
     */
    private static void insert(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int tar = arr[i];
            int tarIndex = i;
            while (tarIndex > 0 && arr[tarIndex - 1] > tar) {
                arr[tarIndex--] = arr[tarIndex];
            }
            arr[tarIndex] = tar;
        }

        ArrUtils.printResult(arr);
    }

    /**
     * T - O(nlogn)</br>
     * S - O(1)
     * 
     * @param arr
     */
    private static void shell(int[] arr) {
        int h = 1;
        // 经验公式
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
     * T - O(n log n)</br>
     * S - O(n)
     * 
     * @param arr
     * @param low
     * @param high
     */
    private static void merge(int[] arr, int low, int high) {
        if (low >= high) {
            return;
        }

        int mid = (low + high) / 2;
        merge(arr, low, mid);
        merge(arr, mid + 1, high);
        processMerge(arr, low, mid, high);
        ArrUtils.printResult(arr);
    }

    private static void processMerge(int[] arr, int low, int mid, int high) {
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
        if (arr == null || arr.length < 2 || from == to) {
            return;
        }

        arr[from] = arr[from] ^ arr[to];
        arr[to] = arr[from] ^ arr[to];
        arr[from] = arr[from] ^ arr[to];
    }
}
