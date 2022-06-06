package com.hhu.bilibili.stack;

import java.util.Stack;

/**
 * 编写一个类，用两个栈实现队列，支持队列的基本操作（add、poll、peek）
 * 
 * @author jacks
 * @date 2021/12/6
 */
public class CustomQueue {
    private static final Stack<Integer> STACK1 = new Stack<>();
    private static final Stack<Integer> STACK2 = new Stack<>();

    public static void main(String[] args) {
        add(1);
        System.out.println(">> add 1, peek: " + peek());
        add(2);
        System.out.println(">> add 2, peek: " + peek());
        add(3);
        System.out.println(">> add 3, peek: " + peek());

        System.out.println(">> poll: " + poll() + ", peek: " + peek());
        System.out.println(">> poll: " + poll() + ", peek: " + peek());
        System.out.println(">> poll: " + poll() + ", peek: " + peek());
    }

    private static void add(Integer item) {
        STACK1.add(item);
    }

    private static Integer poll() {
        Integer result = null;
        while (!STACK1.isEmpty()) {
            result = STACK1.pop();
            STACK2.add(result);
        }

        // 反转过来
        boolean first = true;
        while (!STACK2.isEmpty()) {
            if (first) {
                STACK2.pop();
                first = false;
                continue;
            }
            STACK1.add(STACK2.pop());
        }

        return result;
    }

    private static Integer peek() {
        Integer result = null;
        while (!STACK1.isEmpty()) {
            result = STACK1.pop();
            STACK2.add(result);
        }

        // 反转过来
        while (!STACK2.isEmpty()) {
            STACK1.add(STACK2.pop());
        }

        return result;
    }

    private static Integer peek(boolean rm) {
        Integer result = null;
        while (!STACK1.isEmpty()) {
            result = STACK1.pop();
            STACK2.add(result);
        }

        // 反转过来
        while (!STACK2.isEmpty()) {
            STACK1.add(STACK2.pop());
        }

        return result;
    }
}
