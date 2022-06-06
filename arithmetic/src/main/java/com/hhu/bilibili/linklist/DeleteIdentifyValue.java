package com.hhu.bilibili.linklist;

import com.hhu.bilibili.util.LinkedListUtils;

/**
 * @author jacks
 * @date 2021/7/27
 * @description 给定一个链表的头节点 head 和一个整数 num，请实现函数将值为 num 的节点全部删除。 例如，链表为 1->2->3->4->null，num=3，链表调整后为：1->2->4->null。
 */
public class DeleteIdentifyValue {
    private static final int[] SEED = new int[] {1, 2, 3, 4, 3};

    public static void main(String[] args) {
        Node generate = LinkedListUtils.generate(SEED, false);
        delete(generate, 3);
        LinkedListUtils.print(generate);

    }

    private static Node delete(Node head, int i) {
        if (head == null) {
            return head;
        }

        // 寻找头节点
        Node cur = head;
        Node newHead = null;
        while (cur != null) {
            if (cur.value != i) {
                newHead = cur;
                break;
            }
            cur = cur.next;
        }

        if (newHead == null) {
            return newHead;
        }

        // 删除指定值的节点，这里的pre肯定会有值，最差也会是寻找的newHead
        Node pre = null;
        while (cur != null) {
            Node next = cur.next;
            if (cur.value == i) {
                pre.next = next;
            }
            pre = cur;
            cur = next;
        }

        return newHead;
    }
}
