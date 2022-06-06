package com.hhu.bilibili.zuo;

import java.util.Random;

import com.hhu.bilibili.util.ArrUtils;

/**
 * @author jacks
 * @date 2022/4/26
 */
public class Sort {

    public static void main(String[] args) throws InterruptedException {
        int[] arr = new int[10];
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            arr[i] = random.nextInt(10);
        }

        // StopWatch watch = new StopWatch("custom watcher");
        // watch.start("selectSort");
        // selectSort(arr);
        // watch.stop();
        // watch.start("selectSort2");
        // selectSort2(arr);
        // watch.stop();
        // System.out.println(watch.prettyPrint());

        ArrUtils.printResult(arr);
        bubble(arr);
        ArrUtils.printResult(arr);
    }

    private static void selectSort(int[] arr) throws InterruptedException {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[i]) {
                    swap(arr, i, j);
                }
            }
        }
    }

    /**
     * 优化选择方式，每次只记录一个变量的index，遍历完再进行交互 效率更好
     */
    private static void selectSort2(int[] arr) throws InterruptedException {
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

    private static void bubble(int[] arr) {
        for (int i = arr.length; i > 0; i--) {
            for (int j = 0; j < i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                }
            }
        }
    }

    private static void swap(int[] arr, int from, int to) {
        int tmp = arr[from];
        arr[from] = arr[to];
        arr[to] = tmp;
    }

    /**
     * 使用异或进行交换
     * 注: o^n = n
     * 用异或进行交换的前提必须2个数不是同一块内存地址(即 from != to)，否则会异或成 0
     */
    private static void swap2(int[] arr, int from, int to) {
        arr[from] = arr[from] ^ arr[to];
        arr[to] = arr[from] ^ arr[to];
        arr[from] = arr[from] ^ arr[to];
    }
}
