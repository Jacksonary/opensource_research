package com.hhu.bilibili.stack;

import java.util.Random;
import java.util.Stack;

/**
 * @author jacks
 * @date 2021/6/21
 * @description 用两个栈实现队列，支持队列的基本操作（add、poll、peek）
 */
public class QueueWithStack {
    private static final Stack<Integer> COMMON_STACK = new Stack<>();
    private static final Stack<Integer> QUEUE_STACK = new Stack<>();

    public static void main(String[] args) {
        Random rand = new Random();
        System.out.print("add: ");
        for (int i = 0; i < 5; i++) {
            int nextInt = rand.nextInt(10);
            add(nextInt);
            System.out.print(nextInt + " ");
            if (i == 4) {
                poll();
            }
        }
        System.out.println();

        while (peek() != null) {
            System.out.println(poll() + " ");
        }
    }

    private static void add(Integer item) {
        COMMON_STACK.add(item);
    }

    private static Integer poll() {
        if (COMMON_STACK.isEmpty()) {
            return null;
        }

        QUEUE_STACK.clear();
        while (!COMMON_STACK.isEmpty()) {
            QUEUE_STACK.add(COMMON_STACK.pop());
        }
        Integer pop = QUEUE_STACK.pop();
        while (!QUEUE_STACK.isEmpty()) {
            COMMON_STACK.add(QUEUE_STACK.pop());
        }

        return pop;
    }

    private static Integer peek() {
        if (COMMON_STACK.isEmpty()) {
            return null;
        }
        QUEUE_STACK.clear();
        while (!COMMON_STACK.isEmpty()) {
            QUEUE_STACK.add(COMMON_STACK.pop());
        }
        Integer pop = QUEUE_STACK.peek();
        while (!QUEUE_STACK.isEmpty()) {
            COMMON_STACK.add(QUEUE_STACK.pop());
        }

        return pop;
    }
}
