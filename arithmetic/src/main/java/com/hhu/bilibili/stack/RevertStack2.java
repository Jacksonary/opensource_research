package com.hhu.bilibili.stack;

import java.util.Stack;

/**
 * @formatter:off 
 * 一个栈依次压入 1、2、3、4、5，那么从栈顶到栈底分别为 5、4、3、2、1。
 * 将这个栈转置 后，从栈顶到栈底为 1、2、3、4、5，也就是实现栈中元素的逆序，
 * 但是只能用递归函数来实 现，不能用其他数据结构。
 * @formatter:on
 * @author jacks
 * @date 2021/12/6
 */
public class RevertStack2 {

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.add(1);
        stack.add(2);
        stack.add(3);
        stack.add(4);
        stack.add(5);
        System.out.println(stack);
        reverse(stack);
        System.out.println(stack);
    }

    private static Integer reverse(Stack<Integer> stack) {
        // Integer pop = stack.pop();
        // if (stack.isEmpty()) {
        // return pop;
        // }
        // Integer reverse = reverse(stack);
        // stack.push(reverse);
        // return reverse;
        int result = stack.pop();
        if (stack.isEmpty()) {
            return result;
        } else {
            int last = reverse(stack);
            stack.push(result);
            return last;
        }
    }
}
