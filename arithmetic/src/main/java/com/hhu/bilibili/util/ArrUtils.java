package com.hhu.bilibili.util;

import java.util.Random;

/**
 * @author weiguo.liu
 * @date 2021/4/22
 * @description
 */
public class ArrUtils {
    public static void main(String[] args) {
        int[][] arr = new int[5][4];

        Random random = new Random(6);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                arr[i][j] = random.nextInt(10);
            }
        }
        printResult(arr);
    }

    public static <T> void swap(T[] arr, int from, int to) {
        T tmp = arr[from];
        arr[from] = arr[to];
        arr[to] = tmp;
    }

    public static void printResult(int[] arr) {
        System.out.print(">> " + Thread.currentThread().getStackTrace()[2].getMethodName() + ", result: ");
        for (int i : arr) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public static void printResult(String prefix, int[] arr) {
        System.out.print(">> " + prefix + ": ");
        for (int i : arr) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public static void printResult(Integer[] arr) {
        System.out.print(">> " + Thread.currentThread().getStackTrace()[2].getMethodName() + " result: ");
        if (arr == null) {
            System.out.println("null");
            return;
        }

        for (Integer i : arr) {
            System.out.print(i + " ");
        }
    }

    public static void printResult(int[][] arr) {
        System.out.println(">> " + Thread.currentThread().getStackTrace()[2].getMethodName() + ", result: ");
        if (arr == null) {
            System.out.println("null");
            return;
        }

        for (int i = 0; i < arr.length; i++) {
            System.out.print("{");
            for (int j = 0; j < arr[i].length; j++) {
                System.out.print(arr[i][j]);
                if (j != arr[i].length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("}");
        }
    }

    public static int[] getNewArr(int[] arr) {
        if (arr == null) {
            return null;
        }

        int[] result = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = arr[i];
        }

        return result;
    }
}
