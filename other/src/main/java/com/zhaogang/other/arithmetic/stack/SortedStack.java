package com.zhaogang.other.arithmetic.stack;

import java.util.Stack;

/**
 * @author jacks
 * @date 2021/6/21
 * @description
 * [failed]
 * 一个栈中元素的类型为整型，现在想将该栈从顶到底按从大到小的顺序排序，只许申请一
 * 个栈。除此之外，可以申请新的变量，但不能申请额外的数据结构。如何完成排序？
 */
public class SortedStack {
    private static final Stack<Integer> STACK = StackUtils.generate(5, 10);

    public static void main(String[] args) {
        sorted(STACK);
        StackUtils.printStack(STACK);
    }

    private static void sorted(Stack<Integer> stack) {
        if (stack == null || stack.isEmpty() || stack.size() == 1) {
            return;
        }

        Stack<Integer> helper = new Stack<>();
        // 借助另一个栈，让另一个栈的顺序从顶到底是升序
        while (!stack.isEmpty()) {
            Integer pop = stack.pop();
            while (!helper.isEmpty() && helper.peek() < pop) {
                stack.push(helper.pop());
            }

            helper.push(pop);
        }

        // 将中间栈倒置
        while (!helper.isEmpty()) {
            stack.push(helper.pop());
        }
    }

    private static Integer getMin(Stack<Integer> stack) {
        if (stack == null || stack.isEmpty()) {
            return null;
        }

        return 0;
    }

}
