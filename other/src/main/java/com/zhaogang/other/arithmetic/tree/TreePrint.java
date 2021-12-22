package com.zhaogang.other.arithmetic.tree;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author jacks
 * @date 2021/10/26
 * @description
 * @formatter:off
 * 给定一棵二叉树的头节点 head，分别实现按层和 ZigZag 打印二叉树的函数。
 * 例如，二叉树如下
 *     1
 *  2     3
 * 4    5   6
 *    7   8
 * 按层打印时，输出格式必须如下：
 * Level 1 : 1
 * Level 2 : 2 3
 * Level 3 : 4 5 6
 * Level 4 : 7 8
 * ZigZag 打印时，输出格式必须如下：
 * Level 1 from left to right: 1
 * Level 2 from right to left: 3 2
 * Level 3 from left to right: 4 5 6
 * Level 4 from right to left: 8 7
 * @formatter:on 
 */
public class TreePrint {
    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(5);
        TreeNode node6 = new TreeNode(6);
        TreeNode node7 = new TreeNode(7);
        TreeNode node8 = new TreeNode(8);

        node1.fill(node2, node3);
        node2.fill(node4, null);
        node3.fill(node5, node6);
        node5.fill(node7, node8);

        // levelPrint(node1);
        // levelPrint2(node1);
        zigZag(node1);
    }

    /**
     * @formatter:off
     * bfs 按层打印，换行节点的确认需要当前层和下一层的末尾节点cr和ncr
     * 当到达当前层的末尾节点cr后进行换行，并将cr置为ncr，
     * ncr的确认：始终是最新加入队列的节点
     * @formatter:on
     */
    private static void levelPrint(TreeNode root) {
        if (root == null) {
            return;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        TreeNode mr = root;
        TreeNode nmr = null;
        int level = 1;

        System.out.print("level " + level++ + ": ");
        while (!queue.isEmpty()) {
            TreeNode poll = queue.poll();
            System.out.print(poll.value + " ");

            if (poll.left != null) {
                queue.add(poll.left);
                nmr = poll.left;
            }

            if (poll.right != null) {
                queue.add(poll.right);
                nmr = poll.right;
            }

            if (poll == mr && !queue.isEmpty()) {
                System.out.print("\nlevel " + level++ + ": ");
                mr = nmr;
            }
        }
    }

    /**
     * bfs level2
     * 
     * @param root
     */
    private static void levelPrint2(TreeNode root) {
        if (root == null) {
            return;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        TreeNode mr = root;
        TreeNode nmr = null;
        int level = 1;
        System.out.print("level " + level++ + ": ");
        while (!queue.isEmpty()) {
            TreeNode poll = queue.poll();
            System.out.print(poll.value + " ");
            if (poll.left != null) {
                queue.add(poll.left);
                nmr = poll.left;
            }

            if (poll.right != null) {
                queue.add(poll.right);
                nmr = poll.right;
            }

            if (poll == mr && !queue.isEmpty()) {
                System.out.print("\n level " + level++ + ": ");
                mr = nmr;
            }
        }
    }

    /**
     * 之型打印
     */
    private static void zigZag(TreeNode root) {
        if (root == null) {
            return;
        }

        Deque<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        TreeNode cr = root;
        TreeNode nr = null;
        int level = 1;
        System.out.print("level " + level++ + ": ");
        TreeNode poll;
        while (!queue.isEmpty()) {
            // 奇数从左到右，就是普通的bfs，入队尾顺序为：左右，出队为：头部
            if (level % 2 == 1) {
                poll = queue.pollFirst();
                if (poll.left != null) {
                    queue.addLast(poll.left);
                    nr = poll.left;
                }
                if (poll.right != null) {
                    queue.addLast(poll.right);
                    nr = poll.right;
                }
            } else {
                // 偶数从右到左，入队尾顺序为：右左，出队为：尾部
                poll = queue.pollLast();
                boolean flag = false;
                if (poll.right != null) {
                    queue.addFirst(poll.right);
                    nr = poll.right;
                    flag = true;
                }
                if (poll.left != null) {
                    queue.addFirst(poll.left);
                    nr = poll.left;
                    if (!flag) {
                        nr = poll.left;
                    }
                }
            }

            System.out.print(poll.value + " ");

            if (poll == cr && !queue.isEmpty()) {
                System.out.print("\nlevel " + level++ + ": ");
                cr = nr;
            }
        }
    }
}
