package com.hhu.bilibili.linklist;

import com.hhu.bilibili.util.LinkedListUtils;

/**
 * @author jacks
 * @date 2021/7/15
 * @description
 * @formatter:off
 * 据说著名犹太历史学家 Josephus 有过以下故事：在罗马人占领乔塔帕特后，39 个犹太人与
 * Josephus 及他的朋友躲到一个洞中，39 个犹太人决定宁愿死也不要被敌人抓到，于是决定了一种自杀
 * 方式，41 个人排成一个圆圈，由第 1 个人开始报数，报数到 3 的人就自杀，然后再由下一个人重新
 * 报 1，报数到 3 的人再自杀，这样依次下去，直到剩下最后一个人时，那个人可以自由选择自己的命运
 * 。这就是著名的约瑟夫问题。现在请用单向环形链表描述该结构并呈现整个自杀过程。
 *
 * 输入：一个环形单向链表的头节点 head 和报数的值 m。
 * 返回：最后生存下来的节点，且这个节点自己组成环形单向链表，其他节点都删掉。
 * 进阶问题：如果链表节点数为 N，想在时间复杂度为 O(N)时完成原问题的要求，该怎么实现？
 * @formatter:on
 */
public class JosephusKill {
    private static final int[] SEED = new int[] {1, 2, 3, 4, 5};

    public static void main(String[] args) {
        Node head = LinkedListUtils.generate(SEED, true);
        gameStart(head, 3);
    }

    private static Node gameStart(Node head, int m) {
        Node cur = head;
        Node tail = head;

        // 获取尾节点
        while (tail.next != cur) {
            tail = tail.next;
        }

        int counter = 1;
        Node pre = tail;
        while (cur.next != cur) {
            if (counter == m) {
                System.out.println("kill person: " + cur.value);
                pre.next = cur.next;
                cur = cur.next;
                counter = 1;
                continue;
            }

            pre = cur;
            cur = cur.next;
            counter++;
        }

        System.out.println("last survive person: " + cur.value);

        return cur;
    }
}
