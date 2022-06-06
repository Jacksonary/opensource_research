package com.hhu.bilibili.tree;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author jacks
 * @date 2021/10/21
 * @description 给定一棵二叉树的头节点 head 和一个 32 位整数 sum，二叉树节点值类型为整型，求累加 和为 sum 的最长路径长度。路径是指从某个节点往下，每次最多选择一个孩子节点或者不选所 形成的节点链
 */
public class MaxLengthOfSum {
    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(-3);
        TreeNode node2 = new TreeNode(3);
        TreeNode node3 = new TreeNode(-9);
        TreeNode node4 = new TreeNode(1);
        TreeNode node5 = new TreeNode(0);
        TreeNode node6 = new TreeNode(2);
        TreeNode node7 = new TreeNode(1);
        TreeNode node8 = new TreeNode(1);
        TreeNode node9 = new TreeNode(6);
        node1.fill(node2, node3);
        node2.fill(node4, node5);
        node3.fill(node6, node7);
        node5.fill(node8, node9);

        // -3 -> 3 -> 0 -> 6, print 4
        System.out.println(getLen(node1, 6));
        // -9, print 1
        System.out.println(getLen(node1, -9));
    }

    private static int getLen(TreeNode root, int target) {
        if (root == null) {
            return -1;
        }

        // 累加和，最早的层数
        Map<Integer, Integer> sumMap = new HashMap<>(16);
        int sum = 0;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode pop = stack.pop();
            System.out.print(pop.value + " ");

            sum += pop.value;
            Integer subIndex = sumMap.get(sum - target);
            if (subIndex != null) {
                // 前面有目标序列
                System.out.println("find target: " + subIndex + ", cur: " + pop);
            }

            if (pop.right != null) {
                stack.push(pop.right);
            }
            if (pop.left != null) {
                stack.push(pop.left);
            }
        }

        return 0;
    }
}
