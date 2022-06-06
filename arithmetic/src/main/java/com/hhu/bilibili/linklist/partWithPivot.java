package com.hhu.bilibili.linklist;

import com.hhu.bilibili.util.ArrUtils;
import com.hhu.bilibili.util.LinkedListUtils;

/**
 * @author jacks
 * @date 2021/7/19
 * @description
 * @formatter:off 
 * 给定一个单向链表的头节点 head，节点的值类型是整型，再给定一个整数 pivot。实现一个
 * 调整链表的函数，将链表调整为左部分都是值小于 pivot 的节点，中间部分都是值等于 pivot 的
 * 节点，右部分都是值大于 pivot 的节点。除这个要求外，对调整后的节点顺序没有更多的要求。
 * 例如：链表 9->0->4->5->1，pivot=3。
 * 调整后链表可以是 1->0->4->9->5，也可以是 0->1->9->5->4。
 * @formatter:off 
 */
public class partWithPivot {
    private static final int[] SEED = new int[]{4,2,7,1,9,0};
    public static void main(String[] args){
        Node head = LinkedListUtils.generate(SEED, false);
        Node node = partWithPivot(head, 9);
        LinkedListUtils.print(node);
    }

    private static Node partWithPivot(Node head, int pivot) {
        if (head==null) {
            return head;
        }

        Node cur = head;
        int i = 0;
        while (cur!=null) {
            i++;
            cur = cur.next;
        }

        // 构造链表数组
        Node[] arr = new Node[i];
        cur = head;
        for(i = 0; i < arr.length; i++) {
            arr[i] = cur;
            cur = cur.next;
        }

        // 利用快排思想进行分组
        arrPartition(arr, pivot);
        
        // 将分组后的Node节点进行重新连接
        for(i = 1; i < arr.length; i++) {
            arr[i-1].next = arr[i];
        }
        // 构造尾节点，置为null
        arr[i-1].next = null;
        return arr[0];
    }

    /**
     * todo: 需要看明白
     * 利用快排思想进行分组
     */
    private static void arrPartition(Node[] arr, int pivot) {
        int small = -1;
        int big = arr.length;
        int index = 0;
        while (index!=big) {
            // 左边找小
            if (arr[index].value<pivot) {
                ArrUtils.swap(arr, ++small, index++);
            }else if (arr[index].value==pivot) {
                index++;
            } else {
                // 右边找大
                ArrUtils.swap(arr, --big, index);
            }
        }
    }
}
