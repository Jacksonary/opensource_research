package com.hhu.bilibili.sort;

import com.hhu.bilibili.util.ArrUtils;

/**
 * @author weiguo.liu
 * @date 2021/4/26
 * @description
 */
public class Sort5 {
    private static Integer[] arr = new Integer[] {7, 4, 6, 2, 0, 9, 3, 3, 1, 5, 2};

    public static void main(String[] args) {
        // bubble();
        // select();
        // insert();
        // shell();
        // merge(0, arr.length - 1, arr);
        // fast(0, arr.length - 1, arr);
        // ArrUtils.printResult(arr);

        heap(arr);
    }

    /**
     * T-O(n^2) S-O(1)
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

    private static void select() {
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            for (int j = i; j < arr.length; j++) {
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
            int tar = arr[i];
            int tarIndex = i;
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

    private static void merge(int low, int high, Integer[] arr) {
        int mid = (low + high) / 2;

        if (low < high) {
            merge(low, mid, arr);
            merge(mid + 1, high, arr);
            mergeProcess(low, mid, high, arr);
        }
    }

    private static void mergeProcess(int low, int mid, int high, Integer[] arr) {
        int left = low;
        int right = mid + 1;
        Integer[] tmp = new Integer[high - low + 1];
        int tarIndex = 0;

        while (left <= mid && right <= high) {
            tmp[tarIndex++] = arr[left] < arr[right] ? arr[left++] : arr[right++];
        }

        while (left <= mid) {
            tmp[tarIndex++] = arr[left++];
        }

        while (right <= high) {
            tmp[tarIndex++] = arr[right++];
        }

        System.arraycopy(tmp, 0, arr, low, tmp.length);
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

    private static void heap(Integer[] arr) {
        int maxIndex = arr.length - 1;
        int root = maxIndex - 1 >> 1;
        for (int i = root; i >= 0; i--) {
            heapify(arr, i, maxIndex);
        }

        for (int i = maxIndex; i > 0; i--) {
            swap(arr, 0, i);
            heapify(arr, 0, i - 1);
        }

        ArrUtils.printResult(arr);
    }

    private static void heapify(Integer[] arr, int root, int maxIndex) {
        int left = (root << 1) + 1;
        if (left > maxIndex) {
            return;
        }

        int maxSon = left;
        int right = left + 1;
        if (arr[left] < arr[right] && right <= maxIndex) {
            maxSon = right;
        }

        if (arr[maxSon] > arr[root]) {
            swap(arr, maxSon, root);
            heapify(arr, maxSon, maxIndex);
        }
    }

    private static void swap(Integer[] arr, int from, int to) {
        if (from == to) {
            return;
        }

        arr[from] = arr[from] ^ arr[to];
        arr[to] = arr[from] ^ arr[to];
        arr[from] = arr[from] ^ arr[to];
    }
}
