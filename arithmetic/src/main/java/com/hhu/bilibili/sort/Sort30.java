package com.hhu.bilibili.sort;

import com.hhu.bilibili.util.ArrUtils;

/**
 * @author jacks
 * @date 2022/6/9
 */
public class Sort30 {

    private static final int[] origin = new int[] {1, 3, 6, 4, 0, 9, 5};

    public static void main(String[] args) {
        ArrUtils.printResult("origin", origin);

        int[] bubbleArr = ArrUtils.getNewArr(origin);
        bubble(bubbleArr);
        ArrUtils.printResult("bubble", bubbleArr);

        int[] selectArr = ArrUtils.getNewArr(origin);
        select(selectArr);
        ArrUtils.printResult("select", selectArr);

        int[] insertArr = ArrUtils.getNewArr(origin);
        insert(insertArr);
        ArrUtils.printResult("insert", insertArr);

        int[] shellArr = ArrUtils.getNewArr(origin);
        shell(shellArr);
        ArrUtils.printResult("shell", shellArr);

        int[] mergeArr = ArrUtils.getNewArr(origin);
        merge(mergeArr, 0, mergeArr.length - 1);
        ArrUtils.printResult("merge", mergeArr);

        int[] fastArr = ArrUtils.getNewArr(origin);
        fast(fastArr, 0, fastArr.length - 1);
        ArrUtils.printResult("fast", fastArr);
    }

    /**
     * time - O(n^2)</br>
     * 
     * space - O(1)
     * 
     * @param arr
     */
    private static void bubble(int[] arr) {
        if (doNotSort(arr)) {
            return;
        }

        for (int i = arr.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                }
            }
        }
    }

    /**
     * time - O(n^2)</br>
     * space - O(1)
     * 
     * @param arr
     */
    private static void select(int[] arr) {
        if (doNotSort(arr)) {
            return;
        }

        for (int i = 0; i < arr.length; i++) {
            int min = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[min]) {
                    min = j;
                }
            }
            if (min != i) {
                swap(arr, i, min);
            }
        }
    }

    /**
     * time: O(n^2)</br>
     * space: O(1)
     * 
     * @param arr
     */
    private static void insert(int[] arr) {
        if (doNotSort(arr)) {
            return;
        }

        for (int i = 1; i < arr.length; i++) {
            int tar = arr[i];
            int target = i;
            while (target > 0 && arr[target - 1] > tar) {
                arr[target--] = arr[target];
            }
            arr[target] = tar;
        }
    }

    /**
     * time - O(n log n) </br>
     * space - O(1)
     */
    private static void shell(int[] arr) {
        if (doNotSort(arr)) {
            return;
        }

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
    }

    /**
     * time - O(n logn)</br>
     * space - O(n)
     * 
     * @param arr
     * @param left
     * @param right
     */
    private static void merge(int[] arr, int left, int right) {
        if (doNotSort(arr)) {
            return;
        }

        if (left < right) {
            // 这种求中点的方式可能存在溢出的风险
            // int mid = (left + right) / 2;
            // 这种中点肯定不会溢出，可以优化为位运算
            // int mid = left + (right - left) / 2;
            int mid = left + ((right - left) >> 1);

            merge(arr, left, mid);
            merge(arr, mid + 1, right);
            processMerge(arr, left, mid, right);
        }
    }

    private static void processMerge(int[] arr, int left, int mid, int right) {
        int tmpIndex = 0;
        int[] tmp = new int[right - left + 1];

        int leftIndex = left;
        int rightIndex = mid + 1;
        while (leftIndex <= mid && rightIndex <= right) {
            tmp[tmpIndex++] = arr[leftIndex] < arr[rightIndex] ? arr[leftIndex++] : arr[rightIndex++];
        }

        while (leftIndex <= mid) {
            tmp[tmpIndex++] = arr[leftIndex++];
        }

        while (rightIndex <= right) {
            tmp[tmpIndex++] = arr[rightIndex++];
        }

        // for (int i = 0; i < tmp.length; i++) {
        // arr[left + i] = tmp[i];
        // }
        System.arraycopy(tmp, 0, arr, left, tmpIndex);
    }

    /**
     * time - O(n log n)</br>
     * space - O(1)
     * @param arr
     * @param left
     * @param right
     */
    private static void fast(int[] arr, int left, int right) {
        int leftIndex = left;
        int rightIndex = right;
        int pivot = arr[left];
        int emptyIndex = left;

        while (leftIndex< rightIndex) {
            while (leftIndex < rightIndex && arr[rightIndex] >= pivot) {
                rightIndex--;
            }

            if (leftIndex < rightIndex) {
                arr[emptyIndex] = arr[rightIndex];
                emptyIndex = rightIndex;
            }

            while (leftIndex < rightIndex && arr[leftIndex] <= pivot) {
                leftIndex++;
            }

            if (leftIndex < rightIndex) {
                arr[emptyIndex] = arr[leftIndex];
                emptyIndex = leftIndex;
            }
        }

        arr[emptyIndex] = pivot;

        if (leftIndex - left > 1) {
            fast(arr, left, leftIndex - 1);
        }

        if (right - rightIndex > 1) {
            fast(arr, rightIndex + 1, right);
        }
    }

    private static boolean doNotSort(int[] arr) {
        return arr == null || arr.length < 2;
    }

    private static void swap(int[] arr, int from, int to) {
        if (from == to) {
            return;
        }

        int tmp = arr[from];
        arr[from] = arr[to];
        arr[to] = tmp;
    }
}
