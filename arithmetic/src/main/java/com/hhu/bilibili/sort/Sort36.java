package com.hhu.bilibili.sort;

import com.hhu.bilibili.util.ArrUtils;

/**
 * @author jacks
 * @date 2022/7/8
 */
public class Sort36 {
    public static void main(String[] args) {
        int[] arr = new int[] {1, 4, 6, 8, 9, 0, 0, 5, 2, 7};
        bubble(ArrUtils.getNewArr(arr));
        select(ArrUtils.getNewArr(arr));
        insert(ArrUtils.getNewArr(arr));
        shell(ArrUtils.getNewArr(arr));
        merge(ArrUtils.getNewArr(arr), 0, arr.length - 1);
        fast(ArrUtils.getNewArr(arr), 0, arr.length - 1);

        heap(ArrUtils.getNewArr(arr));
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
            swap(arr, min, i);
        }

        ArrUtils.printResult(arr);
    }

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
            int mid = low + ((high - low) >> 1);
            merge(arr, low, mid);
            merge(arr, mid + 1, high);
            mergeP(arr, low, mid, high);
        }
    }

    private static void mergeP(int[] arr, int low, int mid, int high) {
        int[] tmp = new int[high - low + 1];
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

        System.arraycopy(tmp, 0, arr, low, tmpIndex);

        ArrUtils.printResult(arr);
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

    /**
     * left = 2 * root + 1; right = 2 * root + 2;
     *
     * @param arr
     */
    private static void heap(int[] arr) {
        // 获取最后一个节点的父节点，这个节点之前的节点应该都是父节点
        int root = (arr.length >> 1) - 1;

        int maxIndex = arr.length - 1;
        for (int i = root; i >= 0; i--) {
            heapify(arr, i, maxIndex);
        }

        for (int i = maxIndex; i > 0; i--) {
            swap(arr, 0, i);
            heapify(arr, 0, i - 1);
        }

        ArrUtils.printResult(arr);
    }

    private static void heapify(int[] arr, int root, int maxIndex) {
        // 构建大顶堆
        int left = (root << 1) + 1;
        int right = left + 1;

        if (left > maxIndex) {
            return;
        }

        // 默认给左节点最大
        int maxSon = left;
        if (right <= maxIndex && arr[right] > arr[left]) {
            maxSon = right;
        }

        // 右节点超出索引，直接比较左节点和根节点
        if (arr[maxSon] > arr[root]) {
            swap(arr, maxSon, root);
            heapify(arr, maxSon, maxIndex);
        }
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
