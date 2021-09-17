package com.zhaogang.other.arithmetic.linklist;

import com.zhaogang.other.arithmetic.util.LinkedListUtils;

/**
 * @author jacks
 * @date 2021/9/7
 * @description 给定一个无序单链表的头节点 head，实现单链表的选择排序。 要求：额外空间复杂度为 O(1)。
 */
public class LinkedListSort {

    public static void main(String[] args) {
        Node generate = LinkedListUtils.generate(new int[] {3, 2, 7, 9}, false);
        LinkedListUtils.print(generate);
        reSort(generate);
        LinkedListUtils.print(generate);
//        Node smallestPreNode = getSmallestPreNode(generate);
//        System.out.println(smallestPreNode == null ? null : smallestPreNode.value);
    }

    private static void reSort(Node head) {
        if (head == null || head.next == null) {
            return;
        }

        Node newTail = null;
        Node smallPre;
        Node small;
        Node cur = head;
        while (cur != null) {
            small = cur;
            // 最小节点的前驱
            smallPre = getSmallestPreNode(head);

            // 删除目标节点
            if (smallPre != null) {
                small = smallPre.next;
                smallPre.next = small.next;
            }
            cur = cur == small ? cur.next : cur;

            if (newTail == null) {
                head = small;
            } else {
                newTail.next = small;
            }
            newTail = small;
        }

        return;
    }

    /**
     * 获取最小节点的前驱
     */
    private static Node getSmallestPreNode(Node head) {
        Node cur = head;
        Node target = head;
        Node preTar = null;
        Node pre = null;

        // 开始遍历获取最小节点并记录
        while (cur != null) {
            if (cur.value < target.value) {
                target = cur;
                preTar = pre;
            }
            pre = cur;
            cur = cur.next;
        }

        return preTar;
    }
}
