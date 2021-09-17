package com.zhaogang.other.arithmetic.linklist;

import com.zhaogang.other.arithmetic.util.LinkedListUtils;

/**
 * @author jacks
 * @date 2021/9/10
 * @description
 * @formatter:off 
 * 给定两个有序单链表的头节点 head1 和 head2，请合并两个有序链表，
 * 合并后的链表依然有序，并返回合并后链表的头节点。
 * 例如：
 * 0->2->3->7->null
 * 1->3->5->7->9->null
 * 合并后的链表为：0->1->2->3->3->5->7->7->9->null
 * @formatter:on 
 */
public class MergeLinkedList {

    public static void main(String[] args) {
        Node head1 = LinkedListUtils.generate(new int[] {0, 2, 3, 7}, false);
        Node head2 = LinkedListUtils.generate(new int[] {1, 3, 5, 7, 9}, false);
        Node merge = merge(head1, head2);
        LinkedListUtils.print(merge);
    }

    private static Node merge(Node head1, Node head2) {
        if (head1 == null) {
            return head2;
        }

        if (head2 == null) {
            return head1;
        }

        Node cur1 = head1;
        Node cur2 = head2;
        Node reHead = null;
        Node cur = null;
        while (cur1 != null && cur2 != null) {
            Node target;
            if (cur1.value <= cur2.value) {
                target = cur1;
                cur1 = cur1.next;
            } else {
                target = cur2;
                cur2 = cur2.next;
            }

            if (reHead == null) {
                reHead = target;
                cur = target;
            } else {
                cur.next = target;
                cur = cur.next;
            }
        }

        if (cur1 != null) {
            cur.next = cur1;
        }

        if (cur2 != null) {
            cur.next = cur2;
        }

        return reHead;
    }
}
