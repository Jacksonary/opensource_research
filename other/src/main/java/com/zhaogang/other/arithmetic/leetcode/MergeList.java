package com.zhaogang.other.arithmetic.leetcode;

import com.zhaogang.other.arithmetic.linklist.Node;

/**
 * @author jacks
 * @date 2021/12/16
 */
public class MergeList {
    public Node mergeTwoLists(Node list1, Node list2) {
        if (list1 == null || list2 == null) {
            return list1 == null ? list2 : list1;
        }

        Node cur1 = list1;
        Node cur2 = list2;
        Node result = new Node(Math.min(cur1.value, cur2.value));
        Node cur = result;
        if (cur1.value < cur2.value) {
            cur1 = cur1.next;
        } else {
            cur2 = cur2.next;
        }
        while (cur1 != null && cur2 != null) {
            if (cur1.value < cur2.value) {
                Node next = new Node(cur1.value);
                cur.next = next;
                cur = next;
                cur1 = cur1.next;
                continue;
            }
            Node next = new Node(cur2.value);
            cur.next = next;
            cur = next;
            cur2 = cur2.next;
        }

        if (cur1 != null) {
            cur.next = cur1;
        }

        if (cur2 != null) {
            cur.next = cur2;
        }

        return result;
    }
}
