package com.zhaogang.other.arithmetic.tree;

import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;

/**
 * 已知一棵二叉树所有的节点值都不同，给定这棵树正确的先序和中序数组，不要重建整棵 树，而是通过这两个数组直接生成正确的后序数组。
 * 
 * @author jacks
 * @date 2021/11/29
 */
public class PostTree {

    private static final Map<Integer, Integer> PRE_VALUE_INDEX_MAP = new HashedMap<>();
    private static final Map<Integer, Integer> MID_VALUE_INDEX_MAP = new HashedMap<>();

    public static void main(String[] args) {
        int[] pre = new int[] {1, 2, 3, 4, 5};
        int[] mid = new int[] {3, 4, 2, 1, 5};

        TreeNode root = buildTree(pre, mid);
    }

    private static TreeNode buildTree(int[] pre, int[] mid) {
        if (pre == null || mid == null) {
            return null;
        }

        Map<Integer, Integer> valueIndexMap = new HashedMap<>(mid.length);
        for (int i = 0; i < mid.length; i++) {
            valueIndexMap.put(mid[i], i);
        }

        // 确定根节点
        int rootValue = pre[0];
        TreeNode root = new TreeNode(rootValue);
        int indexInMid = getIndexInMid(rootValue, mid);

        return null;
    }

    private static int getIndexInPre(int value, int[] pre) {
        if (pre == null) {
            return -1;
        }

        if (PRE_VALUE_INDEX_MAP.size() == 1) {
            for (int i = 0; i < pre.length; i++) {
                PRE_VALUE_INDEX_MAP.put(pre[i], i);
            }
        }

        return PRE_VALUE_INDEX_MAP.get(value);
    }

    // 中序中的索引位置
    private static int getIndexInMid(int value, int[] mid) {
        if (mid == null) {
            return -1;
        }

        if (MID_VALUE_INDEX_MAP.size() == 0) {
            for (int i = 0; i < mid.length; i++) {
                MID_VALUE_INDEX_MAP.put(mid[i], i);
            }
        }

        return MID_VALUE_INDEX_MAP.get(value);
    }
}
