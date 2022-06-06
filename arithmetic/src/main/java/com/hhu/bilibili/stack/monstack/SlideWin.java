package com.hhu.bilibili.stack.monstack;

import com.hhu.bilibili.util.ArrUtils;

import java.util.LinkedList;

/**
 * @author jacks
 * @date 2021/6/29
 * @description 滑动窗口，每次滑动一格，求最大值数组
 * 输入：1, 3, -1, -3, 5, 3, 6, 7
 * 窗口：3
 * 输出：3, 3, 5, 5, 6, 7
 */
public class SlideWin {
    private static final int[] arr = new int[] {1, 3, -1, -3, 5, 3, 6, 7};

    public static void main(String[] args) {
        ArrUtils.printResult(getMaxWin(arr, 3));
    }

    private static int[] getMaxWin(int[] arr, int winSize) {
        if (winSize < 1 || arr == null || arr.length < winSize) {
            return null;
        }

        int[] res = new int[arr.length - winSize + 1];
        int index = 0;

        // 双端队列来放置窗口
        LinkedList<Integer> maxq = new LinkedList<>();
        for (int i = 0; i < arr.length; i++) {
            // 若新入元素大于队尾元素，则移除，那队尾元素肯定不会最大值
            while (!maxq.isEmpty() && arr[i] > arr[maxq.peekLast()]) {
                maxq.pollLast();
            }
            maxq.addLast(i);

            // 超出窗口范围，直接移除头部元素
            if (i - maxq.peekFirst() >= winSize) {
                maxq.pollFirst();
            }

            if (i >= winSize - 1) {
                res[index++] = arr[maxq.peekFirst()];
            }
        }

        return res;
    }

}
