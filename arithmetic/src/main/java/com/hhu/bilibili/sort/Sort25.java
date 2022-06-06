package com.hhu.bilibili.sort;

import com.hhu.bilibili.util.ArrUtils;

/**
 * @author jacks
 * @date 2021/11/15
 * @description
 */
public class Sort25 {
    public static void main(String[] args) {
        int[] arr = new int[] {5, 7, 1, 1, 0, 3, 4, 2};
        //        bubble(arr);
        //        select(arr);
        //        insert(arr);
        //        shell(arr);
        //        merge(arr, 0, arr.length - 1);
        fast(arr, 0, arr.length - 1);
        ArrUtils.printResult(arr);
    }

    private static void bubble(int[] arr) {
        if (arr == null) {
            return;
        }

        for (int i = arr.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
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
                int temp = arr[min];
                arr[min] = arr[i];
                arr[i] = temp;
            }
        }

        ArrUtils.printResult(arr);
    }

    private static void insert(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int tarI = i;
            int tar = arr[i];
            while (tarI > 0 && arr[tarI - 1] > tar) {
                arr[tarI--] = arr[tarI];
            }
            arr[tarI] = tar;
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
                int tari = i;
                int tar = arr[i];

                while (tari > h - 1 && arr[tari - 1] > tar) {
                    arr[tari--] = arr[tari];
                }
                arr[tari] = tar;
            }
            h = (h - 1) / 3;
        }

        ArrUtils.printResult(arr);
    }

    private static void merge(int[] arr, int low, int high) {
        if (low < high) {
            int mid = (low + high) / 2;
            merge(arr, low, mid);
            merge(arr, mid + 1, high);
            mergeP(arr, low, mid, high);
        }
    }

    private static void mergeP(int[] arr, int low, int mid, int high) {
        int tempi = 0;
        int[] temp = new int[high - low + 1];
        int left = low;
        int right = mid + 1;

        while (left <= mid && right <= high) {
            temp[tempi++] = arr[left] < arr[right] ? arr[left++] : arr[right++];
        }

        while (left <= mid) {
            temp[tempi++] = arr[left++];
        }

        while (right <= high) {
            temp[tempi++] = arr[right++];
        }

        System.arraycopy(temp, 0, arr, low, tempi);
    }

    private static void fast(int[] arr, int low, int high) {
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
                arr[emptyIndex] = arr[left];
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
