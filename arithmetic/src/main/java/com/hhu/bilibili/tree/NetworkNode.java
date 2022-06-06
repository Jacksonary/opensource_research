package com.hhu.bilibili.tree;

/**
 * @author jacks
 * @date 2021/10/9
 * @description
 */
public class NetworkNode {
    public int value;
    public NetworkNode high;
    public NetworkNode low;
    public NetworkNode left;
    public NetworkNode right;

    public NetworkNode(int value) {
        this.value = value;
    }

    public void fill(NetworkNode high, NetworkNode low, NetworkNode left, NetworkNode right) {
        this.high = high;
        this.low = low;
        this.left = left;
        this.right = right;
    }
}
