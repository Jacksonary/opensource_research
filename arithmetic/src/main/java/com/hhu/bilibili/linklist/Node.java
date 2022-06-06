package com.hhu.bilibili.linklist;

/**
 * @author jacks
 * @date 2021/7/8
 * @description
 */
public class Node {
    public int value;
    public Node next;

    public Node(int value) {
        this.value = value;
    }

    public Node(int value, Node next) {
        this.value = value;
        this.next = next;
    }
}
