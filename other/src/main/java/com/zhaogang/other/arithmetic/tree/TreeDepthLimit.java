package com.zhaogang.other.arithmetic.tree;

import java.util.Stack;

/**
 * @author jacks
 * @date 2021/10/9
 * @description 给定一个二叉树，找出其最大/最小深度。
 */
public class TreeDepthLimit {
    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(5);

        node1.fill(node2, node3);
        node3.fill(node2, node4);
        node4.fill(null, node5);

        // getMaxDepth(node1);
        // System.out.println(getMaxDepthWithRecursive(node1));
        System.out.println(getMinDepthWithRecursive(node1));
    }

    /**
     * 4
     * 
     * @param root
     * @return
     */
    private static int getMaxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode pop = stack.pop();
            System.out.print(pop.value + " ");
            if (pop.right != null) {
                stack.push(pop.right);
            }
            if (pop.left != null) {
                stack.push(pop.left);
            }
        }

        return 0;
    }

    private static int getMaxDepthWithRecursive(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return Math.max(getMaxDepthWithRecursive(root.left) + 1, getMaxDepthWithRecursive(root.right) + 1);
    }

    /**
     * 2
     * 
     * @param root
     * @return
     */
    private static int getMinDepth(TreeNode root) {
        return 0;
    }

    private static int getMinDepthWithRecursive(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int leftDepth = getMinDepthWithRecursive(root.left) + 1;
        int rightDepth = getMinDepthWithRecursive(root.right) + 1;
        return Math.min(leftDepth, rightDepth);
    }
}
