package com.zhaogang.other.arithmetic;

/**
 * @author weiguo.liu
 * @date 2021/4/22
 * @description
 */
public class Sort3 {
    private static int[] arr = new int[] {3, 4, 5, 9, 0, 7, 6, 7, 1, 1, 2, 8};

    public static void main(String[] args) {
        // bubble();
        // select();
        // insert();
        // shell();

        // merge(0, arr.length - 1, arr);
        fast(0, arr.length - 1, arr);
        ArrUtils.printResult(arr);
    }

    /**
     * @formatter:off 
     * S - O(1) 
     * T- O(n^2)
     * S
     * @formatter:on 
     */
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

    /**
     * @formatter:off
     * T-O(n^2)
     * S-O(1)
     * NS
     * @formatter:on 
     */
    private static void select() {
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[minIndex] > arr[j]) {
                    minIndex = j;
                }
            }

            if (minIndex != i) {
                ArrUtils.swap(arr, minIndex, i);
            }
        }

        ArrUtils.printResult(arr);
    }

    /**
     * @formatter:off 
     * T- O(n^2)
     * S-O(1)
     * S
     * @formatter:on 
     */
    private static void insert() {
        for (int i = 1; i < arr.length; i++) {
            int tarIndex = i;
            int tar = arr[i];
            while (tarIndex > 0 && arr[tarIndex - 1] >= tar) {
                arr[tarIndex] = arr[--tarIndex];
            }

            arr[tarIndex] = tar;
        }

        ArrUtils.printResult(arr);
    }

    /**
     * @formatter:off 
     * T-O(log n)
     * S - O(1)
     * ns
     * @formatter:on 
     */
    private static void shell() {
        int h = 1;
        while (h < arr.length / 3) {
            h = h * 3 + 1;
        }

        while (h > 0) {
            for (int i = h; i < arr.length; i++) {
                int tarIndex = i;
                int tar = arr[i];
                while (tarIndex > 0 && arr[tarIndex - 1] >= tar) {
                    arr[tarIndex] = arr[--tarIndex];
                }
                arr[tarIndex] = tar;
            }
            h = (h - 1) / 3;
        }

        ArrUtils.printResult(arr);
    }

    /**
     * @formatter:off
     * T-O(log n)
     * S-O(n)
     *
     * NS
     * @formatter:on
     * @param low
     * @param high
     * @param arr
     */
    private static void merge(int low, int high, int[] arr) {
        int mid = (low + high) / 2;

        if (low < high) {
            merge(low, mid, arr);
            merge(mid + 1, high, arr);
            mergePro(low, mid, high, arr);
        }
    }

    private static void mergePro(int low, int mid, int high, int[] arr) {
        int left = low;
        int right = mid + 1;
        int[] tmp = new int[high - low + 1];
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

    /**
     * @formatter:off 
     * T-O(nlon)
     * S-O(1)
     * ns
     * @formatter:on 
     * @param low
     * @param high
     * @param arr
     */
    private static void fast(int low, int high, int[] arr) {
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
}
