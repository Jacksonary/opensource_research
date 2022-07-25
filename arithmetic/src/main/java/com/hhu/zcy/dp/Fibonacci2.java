package com.hhu.zcy.dp;

/**
 * 1 1 2 3 5 8 13
 * 
 * @author jacks
 * @date 2022/6/26
 */
public class Fibonacci2 {

    public static void main(String[] args) {
        System.out.println(getN(6));
        System.out.println(getN2(6));
    }

    private static int getN(int n) {
        if (n == 1 || n == 2) {
            return 1;
        }

        return getN(n - 1) + getN(n - 2);
    }

    private static int getN2(int n) {
        if (n == 1 || n == 2) {
            return 1;
        }

        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            res[i] = i < 2 ? 1 : (res[i - 1] + res[i - 2]);
        }

        // n - 1
        return res[n - 2] + res[n - 3];
    }
}
