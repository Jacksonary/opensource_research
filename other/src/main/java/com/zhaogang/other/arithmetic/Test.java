package com.zhaogang.other.arithmetic;

import java.util.Scanner;

/**
 * @author jacks
 * @date 2021/9/23
 * @description
 */
public class Test {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            String s = scanner.nextLine();
            if (s == null || s.length() == 0) {
                System.out.println(s);
            }
            String[] arr = s.split(" ");
            if (arr.length == 0 || arr.length == 1) {
                System.out.println(s);
                return;
            }

            printWithBubble(arr);
        }
    }

    private static void printWithBubble(String[] arr) {
        for (int i = arr.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (bigger(arr[j], arr[j + 1])) {
                    String tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                }
            }
        }

        for (String s : arr) {
            System.out.print(s + " ");
        }
    }

    private static boolean bigger(String var1, String var2) {
        int len1 = var1.length();
        int len2 = var2.length();
        int i = 0;
        for (; i < len1 && i < len2; i++) {
            if (var1.charAt(i) > var2.charAt(i)) {
                return true;
            } else if (var1.charAt(i) < var2.charAt(i)) {
                return false;
            }
        }

        if (len1 == len2) {
            return false;
        }

        return len1 > len2;
    }
}
