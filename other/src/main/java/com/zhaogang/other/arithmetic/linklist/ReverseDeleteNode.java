package com.zhaogang.other.arithmetic.linklist;

import com.zhaogang.other.arithmetic.util.LinkedListUtils;

/**
 * @author jacks
 * @date 2021/7/8
 * @description
 * @formatter:off
 * 分别实现两个函数，一个可以删除单链表中倒数第 K 个节点，另一个可以删除双链表中倒数第 K 个节点
 * 【要求】如果链表长度为 N，时间复杂度达到 O(N)，额外空间复杂度达到 O(1)
 * @formatter:on
 */
public class ReverseDeleteNode {
    private static final int[] SEED = new int[] {1, 2, 3, 4, 5};

    public static void main(String[] args) {
        Node root = LinkedListUtils.generate(SEED, false);
        DoubleNode doubleRoot = LinkedListUtils.generateDouble(SEED);

        LinkedListUtils.print(root);
        Node result = deleteReverseNode(5, root);
        LinkedListUtils.print(result);

        System.out.println("================================================");

        LinkedListUtils.printDouble(doubleRoot);
        DoubleNode doubleNode = deleteReverseNode(6, doubleRoot);
        LinkedListUtils.printDouble(doubleNode);
    }

    /**
     * 单链表删除，方式1
     */
    private static Node deleteReverseNode(int reverseIndex, Node root) {
        if (reverseIndex <= 0) {
            return null;
        }

        Node cur1 = root;
        Node cur2 = root;
        Node pre = null;
        int index = 0;
        int zeroLine = reverseIndex - 1;
        while (cur1.next != null) {
            if (index >= zeroLine) {
                pre = cur2;
                cur2 = cur2.next;
            }
            cur1 = cur1.next;
            index++;
        }

        // 打印目标节点
        if (index < reverseIndex) {
            System.out.println("can not find target.");
            return null;
        }
        System.out.println("find target: " + cur2.value);

        // 移除目标节点
        if (pre != null) {
            pre.next = cur2.next;
            return root;
        } else {
            // 若前驱节点为null,则目标节点为头部节点
            return root.next;
        }
    }

    /**
     * 双链表删除，方式2
     */
    private static DoubleNode deleteReverseNode(int reverseIndex, DoubleNode root) {
        DoubleNode cur1 = root;
        DoubleNode cur2 = root;
        int zeroLine = reverseIndex - 1;
        int index = 0;

        DoubleNode pre = null;

        while (cur1 != null) {
            cur1 = cur1.next;
            if (index >= reverseIndex) {
                pre = cur2;
                cur2 = cur2.next;
            }

            index++;
        }

        if (index < reverseIndex) {
            System.out.println("can not find target.");
            return null;
        }

        System.out.println("find target: " + cur2.value);

        if (pre == null) {
            root.next.pre = null;
            return root.next;
        }

        pre.next = cur2.next;
        cur2.pre = pre;
        return root;
    }

    private static Node deleteReverseNode2(Node root, int reverseIndex) {
        if (reverseIndex <= 0) {
            System.out.println(">> illegal param -> illegal reverseIndex: " + reverseIndex);
            return null;
        }


        return null;
    }
}
