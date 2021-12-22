package com.zhaogang.other.arithmetic.leetcode;

import org.apache.commons.lang3.time.StopWatch;

/**
 * @author jacks
 * @date 2021/11/15
 * @description 1 1 2 3 5 8
 */
public class Feili {
    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        System.out.println(get(20));
        System.out.println("get: " + stopWatch.getNanoTime());

        stopWatch.reset();
        stopWatch.start();
        System.out.println(getWithDP(100));
        System.out.println("getWithDP: " + stopWatch.getNanoTime());
    }

    private static int get(int index) {
        return index == 0 || index == 1 ? 1 : get(index - 1) + get(index - 2);
    }

    private static int getWithDP(int index) {
        if (index == 0 || index == 1) {
            return 1;
        }

        int[] arr = new int[index];
        for (int i = 0; i < index; i++) {
            if (i == 0 || i == 1) {
                arr[i] = 1;
                continue;
            }
            arr[i] = arr[i - 1] + arr[i - 2];
        }

        return arr[index - 1] + arr[index - 2];
    }
}
