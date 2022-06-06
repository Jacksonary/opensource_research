package com.hhu.bilibili.stack;

import java.util.Random;
import java.util.Stack;

/**
 * @author jacks
 * @date 2021/6/21
 * @description
 * @formatter:off
 * 实现一个特殊的栈，在实现栈的基本功能的基础上，再实现返回栈中最小元素的操作。
 * 【要求】
 * 1．pop、push、getMin 操作的时间复杂度都是 O(1)。
 * 2．设计的栈类型可以使用现成的栈结构。
 * @formatter:on
 */
public class GetMinStack {
    private static final Stack<Integer> DATA_STACK = new Stack<>();
    private static final Stack<Integer> MIN_STACK = new Stack<>();

    public static void main(String[] args) {
        Random random = new Random();
        System.out.print("push: ");
        for (int i = 0; i < 5; i++) {
            int nextInt = random.nextInt(10);
            System.out.print(nextInt + " ");
            push(nextInt);
        }
        System.out.println();

        System.out.println("pop: ");
        for (int i = 0; i < 5; i++) {
            System.out.println("[currentMin: " + getMin() + "] -> [pop: " + pop() + "]");
        }
    }

    /**
     * @formatter:off
     * 方式1（样例代码实现）：
     * 区分数据栈和最小栈
     * 数据栈：即普通栈
     * 最小栈：只存放比当前栈顶元素小或者相等的元素，否则不入栈（这种方式省空间）
     *
     * 方式2：
     * 最小栈的规则：压入时若当前元素大于栈顶元素，则重复压入栈顶元素（这种方式弹出时省时）
     * @formatter:on
     */
    private static Integer pop() {
        Integer pop = DATA_STACK.pop();
        if (pop.equals(MIN_STACK.peek())) {
            MIN_STACK.pop();
        }
        return pop;
    }

    private static void push(Integer item) {
        if (MIN_STACK.isEmpty() || item <= MIN_STACK.peek()) {
            MIN_STACK.push(item);
        }
        DATA_STACK.push(item);
    }

    private static Integer getMin() {
        return MIN_STACK.isEmpty() ? DATA_STACK.peek() : MIN_STACK.peek();
    }
}
