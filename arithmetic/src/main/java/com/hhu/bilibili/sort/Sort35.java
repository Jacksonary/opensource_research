package com.hhu.bilibili.sort;

import com.hhu.bilibili.util.ArrUtils;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author jacks
 * @date 2022/7/5
 */
public class Sort35 {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        int[] arr = new int[] {4, 6, 7, 9, 0, 1, 1, 2, 3, 8, 5};

        bubble(ArrUtils.getNewArr(arr));
        select(ArrUtils.getNewArr(arr));
        insert(ArrUtils.getNewArr(arr));
        shell(ArrUtils.getNewArr(arr));
        merge(ArrUtils.getNewArr(arr), 0, arr.length - 1);
        fast(ArrUtils.getNewArr(arr), 0, arr.length - 1);

        heap(ArrUtils.getNewArr(arr));
    }

    private static void bubble(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

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
                swap(arr, i, min);
            }
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
        int step = 1;
        while (step < arr.length / 3) {
            step = step * 3 + 1;
        }

        while (step > 0) {
            for (int i = step; i < arr.length; i++) {
                int tar = arr[i];
                int tarIndex = i;
                while (tarIndex > step - 1 && arr[tarIndex - 1] > tar) {
                    arr[tarIndex--] = arr[tarIndex];
                }
                arr[tarIndex] = tar;
            }
            step = (step - 1) / 3;
        }

        ArrUtils.printResult(arr);
    }

    private static void merge(int[] arr, int low, int high) {
        if (low < high) {
            int mid = low + ((high - low) >> 2);
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
     * @formatter:off
     * 堆的特性:
     * 最大堆调整(Max Heapify): 将堆的末端子节点作调整，使得子节点永远小于父节点
     * 建立最大堆(Build Max Heap): 将堆中的所有数据重新排序
     *
     * 通常堆是通过一维数组来实现的。在阵列起始位置为0的情形中(重要特性)：
     * 父节点i的左子节点在位置 (2i + 1);
     * 父节点i的右子节点在位置 (2i + 2);
     * 子节点i的父节点在位置 (i − 1) / 2;
     *
     * T - O(n log n) | S - O(1)
     * @formatter:on 
     */
    private static void heap(int[] arr) {
        int len = arr.length - 1;
        int root = (arr.length >> 1) - 1;

        // 1. 进行堆化，使其成为一个大顶堆
        for (int i = root; i >= 0; i--) {
            maxHeapify(arr, i, len);
        }

        // 2. 对堆化数据排序
        // 每次都是移出最顶层的根节点A[0]，与最尾部节点位置调换，同时遍历长度 - 1。
        // 然后从新整理被换到根节点的末尾元素，使其符合堆的特性。
        // 直至未排序的堆长度为 0。
        for (int i = len; i > 0; i--) {
            swap(arr, 0, i);
            maxHeapify(arr, 0, i - 1);
        }

        ArrUtils.printResult(arr);
    }

    /**
     * 调整索引为 tarIndex 处的数据，使其符合堆的特性。
     */
    private static void maxHeapify(int[] arr, int tarIndex, int len) {
        int left = (tarIndex << 1) + 1;
        int right = left + 1;

        if (left > len) {
            return;
        }

        // 默认给左子节点为最大
        int maxSon = left;
        // 先判断左右子节点，哪个较大
        if (right <= len && arr[right] > arr[left]) {
            maxSon = right;
        }
        // 如果父节点比子节点小调换，最终变成大顶堆
        if (arr[maxSon] > arr[tarIndex]) {
            swap(arr, maxSon, tarIndex);
            maxHeapify(arr, maxSon, len);
        }
    }

    private static int[] count(int[] arr) {
        int[] tmp = new int[arr.length];

        int k = 100;
        countSort(arr, tmp, k);
        return tmp;
    }

    private static void countSort(int[] arr, int[] tmp, int k) {
        int[] c = new int[k];

        // 计数
        for (int i = 0; i < arr.length; i++) {
            int a = arr[i];
            c[a] += 1;
        }

        ArrUtils.printResult(c);

        // 计数求和
        for (int i = 1; i < k; i++) {
            c[i] = c[i] + c[i-1];
        }
        ArrUtils.printResult(c);

        // 整理
        for (int i = arr.length-1; i >= 0; i--) {
            int a = arr[i];
            tmp[c[a] - 1] = a;
            c[a] -= 1;
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
