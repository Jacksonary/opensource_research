package com.zhaogang.other.arithmetic.dp;

/**
 * @formatter:off 
 * 给定一个矩阵 m，从左上角开始每次只能向右或者向下走，最后到达右下角的位置，
 * 路径上所有的数字累加起来就是路径和，返回所有的路径中最小的路径和
 *
 * 如果给定的 m 如下：
 * 1 3 5 9
 * 8 1 3 4
 * 5 0 6 1
 * 8 8 4 0
 * 
 * 路径 1，3，1，0，6，1，0 是所有路径中路径和最小的，所以返回 12
 * @formatter:on 
 *
 * @author jacks
 * @date 2021/12/14
 */
public class MatrixMinLen {
    public static void main(String[] args) {
        int[][] matrix = new int[][] {{1, 3, 5, 9}, {8, 1, 3, 4}, {5, 0, 6, 1}, {8, 8, 4, 0}};

        System.out.println(getMinLen(matrix, 3, 3));
        System.out.println(getMinLen2(matrix, 1, 2));
    }

    /**
     * @formatter:off 
     * 方式1：直接求出最小长度矩阵
     *
     * 指定位置的最小长度的路径可以推导为下面2种方式：
     * 1. 指定节点的上一个节点向下走一格 + 指定节点长度
     * 2. 指定节点的左一个节点向右走一格 + 指定节点长度
     * 公式可以表达为 minLen[i][j] = min(minLen[i-1][j], minLen[i][j-1) + matrix[i][j];
     * 
     * 首行最小长度只能从它的左节点到达
     * 首列最小长度只能从它的上节点到达
     * @formatter:on 
     */
    private static int getMinLen(int[][] matrix, int rowIndex, int colIndex) {
        if (matrix == null) {
            return 0;
        }

        int row = matrix.length;
        int col = matrix[0].length;
        int[][] minLenMatrix = new int[row][col];
        minLenMatrix[0][0] = matrix[0][0];
        // 构建首行和首列
        // 首行
        for (int i = 1; i < col; i++) {
            minLenMatrix[0][i] = minLenMatrix[0][i - 1] + matrix[0][i];
        }
        // 首列
        for (int i = 1; i < row; i++) {
            minLenMatrix[i][0] = minLenMatrix[i - 1][0] + matrix[i][0];
        }

        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                minLenMatrix[i][j] = Math.min(minLenMatrix[i - 1][j], minLenMatrix[i][j - 1]) + matrix[i][j];
            }
        }

        return minLenMatrix[rowIndex][colIndex];
    }

    /**
     * 使用滚动数组替换矩阵，空间复杂度从 O(M*N) 降到 O(M)
     */
    public static int getMinLen2(int[][] matrix, int rowIndex, int colIndex) {
        if (matrix == null) {
            return 0;
        }

        int row = matrix.length;
        int col = matrix[0].length;
        int[] helper = new int[col];

        for (int i = 0; i < col; i++) {
            if (i == 0) {
                helper[i] = matrix[0][0];
                continue;
            }
            helper[i] = helper[i - 1] + matrix[0][i];
        }

        // 遍历行
        for (int i = 1; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (j == 0) {
                    helper[j] = helper[0] + matrix[i][0];
                    continue;
                }
                helper[j] = Math.min(helper[j - 1], helper[j]) + matrix[i][j];
            }
        }

        return helper[col - 1];
    }
}
