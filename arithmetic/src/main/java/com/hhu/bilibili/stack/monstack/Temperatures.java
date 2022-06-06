package com.hhu.bilibili.stack.monstack;

import com.hhu.bilibili.util.ArrUtils;

import java.util.Stack;

/**
 * @author jacks
 * @date 2021/6/29
 * @description 给出几天的温度数组, 要求输出一个新数组, 标识超过当前温度的最小间隔天数
 * 如：73,74,75,71,69,72,76,73,输出应为：1,1,4,2,1,1,0,0
 */
public class Temperatures {
    private static final int[] tem = new int[] {73, 74, 75, 71, 69, 72, 76, 73};

    public static void main(String[] args) {
        ArrUtils.printResult(getExedDays());
    }

    /**
     * 利用单调栈实现
     */
    private static int[] getExedDays() {
        int[] res = new int[tem.length];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < tem.length; i++) {
            while (!stack.isEmpty() && tem[stack.peek()] < tem[i]) {
                Integer index = stack.pop();
                res[index] = i - index;
            }
            stack.push(i);
        }

        if (!stack.isEmpty()) {
            res[stack.pop()] = 0;
        }

        return res;
    }

}
