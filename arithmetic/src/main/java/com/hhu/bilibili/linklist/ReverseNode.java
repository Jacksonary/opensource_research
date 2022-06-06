package com.hhu.bilibili.linklist;

import com.hhu.bilibili.util.LinkedListUtils;

/**
 * @author jacks
 * @date 2021/7/14
 * @description
 */
public class ReverseNode {
    private static final int[] SEED = new int[] {1, 2, 3, 4};

    public static void main(String[] args) {
        Node root = LinkedListUtils.generate(SEED, false);
        DoubleNode doubleRoot = LinkedListUtils.generateDouble(SEED);

        // 全链反转
        // Node reverseNode = reverse(root);
        // LinkedListUtils.print(reverseNode);

        // DoubleNode doubleNode = reverse(doubleRoot);
        // LinkedListUtils.printDouble(doubleNode);

        // 部分反转
//        partReverse(root, 1, 2);
//        LinkedListUtils.print(root);

        LinkedListUtils.print(test(root));
    }

    /**
     * 全量反转单链表
     */
    private static Node reverse(Node root) {
        Node cur = root;
        Node pre = null;
        Node next;
        while (cur != null) {
            // 暂存 next 节点
            next = cur.next;

            // 改变当前节点的后继，让其指向原来的前驱
            cur.next = pre;
            // 将后继节点变成前驱节点（然后指针后移）
            pre = cur;

            // 指针后移
            cur = next;
        }

        return pre;
    }

    /**
     * 全量反转双链表
     */
    private static DoubleNode reverse(DoubleNode root) {
        DoubleNode cur = root;
        DoubleNode pre = null;
        DoubleNode next;
        while (cur != null) {
            next = cur.next;

            cur.next = pre;
            cur.pre = next;

            pre = cur;
            cur = next;
        }
        return pre;
    }

    /**
     * @formatter:off
     * 给定一个单向链表的头节点 head，以及两个整数 from 和 to，
     * 在单向链表上把第 from 个节 点到第 to 个节点这一部分进行反转。
     * 例如： 1->2->3->4->5->null，from=2，to=4，调整结果为：1->4->3->2->5->null
     * 再如： 1->2->3->null，from=1，to=3 调整结果为：3->2->1->null
     * @formatter:on
     */
    private static void partReverse(Node root, int from, int to) {
        if (to <= 0) {
            return;
        }

        // 寻找目标节点
        int index = 1;
        Node cur = root;
        Node pre = null;
        Node next = null;

        Node tarPre = null;
        Node tarNext = null;
        while (cur != null) {
            // 目标区间的前驱
            if (index == from - 1) {
                tarPre = pre;
            }

            // 目标区间的后继
            if (index == to + 1) {
                tarNext = next;
                break;
            }

            next = cur.next;
            pre = cur;

            cur = next;
            index++;
        }

        Node reverse = reverse(tarPre.next);
        tarPre.next = reverse;
    }

    private static Node test(Node head) {
        if (head == null || head.next == null) {
            return head;
        }
        Node preHead = null;
        Node cur = head;
        Node next;
        while (cur != null) {
            next = cur.next;

            cur.next = preHead;
            preHead = cur;

            cur = next;
        }

        return preHead;
    }

}
