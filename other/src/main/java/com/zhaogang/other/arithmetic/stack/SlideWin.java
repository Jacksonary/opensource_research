package com.zhaogang.other.arithmetic.stack;

import com.zhaogang.other.arithmetic.util.ArrUtils;
import com.zhaogang.other.arithmetic.util.ScannerUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author jacks
 * @date 2021/6/24
 * @description
 * 有一个整型数组 arr 和一个大小为 w 的窗口从数组的最左边滑到最右边，窗口每次向右边
 * 滑一个位置。
 * @formatter:off
 * 例如，数组为[4,3,5,4,3,3,6,7]，窗口大小为 3 时：
 * [4 3 5] 4 3 3 6 7 -> 窗口中最大值为 5
 * 4 [3 5 4] 3 3 6 7 -> 窗口中最大值为 5
 * 4 3 [5 4 3] 3 6 7 -> 窗口中最大值为 5
 * 4 3 5 [4 3 3] 6 7 -> 窗口中最大值为 4
 * 4 3 5 4 [3 3 6] 7 -> 窗口中最大值为 6
 * 4 3 5 4 3 [3 6 7] -> 窗口中最大值为 7
 *
 * 如果数组长度为 n，窗口大小为 w，则一共产生 n-w+1 个窗口的最大值。
 * 请实现一个函数。
 *  输入：整型数组 arr，窗口大小为 w。
 *  输出：一个长度为 n-w+1 的数组 res，res[i]表示每一种窗口状态下的最大值。
 * 以本题为例，结果应该返回{5,5,5,4,6,7}。
 * @formatter:on
 */
public class SlideWin {
    public static void main(String[] args) {
        // 初始化数据
        Integer[] arr = ScannerUtil.getArr(Integer.class);
        Integer winSize = ScannerUtil.getSignValue(Integer.class);

        //        Integer[] winArr = getWinArr(arr, winSize);
        //        Integer[] winArr = getWinArr2(arr, winSize);
        int[] winArr = getMaxWin(arr, winSize);

        System.out.print(">> 窗口数组: ");
        ArrUtils.printResult(winArr);
    }

    /**
     * 利用单调栈处理，可以将复杂度降到到线性O(N)
     */
    private static Integer[] getWinArr2(Integer[] source, int winSize) {
        Integer[] res = new Integer[source.length];
        List<Integer> list = new ArrayList<>();

        LinkedList<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < winSize - 1; i++) {
            // 若元素大于顶部元素
            while (!linkedList.isEmpty() && source[i] > source[linkedList.peekLast()]) {
                linkedList.removeLast();
            }
            linkedList.addLast(i);
        }

        for (int i = winSize - 1; i < source.length; i++) {
            // 若进入元素大于对头元素，那对头元素肯定不会成为窗口最大元素，直接出队
            while (!linkedList.isEmpty() && source[i] > source[linkedList.getLast()]) {
                linkedList.removeLast();
            }
            linkedList.addLast(i);

            // 检测窗口有效元素
            if (i - linkedList.getFirst() + 1 > winSize) {
                linkedList.removeFirst();
            }

            list.add(source[linkedList.getFirst()]);
        }

        Integer[] integers = list.toArray(res);
        return integers;
        //        return list.toArray(Integer[] res);
    }

    /**
     * 最优解，原理同上，只是代码更加简洁
     */
    private static int[] getMaxWin(Integer[] source, int winSize) {
        if (source == null || winSize < 1 || source.length < winSize) {
            return null;
        }

        LinkedList<Integer> qmax = new LinkedList<>();
        // 窗口的数量
        int[] res = new int[source.length - winSize + 1];
        int index = 0;
        for (int i = 0; i < source.length; i++) {
            while (!qmax.isEmpty() && source[qmax.peekLast()] < source[i]) {
                qmax.pollLast();
            }
            qmax.addLast(i);

            // 过期元素，超出窗口大小
            if (i - qmax.peekFirst() == winSize) {
                qmax.pollFirst();
            }

            if (i >= winSize - 1) {
                res[index++] = source[qmax.peekFirst()];
            }
        }
        return res;
    }

    /**
     * 基础方式，复杂度 M*N
     */
    private static Integer[] getWinArr(Integer[] source, int winSize) {
        if (source.length < winSize) {
            return null;
        }

        List<Integer> collect = Arrays.asList(source);
        List<Integer> result = new ArrayList<>();
        // 包含这个startIndex，最后的数组
        for (int start = 0; start <= source.length - winSize; start++) {
            result.add(getMax(collect.subList(start, start + winSize)));
        }

        return result.toArray(new Integer[0]);
    }

    private static int getMax(List<Integer> arr) {
        int max = arr.get(0);
        for (Integer o : arr) {
            if (o > max) {
                max = o;
            }
        }
        return max;
    }
}
