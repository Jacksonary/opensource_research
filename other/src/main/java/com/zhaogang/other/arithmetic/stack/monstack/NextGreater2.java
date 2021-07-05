package com.zhaogang.other.arithmetic.stack.monstack;

import com.zhaogang.other.arithmetic.util.ArrUtils;

import java.util.Stack;

/**
 * @author jacks
 * @date 2021/6/30
 * @description NG的变种，可循环查找
 * 输入：1, 2, 1
 * 输出：2, -1, 2
 */
public class NextGreater2 {
    private static int[] arr = new int[] {1, 2, 1};

    public static void main(String[] args) {
        ArrUtils.printResult(getNextMaxArr());
    }

    private static int[] getNextMaxArr() {
        int[] res = new int[arr.length];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < arr.length; i++) {
            while (!stack.isEmpty() && arr[i] > arr[stack.peek()]) {
                Integer popIndex = stack.pop();
                res[popIndex] = arr[i];
            }
            stack.push(i);
        }

        while (!stack.isEmpty()) {
            res[stack.pop()] = -1;
        }
        return res;
    }

}
