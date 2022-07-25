package com.hhu.zcy.str;

/**
 * @formatter:off
 * 给定一个字符串数组 strs[]，在 strs 中有些位置为 null，但在不为 null 的位置上，其字符串
 * 是按照字典顺序由小到大依次出现的。再给定一个字符串 str，请返回 str 在 strs 中出现的最左
 * 的位置。
 * 【举例】
 * strs=[null,"a",null,"a",null,"b",null,"c"]，str="a"，返回 1。
 * strs=[null,"a",null,"a",null,"b",null,"c"]，str=null，只要 str 为 null，就返回-1。
 * strs=[null,"a",null,"a",null,"b",null,"c"]，str="d"，返回-1
 * @formatter:on
 * @author jacks
 * @date 2022/7/6
 */
public class StrFind {

    public static void main(String[] args) {
        String[] strs = new String[] {null, "a", null, "a", null, "b", null, "c"};
        String str = "c";
        // System.out.println(findLeftIndex(strs, str));
        System.out.println(findLeftIndex2(strs, str));
    }

    private static int findLeftIndex(String[] strs, String str) {
        if (strs == null || strs.length == 0 || str == null) {
            return -1;
        }

        for (int i = 0; i < strs.length; i++) {
            String indexStr = strs[i];

            if (str.equals(indexStr)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 有序数列利用二分法
     */
    private static int findLeftIndex2(String[] strs, String str) {
        if (strs == null || strs.length == 0 || str == null) {
            return -1;
        }

        int left = 0;
        int right = strs.length;

        // 寻找中位点
        int mid = (left + right) / 2;
        // 首次寻址，先向左再向右
        int tarIndex = getNoNullIndex(strs, mid, true);
        if (tarIndex == -1) {
            tarIndex = getNoNullIndex(strs, mid, false);
        }
        if (tarIndex == -1) {
            return -1;
        }

        while (left < right) {
            int compare = strs[tarIndex].compareTo(str);
            if (compare == 0) {
                return tarIndex;
            }

            if (compare > 0) {
                right = tarIndex;
                tarIndex = (left + right) / 2;
                continue;
            }

            left = tarIndex;
            tarIndex = (left + right) / 2;
        }

        return -1;
    }

    private static int getNoNullIndex(String[] strs, int start, boolean left) {
        if (strs[start] != null) {
            return start;
        }

        if (left) {
            while (start >= 0) {
                if (strs[start] != null) {
                    return start;
                }
                start--;
            }
            return -1;
        }

        while (start < strs.length) {
            if (strs[start] != null) {
                return start;
            }
            start++;
        }
        return -1;
    }

}
