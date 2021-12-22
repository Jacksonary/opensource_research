package com.zhaogang.other.arithmetic.dp;

/**
 * 给定一个矩阵 m，从左上角开始每次只能向右或者向下走，最后到达右下角的位置，路径上所有的数字累加起来就是路径和，返回所有的路径中最小的路径和
 * 
 * @author jacks
 * @date 2021/12/1
 */
public class MatrixLen {
    public static void main(String[] args) {
        int[][] matrix = new int[][] {{1, 3, 5, 9}, {8, 1, 3, 4}, {5, 0, 6, 1}, {8, 8, 4, 0}};
        int minSum = getMinSum(matrix);
        System.out.println(minSum);
    }

    private static int getMinSum(int[][] matrix) {
        return 0;
    }
}
