package com.hhu.bilibili.sort;

import java.util.PriorityQueue;
import java.util.Queue;

import com.hhu.bilibili.util.ArrUtils;

/**
 * @author jacks
 * @date 2021/11/8
 * @description
 */
public class Sort24 {
    public static void main(String[] args) {
        // 排序: 0 1 2 3 4 4 8 10
        int[] arr = new int[] {8, 2, 10, 0, 4, 1, 3, 4};

        // bubble(arr);
        // select(arr);
        // insert(arr);
        // shell(arr);
        // merge(arr, 0, arr.length - 1);
        // fast(arr, 0, arr.length - 1);
        // ArrUtils.printResult(arr);

        // topK(arr, 2);
        // topKMin(arr, 3);
        // topKMax(arr, 2);
        System.out.println(getTopKIndex(arr, 2));
    }

    private static void bubble(int[] arr) {
        if (arr == null) {
            return;
        }

        for (int i = arr.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
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
                int tmp = arr[min];
                arr[min] = arr[i];
                arr[i] = tmp;
            }
        }

        ArrUtils.printResult(arr);
    }

    private static void insert(int[] arr) {
        if (arr == null) {
            return;
        }

        for (int i = 1; i < arr.length; i++) {
            int tarIndex = i;
            int tar = arr[i];
            while (tarIndex > 0 && arr[tarIndex - 1] >= tar) {
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
                while (tarIndex > h - 1 && arr[tarIndex - 1] >= tar) {
                    arr[tarIndex--] = arr[tarIndex];
                }
                arr[tarIndex] = tar;
            }

            h = (h - 1) / 3;
        }

        ArrUtils.printResult(arr);
    }

    private static void merge(int[] arr, int low, int high) {
        if (low >= high) {
            return;
        }

        int mid = (low + high) / 2;
        merge(arr, low, mid);
        merge(arr, mid + 1, high);
        mergeP(arr, low, mid, high);
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
    }

    private static void fast(int[] arr, int low, int high) {
        if (arr == null) {
            return;
        }

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

    private static void topK(int[] arr, int k) {
        Queue<Integer> smallStack = new PriorityQueue<>(k, (o1, o2) -> {
            // 小顶堆
            // return o1.compareTo(o2);
            // 大顶堆
            return o2.compareTo(o1);
        });

        for (int i = 0; i < arr.length; i++) {
            if (smallStack.size() < k) {
                smallStack.add(i);
                continue;
            }

            Integer peek = smallStack.peek();
            if (arr[i] < peek) {
                smallStack.poll();
                smallStack.offer(arr[i]);
            }
        }

        System.out.print("topK: ");
        while (!smallStack.isEmpty()) {
            System.out.print(smallStack.poll() + " ");
        }
    }

    // 寻找前k个最小的数--使用大顶堆
    public static void topKMin(int[] nums, int k) {
        // 将大顶堆大小定义为k，并重写类函数
        PriorityQueue<Integer> pq = new PriorityQueue<>(k, (a, b) -> {
            // 大顶堆：参数2-参数1；小顶堆：参数1-参数2
            return b - a;
        });
        for (int i = 0; i < nums.length; i++) {
            if (i < k) {
                pq.offer(nums[i]); // 前k个数，直接入堆
                continue;
            } else if (nums[i] < pq.peek()) { // 如果当前元素比堆顶元素小
                pq.poll(); // 说明堆顶元素（堆中最大元素）一定不属于前k小的数，出堆
                pq.offer(nums[i]); // 当前元素有可能属于前k小，入堆
            }
        }

        while (!pq.isEmpty()) {
            System.out.print(pq.poll() + " ");
        }
    }

    /**
     * error，小顶堆，大于堆顶元素则替换并调整
     */
    private static void topKMax(int[] nums, int k) {
        if (nums == null) {
            return;
        }

        // 构造小顶堆
        Queue<Integer> bigStack = new PriorityQueue<>(k, (a, b) -> a - b);
        for (int i = 0; i < nums.length; i++) {
            if (bigStack.size() < k) {
                bigStack.offer(nums[i]);
                continue;
            }
            if (bigStack.peek() >= nums[i]) {
                continue;
            }
            bigStack.poll();
            bigStack.offer(nums[i]);
        }
        while (!bigStack.isEmpty()) {
            System.out.print(bigStack.poll() + " ");
        }
    }

    public static int getTopKIndex(int[] arr, int k) {
        if (arr == null || k <= 0 || arr.length < k) {
            return -1;
        }

        Queue<Integer> queue = new PriorityQueue<>((a, b) -> a - b);
        for (int i = 0; i < arr.length; i++) {
            if (queue.size() < k) {
                queue.add(i);
                continue;
            }

            if (arr[queue.peek()] >= arr[i]) {
                continue;
            }

            queue.poll();
            queue.add(i);
        }

        return arr[queue.peek()];
    }
}
