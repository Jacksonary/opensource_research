package com.hhu.bilibili.linklist;

import com.hhu.bilibili.util.LinkedListUtils;

/**
 * @author jacks
 * @date 2021/9/9
 * @description
 * 
 * @formatter:off 
 * 链表节点值类型为 int 型，给定一个链表中的节点 node，但不给定整个链表的头节点。
 * 如何在链表中删除 node？请实现这个函数，并分析这样做会出现哪些问题。 
 * 
 * 要求：时间复杂度为 O(1)。
 * @formatter:on 
 */
public class DeleteAmazingNode {

    public static void main(String[] args) {
        Node generate = LinkedListUtils.generate(new int[] {1, 2, 3, 4}, false);

        // 随便定义一个待删除的节点
        Node toDelete = null;
        Node cur = generate;
        while (cur != null) {
            if (cur.value == 3) {
                toDelete = cur;
                break;
            }
            cur = cur.next;
        }

        deleteIdentityNode(toDelete);
        LinkedListUtils.print(generate);
    }

    /**
     * @formatter:off
     * 这里只暴露待删除的节点，且不暴露出头节点
     * [注] 思路：复制目标的后继节点，再删除后继，使目标节点完全接替后继节点的工作，该方式不能删除末尾节点。
     * 但现实开发中，完全复制节点的方案在复杂场景中不现实，需要完全属性替换 + 后继节点的对外职能的替换
     * @formatter:on
     */
    private static void deleteIdentityNode(Node toDelete) {
        if (toDelete == null) {
            return;
        }

        if (toDelete.next == null) {
            throw new RuntimeException(">> next node deletion not support!");
        }

        Node next = toDelete.next;
        // 将目标节点替换为后继节点
        toDelete.value = next.value;
        toDelete.next = next.next;
    }
}
