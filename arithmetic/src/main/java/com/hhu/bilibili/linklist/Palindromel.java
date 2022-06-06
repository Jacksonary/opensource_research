package com.hhu.bilibili.linklist;

import java.util.Stack;

import com.hhu.bilibili.util.LinkedListUtils;

/**
 * @author jacks
 * @date 2021/7/16
 * @description
 * @formatter:off
 * 给定一个链表的头节点 head，请判断该链表是否为回文结构。
 * 例如：
 * 1->2->1，返回 true。
 * 1->2->2->1，返回 true。
 * 15->6->15，返回 true。
 * 1->2->3，返回 false。
 *
 * 进阶：
 * 如果链表长度为 N，时间复杂度达到 O(N)，额外空间复杂度达到 O(1)
 * @formatter:on
 */
public class Palindromel {
    private static final int[] SEED = new int[] {1, 2, 1};

    public static void main(String[] args) {
        Node head = LinkedListUtils.generate(SEED, false);
        // System.out.println(isPalindromel(head));
        // Node middleNode = getMiddleNode(head);
        System.out.println(isPalindromeWithMiddle(head));
    }

    /**
     * 【低级】折半入栈，不需要全量入栈反转
     */
    private static boolean isPalindromel(Node head) {
        if (head == null || head.next == null) {
            return true;
        }

        Node cur = head;
        int index = 0;
        // 取中间节点的方式不可取
        while (cur != null) {
            cur = cur.next;
            index++;
        }

        boolean isOu = index % 2 == 0;
        int target = index / 2;

        Stack<Integer> stack = new Stack<>();
        cur = head;
        while (stack.size() != target) {
            stack.push(cur.value);
            cur = cur.next;
        }

        if (!isOu) {
            cur = cur.next;
        }

        while (cur != null) {
            if (cur.value != stack.pop()) {
                return false;
            }
            cur = cur.next;
        }

        return true;
    }

    /**
     * 优化中间节点思路，找到中间节点
     */
    private static boolean isPalindromeWithMiddle(Node head) {
        if (head == null || head.next == null) {
            return true;
        }

        // 定位中间节点
        Node cur = head;
        Node right = head;
        while (right.next != null && right.next.next != null) {
            cur = cur.next;
            right = right.next.next;
        }

        // 指针继续向后移动，将后半个链路压栈
        Stack<Node> stack = new Stack<>();
        cur = cur.next;
        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
        }

        // 比较
        while (!stack.isEmpty()) {
            if (head.value != stack.pop().value) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    /**
     * 快慢指针即可获取 慢指针一次后移1个，快指针一次后移2个，当快指针遍历完后，慢指针位置即中间节点
     */
    private static Node getMiddleNode(Node head) {
        Node cur1 = head;
        Node cur2 = head;
        while (cur2.next != null && cur2.next.next != null) {
            cur1 = cur1.next;
            cur2 = cur2.next.next;
        }

        System.out.println(cur1.value);
        return cur1;
    }
}
