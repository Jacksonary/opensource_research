package com.zhaogang.other.arithmetic.tree;

/**
 * @author jacks
 * @date 2021/10/19
 * @description mirrors 神级遍历，空间复杂度为O(1)，主要利用了叶子节点的空间概念
 * 
 * @formatter:off 
 * 我们先不管先序、中序、后序的概念，先看看 Morris 遍历的过程。
 * 假设当前节点为 cur，初始时 cur 就是整棵树的头节点，根据以下标准让 cur 移动：
 * 1．如果 cur 为 null，则过程停止，否则继续下面的过程。
 * 2．如果 cur 没有左子树，则让 cur 向右移动，即令 cur = cur.right。
 * 3．如果 cur 有左子树，则找到 cur 左子树上最右的节点，记为 mostRight。
 *     1）如果 mostRight 的 right 指针指向 null，则令 mostRight.right = cur，也就是让 mostRight
 * 的 right 指针指向当前节点，然后让 cur 向左移动，即令 cur = cur.left。
 *     2）如果 mostRight 的 right 指针指向 cur，则令 mostRight.right = null，也就是让 mostRight
 * 的 right 指针指向 null，然后让 cur 向右移动，即令 cur = cur.right
 * @formatter:on 
 */
public class MirrorIterator {
    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(4);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(6);
        TreeNode node4 = new TreeNode(1);
        TreeNode node5 = new TreeNode(3);
        TreeNode node6 = new TreeNode(5);
        TreeNode node7 = new TreeNode(7);

        node1.fill(node2, node3);
        node2.fill(node4, node5);
        node3.fill(node6, node7);

        // mirrors(node1);
        // preMirrors(node1);
        midMirrors(node1);
    }

    /**
     * 原始神级遍历，会重复打印具有左子树的节点1次
     */
    private static void mirrors(TreeNode root) {
        if (root == null) {
            return;
        }

        TreeNode cur = root;
        while (cur != null) {
            System.out.print(cur.value + " ");
            if (cur.left != null) {
                // 最右节点
                TreeNode mostRight = cur.left;
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }

                if (mostRight.right == null) {
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                }

                // 断开链接
                if (mostRight.right == cur) {
                    mostRight.right = null;
                    cur = cur.right;
                }
                continue;
            }

            // 左子树为null
            cur = cur.right;
        }
        System.out.println();
    }

    /**
     * 神级先序遍历，只打印具有左子树的第一次
     */
    private static void preMirrors(TreeNode root) {
        if (root == null) {
            return;
        }

        TreeNode cur = root;
        while (cur != null) {
            // System.out.print(cur.value + " ");
            if (cur.left == null) {
                System.out.print(cur.value + " ");
                // 没有左子树，cur 右移
                cur = cur.right;
                continue;
            }

            // 默认左子树的根节点为最右节点
            TreeNode mostRight = cur.left;
            // 寻找左子树的最右节点
            while (mostRight.right != null || mostRight.left != null) {
                if (mostRight.right == null) {
                    mostRight = mostRight.left;
                    continue;
                }

                if (mostRight.right == cur) {
                    break;
                }
                mostRight = mostRight.right;
            }

            if (mostRight.right == cur) {
                // 断开链接
                mostRight.right = null;
                cur = cur.right;
                continue;
            }

            // mostRight.right != cur ，其实就是 right 指向 null
            System.out.print(cur.value + " ");
            mostRight.right = cur;
            cur = cur.left;
        }

        System.out.println();
    }

    /**
     * 神级中序遍历
     */
    private static void midMirrors(TreeNode root) {
        if (root == null) {
            return;
        }

        TreeNode cur = root;
        while (cur != null) {
            if (cur.left == null) {
                System.out.print(cur.value + " ");
                cur = cur.right;
                continue;
            }

            TreeNode mostRight = cur.left;
            // 寻找最右节点
            while (mostRight.right != null || mostRight.left != null) {
                if (mostRight.right == null) {
                    mostRight = mostRight.left;
                    continue;
                }

                if (mostRight.right == cur) {
                    break;
                }
                mostRight = mostRight.right;
            }

            if (mostRight.right == cur) {
                System.out.print(cur.value + " ");
                mostRight.right = null;
                cur = cur.right;
                continue;
            }

            mostRight.right = cur;
            cur = cur.left;
        }

        System.out.println();
    }

    /**
     * 寻找最右节点
     */
    private static TreeNode findMostRightNode(TreeNode node) {
        if (node == null) {
            return null;
        }

        TreeNode mostRight = node;
        while (mostRight.right != null && mostRight.right != node) {
            mostRight = mostRight.right != null ? mostRight.right : mostRight.left;
        }

        return mostRight;
    }
}
