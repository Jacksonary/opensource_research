package com.zhaogang.other.arithmetic;

/**
 * @author weiguo.liu
 * @date 2021/4/22
 * @description
 */
public class ArrUtils {
    public static void swap(int[] arr, int from, int to) {
        int tmp = arr[from];
        arr[from] = arr[to];
        arr[to] = tmp;
    }

    public static void printResult(int[] arr) {
        System.out.print(">> " + Thread.currentThread().getStackTrace()[2].getMethodName() + " result: ");
        for (int i : arr) {
            System.out.print(i + " ");
        }
    }
}
