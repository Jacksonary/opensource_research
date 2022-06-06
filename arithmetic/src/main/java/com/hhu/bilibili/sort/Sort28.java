package com.hhu.bilibili.sort;

import java.util.PriorityQueue;

import com.hhu.bilibili.util.ArrUtils;

/**
 * @author jacks
 * @date 2021/12/9
 */
public class Sort28 {
    public static void main(String[] args) {
        int[] arr = new int[] {3, 2, 7, 1, 0, 8, 10, 2};
        // bubble(arr);
        // select(arr);
        // insert(arr);
        // shell(arr);

        merge(arr, 0, arr.length - 1);
        // fast(arr, 0, arr.length - 1);
        ArrUtils.printResult(arr);
    }

    /**
     * T-O(n^2) S-O(1) Stable
     */
    private static void bubble(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                }
            }
        }

        ArrUtils.printResult(arr);
    }

    /**
     * T-O(n^2) S-O(1) unstable
     */
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

    /**
     * T-O(n^2) S-O(1) stable
     */
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

    /**
     * T-O(n log n) S-O(1) unstable
     */
    private static void shell(int[] arr) {
        int h = 1;
        int len = arr.length / 3;
        while (h < len) {
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
     * T-O(n log n) S-O(n) stable
     */
    private static void merge(int[] arr, int low, int high) {
        if (low >= high) {
            return;
        }

        // int mid = (low + high) / 2;
        // 在存在边界值比较大的情况使用下面的方法取中点（广义上的中点，不是我们平常意义上的中点），可以避免溢出的情况，等价，移位运算
        // int mid = low + (high - low) / 2;
        int mid = low + ((high - low) >> 1);

        merge(arr, low, mid);
        merge(arr, mid + 1, high);
        mergeProcess(arr, low, mid, high);
    }

    private static void mergeProcess(int[] arr, int low, int mid, int high) {
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

        System.arraycopy(tmp, 0, arr, low, tmpIndex);
    }

    /**
     * T-O(n log n) S-O(log n) unstable
     */
    private static void fast(int[] arr, int low, int high) {
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

            while (left < right && arr[left] < pivot) {
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

    private static void dump(int[] arr) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int i : arr) {
            queue.add(i);
        }

    }

    private static void swap(int[] arr, int from, int to) {
        arr[from] = arr[from] ^ arr[to];
        arr[to] = arr[from] ^ arr[to];
        arr[from] = arr[from] ^ arr[to];
    }
}
