package com.zhaogang.other.arithmetic.tree;

import java.util.Stack;

/**
 * @author jacks
 * @date 2021/9/22
 * @description
 * @formatter:off 
 * 用递归和非递归方式，分别按照二叉树先序、中序和后序打印所有的节点。我们约定：先
 * 序遍历顺序为根、左、右；中序遍历顺序为左、根、右；后序遍历顺序为左、右、根
 * @formatter:on 
 */
public class IteratorTree {
    public static void main(String[] args) {
        // 1 2 4 5 3 6 7
        TreeNode root = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(5);
        TreeNode node6 = new TreeNode(6);
        TreeNode node7 = new TreeNode(7);

        root.fill(node2, node3);
        node2.fill(node4, node5);
        node3.fill(node6, node7);

        // preRecursiveIterate(root);
        // midReceiveIterate(root);
        // postReceiveIterate(root);

        // preIterate(root);
        midIterate(root);
    }

    /**
     * 前序: 1 2 4 5 3 6 7
     */
    private static void preRecursiveIterate(TreeNode root) {
        if (root == null) {
            return;
        }
        System.out.print(root.value + " ");
        preRecursiveIterate(root.left);
        preRecursiveIterate(root.right);
    }

    /**
     * 中序: 4 2 5 1 6 3 7
     */
    private static void midReceiveIterate(TreeNode root) {
        if (root == null) {
            return;
        }

        midReceiveIterate(root.left);
        System.out.print(root.value + " ");
        midReceiveIterate(root.right);
    }

    /**
     * 后序: 4 5 2 6 7 3 1
     */
    private static void postReceiveIterate(TreeNode root) {
        if (root == null) {
            return;
        }

        postReceiveIterate(root.left);
        postReceiveIterate(root.right);
        System.out.print(root.value + " ");
    }

    /**
     * 三角压栈
     */
    private static void preIterate(TreeNode root) {
        if (root == null) {
            return;
        }

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode pop = stack.pop();
            if (pop.right != null) {
                stack.push(pop.right);
            }
            if (pop.left != null) {
                stack.push(pop.left);
            }
            System.out.print(pop.value + " ");
        }
    }

    /**
     * 左、右、中入栈
     */
    private static void midIterate(TreeNode root) {
        if (root == null) {
            return;
        }

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        TreeNode cur = root;
        while (cur.left != null) {
            stack.push(cur.left);
            cur = cur.left;
        }

        while (!stack.isEmpty()) {
            TreeNode pop = stack.pop();
            System.out.print(pop.value + " ");
            if (pop.right != null) {
                stack.push(pop.right);
            }
        }

    }
}
