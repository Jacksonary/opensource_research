package com.zhaogang.other.arithmetic.stack.monstack;

import com.zhaogang.other.arithmetic.util.ArrUtils;

import java.util.Stack;

/**
 * @author jacks
 * @date 2021/6/29
 * @description 求下一个更大值的数组，若无则置为-1
 * 数组[2,1,2,4,3]，返回数组  [4,2,4,−1,−1]
 */
public class NextGreater {
    private static int[] arr = new int[] {2, 1, 2, 4, 3};

    public static void main(String[] args) {
        //        ArrUtils.printResult(getMaxArr());
        ArrUtils.printResult(getNG());
    }

    private static int[] getMaxArr() {
        int[] res = new int[arr.length];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < arr.length; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] < arr[i]) {
                // 弹出顶部元素
                Integer pop = stack.pop();
                res[pop] = arr[i];
            }
            stack.push(i);
        }

        while (!stack.isEmpty()) {
            res[stack.pop()] = -1;
        }
        return res;
    }

    private static int[] getNG() {
        int[] res = new int[arr.length];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < arr.length; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] < arr[i]) {
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
