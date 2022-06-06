package com.hhu.bilibili.tree;

/**
 * @author jacks
 * @date 2021/10/9
 * @description 网格遍历
 */
public class NetworkTreeIterator {
    public static void main(String[] args) {
        NetworkNode node1 = new NetworkNode(1);
        NetworkNode node2 = new NetworkNode(2);
        NetworkNode node3 = new NetworkNode(3);
        NetworkNode node4 = new NetworkNode(4);
        NetworkNode node5 = new NetworkNode(5);
        NetworkNode node6 = new NetworkNode(6);
        NetworkNode node7 = new NetworkNode(7);
        NetworkNode node8 = new NetworkNode(8);
        NetworkNode node9 = new NetworkNode(9);

        node1.fill(null, node4, null, node2);
        node2.fill(null, node5, node1, node3);
        node3.fill(null, node6, node2, null);
        node4.fill(node1, node7, null, node5);
        node5.fill(node2, node8, node4, node6);
        node6.fill(node3, node9, node5, null);
        node7.fill(node4, null, null, node5);
        node8.fill(node5, null, node7, node9);
        node9.fill(node6, null, node8, null);

        // dfs(node1);

        // dfs
        // int[][] arr = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        // dfsWithArray(arr, 0, 0);

        // 最大面积，0-海洋 1-陆地
        int[][] arr1 = new int[][] {{1, 0, 1, 1}, {1, 0, 1, 0}, {1, 1, 1, 1}};
//        System.out.println(getMaxSize(arr1));
//
//        System.out.println(getMaxSize2(arr1));
//
        System.out.println(getMaxLength(arr1));

        System.out.println("Aa".hashCode());
        System.out.println("BB".hashCode());
    }

    private static void dfs(NetworkNode root) {
        if (root == null || root.value == -1) {
            return;
        }

        System.out.print(root.value + " ");
        root.value = -1;

        dfs(root.high);
        dfs(root.low);
        dfs(root.left);
        dfs(root.right);
    }

    private static void dfsWithArray(int[][] arr, int x, int y) {
        if (arr == null || x < 0 || y < 0 || x >= arr.length || y >= arr[0].length) {
            return;
        }

        if (arr[x][y] == -1) {
            return;
        }

        System.out.print(arr[x][y] + " ");
        arr[x][y] = -1;
        dfsWithArray(arr, x - 1, y);
        dfsWithArray(arr, x + 1, y);
        dfsWithArray(arr, x, y - 1);
        dfsWithArray(arr, x, y + 1);
    }

    private static int getPreMax(int[][] arr, int x, int y) {
        if (x < 0 || y < 0 || arr == null || x >= arr.length || y >= arr[0].length) {
            return 0;
        }

        if (arr[x][y] != 1) {
            return 0;
        }
        arr[x][y] = 0;
        return 1 + getPreMax(arr, x - 1, y) + getPreMax(arr, x + 1, y) + getPreMax(arr, x, y - 1)
            + getPreMax(arr, x, y + 1);
    }

    private static int getMaxSize(int[][] arr) {
        if (arr == null) {
            return 0;
        }

        int re = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                re = Math.max(re, getPreMax(arr, i, j));
            }
        }

        return re;
    }

    public static int getMaxSize2(int[][] grid) {
        int res = 0;
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c] == 1) {
                    int a = area(grid, r, c);
                    res = Math.max(res, a);
                }
            }
        }
        return res;
    }

    static int area(int[][] grid, int r, int c) {
        if (!inArea(grid, r, c)) {
            return 0;
        }
        if (grid[r][c] != 1) {
            return 0;
        }
        grid[r][c] = 2;

        return 1 + area(grid, r - 1, c) + area(grid, r + 1, c) + area(grid, r, c - 1) + area(grid, r, c + 1);
    }

    static boolean inArea(int[][] grid, int r, int c) {
        return 0 <= r && r < grid.length && 0 <= c && c < grid[0].length;
    }

    private static int getMaxLength(int[][] arr) {
        if (arr == null) {
            return 0;
        }

        int re = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                re += getPreLen(arr, i, j);
            }
        }
        return re;
    }

    private static int getPreLen(int[][] arr, int x, int y) {
        if (arr == null || x < 0 || y < 0 || x >= arr.length || y >= arr[0].length || arr[x][y] == 2) {
            return 1;
        }

        arr[x][y] = 2;

        return getPreLen(arr, x, y + 1) + getPreLen(arr, x, y - 1) + getPreLen(arr, x - 1, y)
            + getPreLen(arr, x + 1, y);
    }

}
