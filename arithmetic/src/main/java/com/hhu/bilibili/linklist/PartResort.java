package com.hhu.bilibili.linklist;

import com.hhu.bilibili.util.LinkedListUtils;

/**
 * @author jacks
 * @date 2021/9/10
 * @description
 * @formatter:on
 * 给定一个单链表的头部节点 head，链表长度为 N，如果 N 为偶数，
 * 那么前 N/2 个节点算作左半区，后 N/2 个节点算作右半区；
 * 如果 N 为奇数，那么前 N/2 个节点算作左半区，
 * 后 N/2+1个节点算作右半区。左半区从左到右依次记为 L1->L2->…，
 * 右半区从左到右依次记为 R1->R2->…，
 * 请将单链表调整成 L1->R1->L2->R2->…的形式。
 * 例如：
 * 1->null，调整为 1->null。
 * 1->2->null，调整为 1->2->null
 * 1->2->3->null，调整为 1->2->3->null。
 * 1->2->3->4->null，调整为 1->3->2->4->null。
 * 1->2->3->4->5->null，调整为 1->3->2->4->5->null。
 * 1->2->3->4->5->6->null，调整为 1->4->2->5->3->6->null
 * @formatter:off
 */
public class PartResort {
    public static void main(String[] args){
        Node generate = LinkedListUtils.generate(new int[]{1,2,3,4}, false);
        Node node = partResort(generate);
        LinkedListUtils.print(node);
    }

    private static Node partResort(Node head) {
        if (head==null|| head.next==null || head.next.next==null) {
            return head;
        }

        Node slow = head;
        Node fast = head;
        // 遍历得到中间节点
        while (fast.next!=null && fast.next.next!=null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        Node cur1;
        Node cur2;
        // 识别奇偶，定义左右半区
        if (fast.next==null) {
            // 奇数，slow 指向中间节点，slow 指向右半区的起点
             cur1 = head;
             cur2 = slow;
        } else {
            // 偶数，slow指向左半区的末尾节点
             cur1 = head;
             cur2 = slow.next;
        }

        Node reHead = null;
        Node cur = null;
        boolean isCur1 = true;
        while (cur1!=null&&cur2!=null) {
            if (isCur1) {
                if (reHead==null) {
                    reHead = cur1;
                    cur = reHead;
                } else {
                    cur.next = cur1;
                    cur = cur.next;
                    cur1 = cur1.next;
                }
                isCur1 = false;
                continue;
            }
            
            cur = cur2;
            cur2 = cur2.next;
            isCur1 = true;
        }

        if (cur2!=null) {

        }
        return reHead;
    }
}
