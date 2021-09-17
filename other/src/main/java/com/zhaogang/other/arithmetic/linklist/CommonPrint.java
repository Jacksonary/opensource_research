package com.zhaogang.other.arithmetic.linklist;

import com.zhaogang.other.arithmetic.util.LinkedListUtils;

/**
 * @author jacks
 * @date 2021/7/8
 * @description 给定两个有序链表的头指针 head1 和 head2，打印两个链表的公共部分
 */
public class CommonPrint {
    public static void main(String[] args) {
        Node root1 = LinkedListUtils.generate(new int[] {1, 2, 3, 4, 10}, false);
        Node root2 = LinkedListUtils.generate(new int[] {3, 4, 5, 6, 10}, false);

        printCommon(root1, root2);
    }

    private static void printCommon(Node root1, Node root2) {
        System.out.print(">> print: ");
        if (root1 == null || root2 == null) {
            return;
        }

        Node cur1 = root1;
        Node cur2 = root2;
        while (cur1 != null && cur2 != null) {
            if (cur1.value < cur2.value) {
                cur1 = cur1.next;
            } else if (cur1.value > cur2.value) {
                cur2 = cur2.next;
            } else {
                System.out.print(cur1.value + " ");
                cur1 = cur1.next;
                cur2 = cur2.next;
            }
        }
        System.out.println();
    }
}
