package com.hhu.bilibili.tree;

import java.util.Stack;

/**
 * @author jacks
 * @date 2021/10/14
 * @description
 * @formatter:off
 * 二叉树被记录成文件的过程叫作二叉树的序列化，通过文件内容重建原来二叉树的过程叫
 * 作二叉树的反序列化。给定一棵二叉树的头节点 head，已知二叉树节点值的类型为 32 位整型。
 * 请设计一种二叉树序列化和反序列化的方案，并用代码实现
 * @formatter:on
 */
public class TreeSerializer {
    public static void main(String[] args) {
//        TreeNode node1 = new TreeNode(1);
//        TreeNode node2 = new TreeNode(2);
//        TreeNode node3 = new TreeNode(3);
//        TreeNode node4 = new TreeNode(4);
//        TreeNode node5 = new TreeNode(5);
//        TreeNode node6 = new TreeNode(6);
//        TreeNode node7 = new TreeNode(7);
//        node1.fill(node2, node3);
//        node2.fill(node4, null);
//        node3.fill(node5, node6);
//        node4.fill(null, node7);

        TreeNode node1 = new TreeNode(12);
        TreeNode node3 = new TreeNode(3);
        node1.fill(node3, null);

        preSerialize(node1);
//        preSerialize2(node1);
    }

    private static void preSerialize(TreeNode root) {
        if (root == null) {
            System.out.print("#!");
            return;
        }

        System.out.print(root.value + "!");
        preSerialize(root.left);
        preSerialize(root.right);
    }

    private static void preSerialize2(TreeNode root) {
        if (root == null) {
            return;
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
    }
}
