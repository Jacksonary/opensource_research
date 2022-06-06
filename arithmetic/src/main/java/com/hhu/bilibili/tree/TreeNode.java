package com.hhu.bilibili.tree;

/**
 * @author jacks
 * @date 2021/7/27
 * @description
 */
public class TreeNode {
    public int value;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(int value) {
        this.value = value;
    }

    public void fill(TreeNode left, TreeNode right) {
        this.left = left;
        this.right = right;
    }
}
