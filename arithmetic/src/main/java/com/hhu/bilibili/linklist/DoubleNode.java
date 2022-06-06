package com.hhu.bilibili.linklist;

/**
 * @author jacks
 * @date 2021/7/9
 * @description
 */
public class DoubleNode {
    public int value;
    public DoubleNode pre;
    public DoubleNode next;

    public DoubleNode(int value) {
        this.value = value;
    }

    public DoubleNode(DoubleNode pre, int value) {
        this.value = value;
        this.pre = pre;
    }
}
