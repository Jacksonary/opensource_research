package com.hhu.bilibili.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 顺势针打印二维数组
 * 
 * @author jacks
 * @date 2021/12/24
 */
public class SpiralOrder {
    public static void main(String[] args) {
        spiralOrder(new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
    }

    public static List<Integer> spiralOrder(int[][] matrix) {
        if (matrix == null) {
            return new ArrayList<Integer>();
        }

        boolean right = true;
        boolean down = true;
        boolean level = true;
        int rightLow = 0;
        int rightHight = matrix[0].length;
        int downLow = 0;
        int downHight = matrix.length;

        while (true) {
            if (level) {
                // 水平方向
                if (right) {
                    // 向右，注意排除拐点位置
                    for (int j = downLow; j < downHight; j++) {
                        System.out.print(matrix[rightLow][j] + " ");
                    }
                } else {
                    // 向左，注意排除拐点位置
                    for (int j = rightHight - 1; j > rightLow; j--) {
                        System.out.print(matrix[rightHight - 1][j] + " ");
                    }

                    // 向左遍历后代表向下两行已经遍历完
                    rightLow++;
                    rightHight--;
                }
                level = !level;
                right = !right;
            } else {
                // 垂直方向
                if (down) {
                    // 向下，注意排除拐点位置
                    for (int i = downLow + 1; i < downHight; i++) {
                        System.out.print(matrix[i][rightHight - 1] + " ");
                    }
                } else {
                    // 向上，注意排除拐点位置
                    for (int i = downHight - 1; i > downLow; i--) {
                        System.out.print(matrix[i][rightHight - 1] + " ");
                    }
                    // 向上遍历代表左右两列已经遍历完
                    downLow++;
                    downHight--;
                }
                level = !level;
                down = !down;
            }
        }
    }
}
