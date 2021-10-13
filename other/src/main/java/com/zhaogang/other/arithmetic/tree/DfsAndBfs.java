package com.zhaogang.other.arithmetic.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * @author jacks
 * @date 2021/10/8
 * @description
 */
public class DfsAndBfs {
    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(5);
        TreeNode node6 = new TreeNode(6);
        TreeNode node7 = new TreeNode(7);
        TreeNode node8 = new TreeNode(8);
        TreeNode node9 = new TreeNode(9);
        TreeNode node10 = new TreeNode(10);
        TreeNode node11 = new TreeNode(11);
        TreeNode node12 = new TreeNode(12);
        TreeNode node13 = new TreeNode(13);
        TreeNode node14 = new TreeNode(14);

        node1.fill(node2, node3);
        node2.fill(node5, null);
        node3.fill(node6, node7);
        node4.fill(null, node8);
        node5.fill(node9, node11);
        node6.fill(node10, node12);
        node7.fill(node13, node14);

        // dfsRecursive(node1);
        // dfsRecursive(node1);

        // bfsNoRecursive(node1);
        // bfsWithArray(node1);
        // bfsWithBinaryTreeLevelOrderTraversal(node1);

        bfs(node1);
    }

    private static void dfsRecursive(TreeNode root) {
        if (root == null) {
            return;
        }

        System.out.print(root.value + " ");
        if (root.left != null) {
            dfsRecursive(root.left);
        }
        if (root.right != null) {
            dfsRecursive(root.right);
        }
    }

    private static void dfsNoRecursive(TreeNode root) {
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

    private static void bfsNoRecursive(TreeNode root) {
        if (root == null) {
            return;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode pop = queue.poll();
            System.out.print(pop.value + " ");
            if (pop.left != null) {
                queue.add(pop.left);
            }
            if (pop.right != null) {
                queue.add(pop.right);
            }
        }
    }

    private static int[][] bfsWithArray(TreeNode root) {
        if (root == null) {
            return null;
        }

        List<List<Integer>> result = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            List<Integer> parRe = new ArrayList<>();

            // 这里定义二维数组的宽度，也就是要循环多少次 poll
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode poll = queue.poll();
                parRe.add(poll.value);
                if (poll.left != null) {
                    queue.add(poll.left);
                }

                if (poll.right != null) {
                    queue.add(poll.right);
                }
            }
            result.add(parRe);
        }

        System.out.println(result);

        return null;
    }

    private static List<List<Integer>> bfsWithBinaryTreeLevelOrderTraversal(TreeNode root) {
        if (root == null) {
            // 根节点为空，说明二叉树不存在，直接返回空数组
            return Arrays.asList();
        }

        // 最终的层序遍历结果
        List<List<Integer>> result = new ArrayList<>();

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            // 记录每一层
            List<Integer> parRe = new ArrayList<>();
            int levelNum = queue.size();
            // 遍历当前层的节点
            for (int i = 0; i < levelNum; i++) {
                TreeNode node = queue.poll();
                parRe.add(node.value);

                // 队首节点的左右子节点入队,由于 levelNum 是在入队前算的，所以入队的左右节点并不会在当前层被遍历到
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            result.add(parRe);
        }

        System.out.println(result);
        return result;
    }

    private static void bfs(TreeNode root) {
        if (root == null) {
            return;
        }

        int levelNum = 1;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            System.out.print("level " + levelNum++ + ": ");

            // 每次出队都伴随着入队的操作，导致当前层元素会参杂下一层的部分元素
            // 因此必须要
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode poll = queue.poll();
                System.out.print(poll.value + " ");
                if (poll.left != null) {
                    queue.add(poll.left);
                }
                if (poll.right != null) {
                    queue.add(poll.right);
                }
            }
            System.out.println();
        }
    }
}
