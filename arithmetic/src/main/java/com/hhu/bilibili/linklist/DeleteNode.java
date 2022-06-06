package com.hhu.bilibili.linklist;

import com.hhu.bilibili.util.LinkedListUtils;

/**
 * @author jacks
 * @date 2021/7/13
 * @description
 * @formatter:off
 * 给定链表的头节点 head，实现删除链表的中间节点的函数。
 * 例如：
 * 不删除任何节点；
 * 1->2，删除节点 1；
 * 1->2->3，删除节点 2；
 * 1->2->3->4，删除节点 2；
 * 1->2->3->4->5，删除节点 3；
 * 进阶：
 * 给定链表的头节点 head、整数 a 和 b，实现删除位于 a/b 处节点的函数。
 * 例如：
 * 链表：1->2->3->4->5，假设 a/b 的值为 r。
 * 如果 r 等于 0，不删除任何节点；
 * 如果 r 在区间(0, 1/5]上，删除节点 1；
 * 如果 r 在区间(1/5, 2/5]上，删除节点 2；
 * 如果 r 在区间(2/5, 3/5]上，删除节点 3；
 * 如果 r 在区间(3/5, 4/5]上，删除节点 4；
 * 如果 r 在区间(4/5, 1]上，删除节点 5；
 * 如果 r 大于 1，不删除任何节点。
 * @formatter:on
 */
public class DeleteNode {
    private static final int[] SEED = new int[] {1, 2, 3, 4};

    public static void main(String[] args) {
        Node head = LinkedListUtils.generate(SEED, false);
        deleteMiddle2(head);
        LinkedListUtils.print(head);
    }

    /**
     * leteeCode 题目不一样
     *
     * @param root
     */
    private static void deleteMiddle(Node root) {
        root.value = root.next.value;
        root.next = root.next.next;
    }

    private static Node deleteMiddle2(Node root) {
        if (root == null || root.next == null) {
            return root;
        }

        if (root.next.next == null) {
            return root.next;
        }

        Node pre = root;
        Node cur = root.next.next;
        while (cur.next != null && cur.next.next != null) {
            pre = pre.next;
            cur = cur.next.next;
        }

        pre.next = pre.next.next;
        return root;
    }
}
