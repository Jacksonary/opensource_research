package com.hhu.zcy.str;

/**
 * @formatter:off
 * 给定一个字符串 str，返回 str 的统计字符串。例如，"aaabbadddffc"的统计字符串为
 * "a_3_b_2_a_1_d_3_f_2_c_1"。
 * 补充问题：给定一个字符串的统计字符串 cstr，再给定一个整数 index，返回 cstr 所代表的
 * 原始字符串上的第 index 个字符。例如，"a_1_b_100"所代表的原始字符串上第 0 个字符是'a'，
 * 第 50 个字符是'b'
 * @formatter:on
 * @author jacks
 * @date 2022/7/2
 */
public class StrStatics {

    public static void main(String[] args) {
        System.out.println(getStaticsStr("aaabbadddffc"));
        System.out.println(getCharIndex("a_1_b_100", 50));
    }

    private static String getStaticsStr(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }

        StringBuilder sb = new StringBuilder();
        char preStr = str.charAt(0);
        int preCount = 0;
        for (int i = 0; i < str.length(); i++) {
            char cur = str.charAt(i);
            if (cur == preStr) {
                preCount++;
                continue;
            }

            sb.append(preStr).append("_").append(preCount).append("_");
            preStr = cur;
            preCount = 1;

            if (i == str.length() - 1) {
                sb.append(preStr).append("_").append(preCount);
            }
        }
        return sb.toString();
    }

    private static char getCharIndex(String staticStr, int index) {
        if (staticStr == null || staticStr.length() == 0) {
            return 0;
        }

        int total = 0;
        for (int i = 2; i < staticStr.length(); i = i + 4) {
            int i1 = staticStr.indexOf('_', i);
            int numStartIndex = i;
            int numEndIndex = i + 1;
            if (i1 == -1) {
                numEndIndex = staticStr.length();
            }
            int num = Integer.parseInt(staticStr.substring(numStartIndex, numEndIndex));

            if (total + num >= index) {
                return staticStr.charAt(i - 2);
            }
        }

        return 0;
    }
}
