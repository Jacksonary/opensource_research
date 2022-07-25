package com.hhu.zcy.str;

/**
 * @formatter:off
 * 给定一个字符串 str，如果 str 符合日常书写的整数形式，并且属于 32 位整数的范围，返回
 * str 所代表的整数值，否则返回 0。
 * 【举例】
 * str="123"，返回 123。
 * str="023"，因为"023"不符合日常的书写习惯，所以返回 0。
 * str="A13"，返回 0。
 * str="0"，返回 0。
 * str="2147483647"，返回 2147483647。
 * str="2147483648"，因为溢出了，所以返回 0。
 * str="-123"，返回-123
 * @formatter:on 
 * @author jacks
 * @date 2022/7/2
 */
public class Str2Num {

    public static void main(String[] args) {
        System.out.println(getNum("-123"));
    }

    private static int getNum(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }

        if (str.startsWith("0") && str.length() != 1) {
            return 0;
        }

        String max = String.valueOf(Integer.MAX_VALUE);
        int maxLen = max.length();
        String min = String.valueOf(Integer.MIN_VALUE);
        int minLen = min.length();
        if (str.length() > minLen) {
            return 0;
        }

        boolean isNegative = str.charAt(0) == '-';
        int start = isNegative ? 1 : 0;
        boolean isPossibleOver = isNegative ? str.length() == minLen : str.length() == maxLen;

        for (int i = start; i < str.length(); i++) {
            if (str.charAt(i) < '0' || str.charAt(i) > '9') {
                return 0;
            }

            if (!isPossibleOver) {
                continue;
            }

            if (isNegative) {
                if (str.charAt(i) > min.charAt(i)) {
                    return 0;
                }
            } else {
                if (str.charAt(i) > max.charAt(i)) {
                    return 0;
                }
            }
        }

        return Integer.parseInt(str);
    }
}
