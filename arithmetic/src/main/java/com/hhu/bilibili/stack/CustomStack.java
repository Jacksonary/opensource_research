package com.hhu.bilibili.stack;

import java.util.Stack;

/**
 * @formatter:off 
 * 实现一个特殊的栈，在实现栈的基本功能的基础上，再实现返回栈中最小元素的操作。 
 * 1．pop、push、getMin 操作的时间复杂度都是 O(1)。 
 * 2．设计的栈类型可以使用现成的栈结构。
 * @formatter:on 
 * 
 * @author jacks
 * @date 2021/12/6
 */
public class CustomStack {

    private static final Stack<Integer> STACK = new Stack<>();
    private static final Stack<Integer> MIN_STACK = new Stack<>();

    public static void main(String[] args) {
        push(3);
        System.out.println(">> put: 3, min: " + getMin());
        push(2);
        System.out.println(">> put: 2, min: " + getMin());
        push(5);
        System.out.println(">> put: 5, min: " + getMin());
        push(7);
        System.out.println(">> put: 7, min: " + getMin());
        push(1);
        System.out.println(">> put: 1, min: " + getMin());

        System.out.println("================================");

        System.out.println(">> pop: " + pop() + " , min: " + getMin());
        System.out.println(">> pop: " + pop() + " , min: " + getMin());
        System.out.println(">> pop: " + pop() + " , min: " + getMin());
        System.out.println(">> pop: " + pop() + " , min: " + getMin());
        System.out.println(">> pop: " + pop() + " , min: " + getMin());
    }

    private static void push(Integer item) {
        STACK.push(item);
        if (MIN_STACK.isEmpty()) {
            MIN_STACK.push(item);
            return;
        }

        if (MIN_STACK.peek() > item) {
            MIN_STACK.push(item);
        } else {
            MIN_STACK.push(MIN_STACK.peek());
        }
    }

    private static Integer pop() {
        if (STACK.isEmpty()) {
            return null;
        }
        MIN_STACK.pop();
        return STACK.pop();
    }

    private static Integer getMin() {
        if (MIN_STACK.isEmpty()) {
            return null;
        }
        return MIN_STACK.peek();
    }
}
