package com.zhaogang.other.arithmetic.sort;

/**
 * @author weiguo.liu
 * @date 2021/4/21
 * @description
 */
public class Sort2 {
    private static int[] arr = new int[] {3, 2, 5, 1, 8, 8, 6, 0, 0};

    private static void swap(int from, int to) {
        int tmp = arr[from];
        arr[from] = arr[to];
        arr[to] = tmp;
    }

    private static void printResult() {
        System.out.println(">> " + Thread.currentThread().getStackTrace()[2].getMethodName() + " result: ");
        for (int i : arr) {
            System.out.print(i + " ");
        }
    }

    public static void main(String[] args) {
        // bubble();
        // select();
        // insert();
        // shell();

        merge(arr, 0, arr.length - 1);
        printResult();

        // fast(arr, 0, arr.length - 1);
        // printResult();
    }

    /**
     * S - O(1) T - O(N^2) S
     */
    private static void bubble() {
        for (int i = arr.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                // 这里不包含 =，这种排序方式是稳定的
                if (arr[j] > arr[j + 1]) {
                    swap(j, j + 1);
                }
            }
        }
        printResult();
    }

    /**
     * S O(1) T O(n^2) Y
     */
    private static void select() {
        for (int i = 0; i < arr.length; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }

            if (minIndex != i) {
                swap(i, minIndex);
            }
        }

        printResult();
    }

    /**
     * T - O(logn) S - O(1) NS
     */
    private static void insert() {
        for (int i = 1; i < arr.length; i++) {
            int tar = arr[i];
            int tarIndex = i;
            while (tarIndex > 0 && arr[tarIndex - 1] >= tar) {
                arr[tarIndex] = arr[--tarIndex];
            }
            arr[tarIndex] = tar;
        }

        printResult();
    }

    private static void shell() {
        int h = 1;
        while (h < arr.length / 3) {
            h = h * 3 + 1;
        }

        while (h > 0) {
            for (int i = h + 1; i < arr.length; i++) {
                int tarIndex = i;
                int tar = arr[i];
                while (tarIndex > h - 1 && arr[tarIndex - 1] >= tar) {
                    arr[tarIndex] = arr[--tarIndex];
                }
                arr[tarIndex] = tar;
            }
            h = (h - 1) / 3;
        }

        printResult();
    }

    private static void merge(int[] arr, int low, int high) {
        int mid = (low + high) / 2;

        if (low < high) {
            merge(arr, low, mid);
            merge(arr, mid + 1, high);
            mergeProcess(arr, low, mid, high);
        }
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

        System.arraycopy(tmp, 0, arr, low, tmp.length);
    }

    private static void fast(int[] arr, int low, int high) {
        int left = low;
        int right = high;
        int pivot = arr[low];
        int tarIndex = low;

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
    }
}
