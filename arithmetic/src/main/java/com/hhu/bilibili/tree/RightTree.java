package com.hhu.bilibili.tree;

/**
 * @author jacks
 * @date 2021/11/4
 * @description
 */
public class RightTree {
    public static void main(String[] args) {
        TreeNode node8 = new TreeNode(8);
        TreeNode node5 = new TreeNode(5);
        TreeNode node10 = new TreeNode(10);
        TreeNode node3 = new TreeNode(3);
        TreeNode node6 = new TreeNode(6);
        TreeNode node9 = new TreeNode(9);
        TreeNode node15 = new TreeNode(15);

        node8.fill(node5, node10);
        node5.fill(node3, node6);
        node10.fill(node9, node15);

        mid(node8);
    }

    private static void mid(TreeNode root) {
        if (root == null) {
            return;
        }

        if (root.left != null) {
            mid(root.left);
        }
        System.out.print(root.value + " ");
        if (root.right != null) {
            mid(root.right);
        }
    }
}
