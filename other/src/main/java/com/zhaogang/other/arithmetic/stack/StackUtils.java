package com.zhaogang.other.arithmetic.stack;

import java.util.Random;
import java.util.Stack;

/**
 * @author jacks
 * @date 2021/6/21
 * @description
 */
public class StackUtils {
    public static void printStack(Stack<?> stack) {
        if (stack.isEmpty()) {
            return;
        }

        System.out.println();
        System.out.print(">> print stack: ");
        while (!stack.isEmpty()) {
            System.out.print("[pop " + stack.pop() + "] ");
        }
        System.out.println();
    }

    public static Stack<Integer> generate(int capacity, int range) {
        System.out.println();
        Stack<Integer> result = new Stack<>();
        Random random = new Random();
        System.out.print(">> generate stack: ");
        for (int i = 0; i < capacity; i++) {
            int nextInt = random.nextInt(range);
            result.push(nextInt);
            System.out.print("[push " + nextInt + "] ");
        }
        System.out.println();
        return result;
    }
}
