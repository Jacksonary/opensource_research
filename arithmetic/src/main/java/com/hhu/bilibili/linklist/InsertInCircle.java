package com.hhu.bilibili.linklist;

import com.hhu.bilibili.util.LinkedListUtils;

/**
 * @author jacks
 * @date 2021/9/9
 * @description
 * @formatter:off
 * 一个环形单链表从头节点 head 开始不降序，同时由最后的节点指回头节点。
 * 给定这样一个环形单链表的头节点 head 和一个整数 num，
 * 请生成节点值为 num 的新节点，并插入到这个环形链表中，保证调整后的链表依然有序。
 * @formatter:on 
 */
public class InsertInCircle {
    public static void main(String[] args) {
        Node generate = LinkedListUtils.generate(new int[] {1, 3, 4, 1}, true);
        Node insert = insert(generate, 5);
        LinkedListUtils.print(insert);
    }

    private static Node insert(Node head, int toInsert) {
        if (head == null) {
            head = new Node(toInsert);
            head.next = head;
            return head;
        }

        Node cur = head;
        Node targetPre = null;
        Node tail;
        do {
            tail = cur;
            if (cur.value >= toInsert) {
                break;
            }
            targetPre = cur;
            cur = cur.next;
        } while (cur != null && cur != head);

        Node newNext = new Node(toInsert);
        if (targetPre == null) {
            // 末位节点
            newNext.next = head;
            tail.next = newNext;
            return head;
        }
        // 中间节点
        newNext.next = targetPre.next;
        targetPre.next = newNext;
        return head;
    }
}
