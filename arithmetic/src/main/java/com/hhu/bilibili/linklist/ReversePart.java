package com.hhu.bilibili.linklist;

import java.util.Stack;

import com.hhu.bilibili.util.LinkedListUtils;

/**
 * @author jacks
 * @date 2021/7/23
 * @description 
 * @formatter:off 
 * 给定一个单链表的头节点 head，实现一个调整单链表的函数，使得每 K 个节点之间
 * 逆序，如果最后不够 K 个节点一组，则不调整最后几个节点。
 * 例如：
 * 链表：1->2->3->4->5->6->7->8->null，K=3。
 * 调整后为：3->2->1->6->5->4->7->8->null。其中 7、8 不调整，因为不够一组
 * @formatter:on 
 */
public class ReversePart {
    private static final int[] SEED = new int[] {1, 2, 3, 4, 5, 6, 7, 8};

    public static void main(String[] args) {
        Node head = LinkedListUtils.generate(SEED, false);
        Node newHead = reversePart(head, 3);
        LinkedListUtils.print(newHead);
    }

    /**
     * 思想：利用栈，不足 perPart 则持续压栈，若满则弹栈构建新链表，注意断开原有 链表的 next 节点。
     */
    private static Node reversePart(Node head, int perPart) {
        if (head == null || head.next == null || perPart <= 1) {
            return head;
        }

        Stack<Node> stack = new Stack<>();
        Node cur = head;
        Node newHead = null;
        Node cur2 = null;
        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
            // 到达指定部分进行反转并清空
            if (stack.size() == perPart) {
                while (!stack.isEmpty()) {
                    Node pop = stack.pop();
                    // 每个节点断开原有联系
                    pop.next = null;
                    if (newHead == null) {
                        newHead = pop;
                        cur2 = newHead;
                    } else {
                        cur2.next = pop;
                        cur2 = pop;
                    }
                }
            }
        }

        // 将原有不足perPart的部分节点还原回来
        Node tail = null;
        while (!stack.isEmpty()) {
            tail = stack.pop();
        }
        cur2.next = tail;

        return newHead;
    }

    private static Node partReverse2(Node head, int perPart) {
        if (head == null || head.next == null || perPart <= 1) {
            return head;
        }

        Node cur = head;
        Node partHead = null;
        Node partTail = null;
        int count = 1;
        while (cur != null) {

        }

        return null;
    }
}
