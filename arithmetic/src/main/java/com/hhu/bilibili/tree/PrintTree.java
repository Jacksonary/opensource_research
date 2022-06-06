package com.hhu.bilibili.tree;

/**
 * @author jacks
 * @date 2021/9/24
 * @description
 * @formatter:off 
 * 给定一棵二叉树的头节点 head，按照如下两种标准分别实现二叉树边界节点的逆时针打印。
 * 标准一：
 * 1．头节点为边界节点。
 * 2．叶节点为边界节点。
 * 3．如果节点在其所在的层中是最左的或最右的，那么该节点也是边界节点。
 * 标准二：
 * 1．头节点为边界节点。
 * 2．叶节点为边界节点。
 * 3．树左边界延伸下去的路径为边界节点。
 * 4．树右边界延伸下去的路径为边界节点。
 * @formatter:on 
 */
public class PrintTree {
    public static void main(String[] args) {
        /**
         * 构建的
         */
        TreeNode root = new TreeNode(1);
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
        TreeNode node15 = new TreeNode(15);
        TreeNode node16 = new TreeNode(16);

        root.fill(node2, node3);
        node2.fill(null, node4);
        node3.fill(node5, node6);
        node4.fill(node7, node8);
        node5.fill(node9, node10);
        node8.fill(null, node11);
        node9.fill(node12, null);
        node11.fill(node13, node14);

    }

    /**
     * 标准一
     */
    private static void printTree1(TreeNode root) {

    }

    /**
     * 标准二
     */
    private static void printTree2(TreeNode root) {

    }
}
