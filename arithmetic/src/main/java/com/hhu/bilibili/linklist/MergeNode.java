package com.hhu.bilibili.linklist;

import java.util.Stack;

import com.hhu.bilibili.util.LinkedListUtils;

/**
 * @author jacks
 * @date 2021/7/20
 * @description
 * @formatter:off 
 * 假设链表中每一个节点的值都在 0～9 之间，那么链表整体就可以代表一个整数。
 * 例如：9->3->7，可以代表整数 937。
 * 给定两个这种链表的头节点 head1 和 head2，请生成代表两个整数相加值的结果链表。
 * 例如：链表 1 为 9->3->7，链表 2 为 6->3，最后生成新的结果链表为 1->0->0->0。
 * @formatter:on 
 */
public class MergeNode {
    private static final int[] SEED1 = new int[] {9, 3, 7};
    private static final int[] SEED2 = new int[] {6, 3};

    public static void main(String[] args) {
        Node head1 = LinkedListUtils.generate(SEED1, false);
        Node head2 = LinkedListUtils.generate(SEED2, false);

        Node result = mergeAndAdd(head1, head2);
        LinkedListUtils.print(result);
    }

    private static Node mergeAndAdd(Node head1, Node head2) {
        if (head1 == null) {
            return head2;
        }

        if (head2 == null) {
            return head1;
        }
        Stack<Integer> stack1 = getStack(head1);
        Stack<Integer> stack2 = getStack(head2);

        Stack<Integer> reverseNode = new Stack<>();
        // 进位标识
        int ca = 0;
        int total = 0;
        while (!stack1.isEmpty() && !stack2.isEmpty()) {
            total = stack1.pop() + stack2.pop() + ca;
            ca = total >= 10 ? 1 : 0;
            reverseNode.push(ca == 1 ? total - 10 : total);
        }

        while (!stack1.isEmpty()) {
            total = stack1.pop() + ca;
            ca = total >= 10 ? 1 : 0;
            reverseNode.push(ca == 1 ? total - 10 : total);
        }

        while (!stack2.isEmpty()) {
            total = stack2.pop() + ca;
            ca = total >= 10 ? 1 : 0;
            reverseNode.push(ca == 1 ? total - 10 : total);
        }

        if (ca == 1) {
            reverseNode.push(ca);
        }

        // 构建新的链表
        Node result = new Node(reverseNode.pop());
        Node cur = result;
        while (!reverseNode.isEmpty()) {
            Node node = new Node(reverseNode.pop());
            cur.next = node;
            cur = node;
        }
        return result;
    }

    private static Stack<Integer> getStack(Node head) {
        Stack<Integer> stack = new Stack<>();
        Node cur = head;
        while (cur != null) {
            stack.push(cur.value);
            cur = cur.next;
        }
        return stack;
    }
}
