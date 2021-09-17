package com.zhaogang.other.arithmetic.linklist;

import java.util.Stack;

import com.zhaogang.other.arithmetic.util.LinkedListUtils;

/**
 * @author jacks
 * @date 2021/7/21
 * @description 判断一个链表是否存在环，若存在返回进入环的第一个节点
 */
public class LoopList {
    private static final int[] SEED = new int[] {1, 2, 3, 6};
    private static final int[] SEED2 = new int[] {3, 2, 6, 7, 5, 6};
    private static final int[] CROSS_SEED = new int[] {1, 2, 3, 4};

    public static void main(String[] args) {
        // Node head = null;
        // Node cur = head;
        // Node pivot = null;
        // for (int i = 1; i < 6; i++) {
        // Node node = new Node(i);
        // if (i == 3) {
        // pivot = node;
        // }
        // if (head == null) {
        // head = node;
        // cur = head;
        // continue;
        // }
        // cur.next = node;
        // cur = node;
        // }
        //
        // cur.next = pivot;
        //
        // Node startNode = getLoopStart(head);
        // System.out.println(startNode.value);

        Node head1 = LinkedListUtils.generate(SEED, false);
        Node head2 = LinkedListUtils.generate(SEED2, false);
        Node crossHead = LinkedListUtils.generate(CROSS_SEED, false);
        mergeWithCrossPart(head1, head2, crossHead);
        // Node crossNode = getCrossNode1(head1, head2);
        Node crossNode = getCrossNode2(head1, head2);
        System.out.println(crossNode.value);
    }

    /**
     * 将相交部分合并到各个链表中
     */
    private static void mergeWithCrossPart(Node head1, Node head2, Node crossHead) {
        if (crossHead == null) {
            return;
        }

        Node cur1 = head1;
        Node cur2 = head2;

        while (cur1.next != null) {
            cur1 = cur1.next;
        }
        cur1.next = crossHead;

        while (cur2.next != null) {
            cur2 = cur2.next;
        }
        cur2.next = crossHead;
    }

    /**
     * @formatter:off
     * 判断一个链表是否存在环，若存在返回进入环的第一个节点
     * 思路：快慢指针，fast每次后移2次，slow每次后移1次，依次遍历
     * 
     * 1. 若2者相遇则有环，此时重置fast到head，并将其速度改为每次后移1次，
     * slow速度不变，再次相遇的节点即为目标节点
     * 2. 若fast为null则无环
     * @formatter:on 
     */
    private static Node getLoopStart(Node head) {
        if (head == null || head.next == null) {
            return null;
        }

        Node fast = head;
        Node slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            // 若相遇，说明有环
            if (fast == slow) {
                fast = head;
                return getFirstNode(fast, slow);
            }
        }

        return null;
    }

    private static Node getFirstNode(Node fast, Node slow) {
        while (slow != null) {
            if (fast == slow) {
                return fast;
            }
            slow = slow.next;
            fast = fast.next;
        }
        return null;
    }

    /**
     * @formatter:off 
     * 如何判断两个无环链表是否相交，相交则返回第一个相交节点，不相交则返回 null。 
     * 如果两个无环链表相交，那么从相交节点开始，一直到两个链表终止的这一段，是两个链表共享的。
     *
     * 注意相交的定义为从相交点到末尾
     * @formatter:on
     * 解法1：利用栈实现，需要额外的空间复杂度
     */
    private static Node getCrossNode1(Node head1, Node head2) {
        if (head1 == null || head2 == null) {
            return null;
        }

        Stack<Node> stack1 = LinkedListUtils.getNodeStack(head1);
        Stack<Node> stack2 = LinkedListUtils.getNodeStack(head2);

        // 记录相交的节点，每次pop后进行更新
        Node result = null;
        while (!stack1.isEmpty() && !stack2.isEmpty()) {
            Node pop1 = stack1.pop();
            Node pop2 = stack2.pop();
            if (pop1 == pop2) {
                result = pop1;
            } else {
                break;
            }
        }
        return result;
    }

    /**
     * 相交节点的获取，比方法1更优，同样的时间复杂度，但不需要额外的空间。 思路：遍历获取链表长度，让2个指针同时指向倒数第N位（N为2个链表中长度较小的链表长度） 然后同时向后移动，第一次相等的就是目标节点
     */
    private static Node getCrossNode2(Node head1, Node head2) {
        Node cur1 = head1;
        Node cur2 = head2;
        int len1 = 0;
        int len2 = 0;

        // 遍历获取长度和尾节点
        while (cur1.next != null) {
            len1++;
            cur1 = cur1.next;
        }
        len1 = len1 + 1;

        while (cur2.next != null) {
            len2++;
            cur2 = cur2.next;
        }
        len2 = len2 + 1;

        // 注意相交的定义为从相交点到末尾，若尾节点不等就肯定没有相交节点了
        if (cur1 != cur2) {
            return null;
        }

        int subLen = len1 - len2;
        cur1 = head1;
        cur2 = head2;
        int index = 0;
        // 链表1长度大于等于链表2，先移动subLen位
        if (subLen >= 0) {
            while (cur1 != null) {
                // 到达目标位置，链表2开始移动
                if (index == subLen) {
                    if (cur1 == cur2) {
                        return cur1;
                    }
                    cur2 = cur2.next;
                }

                cur1 = cur1.next;
                index++;
            }
        }

        // 链表2长度大于等于链表1，先移动subLen位
        subLen = Math.abs(subLen);
        while (cur2 != null) {
            if (index >= subLen) {
                if (cur2 == cur1) {
                    return cur2;
                }
                cur1 = cur1.next;
            }

            cur2 = cur2.next;
            index++;
        }
        return null;
    }
}
