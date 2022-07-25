package com.hhu.zcy.str;

/**
 * 如果一个字符串为 str，把字符串 str 前面任意的部分挪到后面形成的字符串叫作 str 的旋转 词。比如 str="12345"，str
 * 的旋转词有"12345"、"23451"、"34512"、"45123"和"51234"。给定两 个字符串 a 和 b，请判断 a 和 b 是否互为旋转词。 【举例】 a="cdab"，b="abcd"，返回 true。
 * a="1ab2"，b="ab12"，返回 false。 a="2ab1"，b="ab12"，返回 true
 * 
 * @author jacks
 * @date 2022/7/2
 */
public class RotatingWord {

    public static void main(String[] args) {
        System.out.println(isPre("2ab1", "ab12"));
        System.out.println(isPre2("2ab1", "ab12"));

        System.out.println(isPre("cdab", "abcd"));
        System.out.println(isPre2("cdab", "abcd"));

        System.out.println(isPre("1ab2", "ab12"));
        System.out.println(isPre2("1ab2", "ab12"));
    }

    /**
     * 直接利用字串进行解析
     * 
     * @param str1
     * @param str2
     * @return
     */
    private static boolean isPre(String str1, String str2) {
        if (str1 == null) {
            return str2 == null;
        }

        if (str2 == null) {
            return false;
        }

        if (str1.length() == 0) {
            return str2.length() == 0;
        }
        if (str1.length() != str2.length()) {
            return false;
        }

        char char2 = str2.charAt(0);
        int starIndex = 0;
        while (true) {
            int index1 = str1.indexOf(char2, starIndex);
            if (index1 == -1) {
                return false;
            }

            // str1 尝试从 index1 开始向后 | index1 -> str1.length-1 | 0 ->
            String substring1 = str1.substring(index1, str1.length());
            String substring2 = str2.substring(0, substring1.length());
            if (!substring1.equals(substring2)) {
                starIndex++;
                continue;
            }

            return str2.substring(substring1.length()).equals(str1.substring(0, index1));
        }
    }

    /**
     * 方法2: 直接将 str2 double, 寻找 str1 字串
     * @param str1
     * @param str2
     * @return
     */
    private static boolean isPre2(String str1, String str2) {
        if (str1 == null) {
            return str2 == null;
        }

        if (str2 == null) {
            return false;
        }

        String mixStr2 = str2 + str2;

        return mixStr2.contains(str1);
    }
}
