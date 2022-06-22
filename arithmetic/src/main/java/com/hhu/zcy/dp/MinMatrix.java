package com.hhu.zcy.dp;

/**
 * 给定一个矩阵 m，从左上角开始每次只能向右或者向下走，最后到达右下角的位置，路径 上所有的数字累加起来就是路径和，返回所有的路径中最小的路径和。
 * 
 * @author jacks
 * @date 2022/6/17
 */
public class MinMatrix {
    public static void main(String[] args) {
        int[][] arr = new int[][] {{1, 3, 5, 9}, {8, 1, 3, 4}, {5, 0, 6, 1}, {8, 8, 4, 0}};
        System.out.println(minStep(arr));
        System.out.println(minStep2(arr));
    }

    /**
     * 还是构建 dp 矩阵，计算出每个位置的最小最短路径，因为只能向右或向下， 故最短路径要么经过目标中点的上方或者左方
     * 
     * T - O(M*N) S - O(M*N)
     */
    private static int minStep(int[][] arr) {
        if (arr == null) {
            return 0;
        }

        int[][] step = new int[arr.length][arr[0].length];
        // 0行
        for (int i = 0; i < arr[0].length; i++) {
            step[0][i] = i == 0 ? arr[0][0] : arr[0][i] + step[0][i - 1];
        }
        // 0 列
        for (int i = 1; i < arr.length; i++) {
            step[i][0] = arr[i][0] + step[i - 1][0];
        }

        // 构建其他行列
        for (int i = 1; i < arr.length; i++) {
            for (int j = 1; j < arr[i].length; j++) {
                step[i][j] = Math.min(step[i - 1][j], step[i][j - 1]) + arr[i][j];
            }
        }

        return step[arr.length - 1][arr[0].length - 1];
    }

    /**
     * 可以将上述方式利用滚动数组的思想将空间复杂度下降
     * 
     * @param arr
     * @return
     */
    private static int minStep2(int[][] arr) {
        int colLen = arr[0].length;
        int[] minStep = new int[colLen];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < colLen; j++) {
                if (i == 0) {
                    minStep[j] = j == 0 ? arr[i][j] : minStep[j - 1] + arr[i][j];
                    continue;
                }

                minStep[j] = j == 0 ? minStep[j] + arr[i][j] : Math.min(minStep[j], minStep[j - 1]) + arr[i][j];
            }
        }
        return minStep[colLen - 1];
    }
}
