package com.hhu.bilibili.stack;

import java.util.Random;
import java.util.Stack;

/**
 * @author jacks
 * @date 2021/6/21
 * @description 一个栈依次压入 1、2、3、4、5，那么从栈顶到栈底分别为 5、4、3、2、1。将这个栈转置
 * 后，从栈顶到栈底为 1、2、3、4、5，也就是实现栈中元素的逆序，但是只能用递归函数来实
 * 现，不能用其他数据结构
 */
public class RevertStack {
    private static final Stack<Integer> STACK = new Stack<>();

    public static void main(String[] args) {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int nextInt = random.nextInt(10);
            System.out.print(nextInt + " ");
            STACK.push(nextInt);
        }

        reverse(STACK);

        StackUtils.printStack(STACK);
    }

    /**
     * 将栈进行转置
     */
    private static void reverse(Stack<Integer> stack) {
        if (stack.isEmpty()) {
            return;
        }
        // 获取栈底元素
        Integer last = getAndRemoveLast(stack);
        reverse(stack);
        // 压入栈
        stack.push(last);
    }

    /**
     * 获取栈底元素
     */
    private static Integer getAndRemoveLast(Stack<Integer> stack) {
        if (stack.isEmpty()) {
            return null;
        }

        Integer pop = stack.pop();
        if (stack.isEmpty()) {
            return pop;
        }

        Integer last = getAndRemoveLast(stack);
        stack.push(pop);

        return last;
    }
}
