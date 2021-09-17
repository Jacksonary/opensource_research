package com.zhaogang.other.arithmetic.linklist;

import java.util.HashSet;
import java.util.Set;

import com.zhaogang.other.arithmetic.util.LinkedListUtils;

/**
 * @author jacks
 * @date 2021/7/26
 * @description 给定一个无序单链表的头节点 head，删除其中值重复出现的节点。 1->2->3->3->4->4->2->1->1->null，删除值重复的节点之后为 1->2->3->4->nul
 */
public class DeleteRepeat {
    private static final int[] SEED = new int[] {1, 2, 3, 3, 4, 4, 2, 1, 1};

    public static void main(String[] args) {
        Node head = LinkedListUtils.generate(SEED, false);
        Node deleteRepeat = deleteRepeat(head);
        LinkedListUtils.print(deleteRepeat);
    }

    private static Node deleteRepeat(Node head) {
        if (head == null || head.next == null) {
            return head;
        }
        Set<Integer> set = new HashSet<>();
        Node newHead = null;
        Node cur2 = null;
        Node cur = head;
        while (cur != null) {
            if (set.contains(cur.value)) {
                cur = cur.next;
                continue;
            }
            set.add(cur.value);
            Node next = new Node(cur.value);
            if (newHead == null) {
                newHead = next;
                cur2 = newHead;
            }
            cur2.next = next;
            cur2 = next;
            cur = cur.next;
        }

        return newHead;
    }
}
