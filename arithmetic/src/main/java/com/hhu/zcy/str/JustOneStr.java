package com.hhu.zcy.str;

/**
 * @formatter:off 
 * 给定一个字符类型数组 chas[]，判断 chas 中是否所有的字符都只出现过一次，请根据以下
 * 不同的两种要求实现两个函数。
 * 【举例】
 * chas=['a','b','c']，返回 true；chas=['1','2','1']，返回 false。
 *
 * 1．实现时间复杂度为 O(N)的方法。
 * 2．在保证额外空间复杂度为 O(1)的前提下，请实现时间复杂度尽量低的方法。
 * @formatter:on 
 * @author jacks
 * @date 2022/7/5
 */
public class JustOneStr {
    public static void main(String[] args) {
        char[] chas = new char[] {'a', 'b', 'c'};
        System.out.println(isAllOne(chas));
        System.out.println(isAllOne2(chas));
    }

    /**
     * T - O(n) | S - O(n)
     * 
     * @param chas
     * @return
     */
    private static boolean isAllOne(char[] chas) {
        if (chas == null || chas.length == 0) {
            return true;
        }

        // 注意使用这种数组作为 map，效率更好
        int[] charCount = new int[256];
        for (int i = 0; i < chas.length; i++) {
            if (charCount[chas[i]] == 1) {
                return false;
            }
            charCount[chas[i]] += 1;
        }

        return true;
    }

    /**
     * 反向重复遍历(常规思路)，但时间复杂度太高
     */
    private static boolean isAllOne2(char[] chas) {
        for (int i = 1; i < chas.length; i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (chas[j] == chas[i]) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * 可以先排序，相同的字符肯定是连在一起的 | 核心是找一个空间复杂度为 O(1) 的排序算法
     * 
     * @return
     */
    private static boolean isAllOne3() {
        return false;
    }

}
