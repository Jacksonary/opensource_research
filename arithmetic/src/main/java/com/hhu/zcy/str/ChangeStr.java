package com.hhu.zcy.str;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 给定两个字符串 str1 和 str2，如果 str1 和 str2 中出现的字符种类一样且每种字符出现的次 数也一样，那么 str1 与 str2 互为变形词。请实现函数判断两个字符串是否互为变形词
 * 
 * @author jacks
 * @date 2022/7/1
 */
public class ChangeStr {

    public static void main(String[] args) {
        System.out.println(isChangeWord("123", "231"));

        System.out.println(new BigDecimal("77").setScale(2, RoundingMode.HALF_UP));
    }

    private static boolean isChangeWord(String str1, String str2) {
        if (str1 == null || str2 == null) {
            return str2 == null && str1 == null;
        }

        if (str1.length() != str2.length()) {
            return false;
        }

        Map<Character, Integer> map1 = new HashMap<>(str1.length());
        Map<Character, Integer> map2 = new HashMap<>(str1.length());
        for (int i = 0; i < str1.length(); i++) {
            char c1 = str1.charAt(i);
            char c2 = str2.charAt(i);

            Integer i1 = map1.get(c1);
            Integer i2 = map1.get(c1);

            if (i1 == null) {
                i1 = 0;
            }
            map1.put(c1, i1++);
            if (i2 == null) {
                i2 = 0;
            }
            map2.put(c2, i2++);
        }

        if (map1.size() != map2.size()) {
            return false;
        }

        for (Entry<Character, Integer> entry : map1.entrySet()) {
            if (!entry.getValue().equals(map2.get(entry.getKey()))) {
                return false;
            }
        }
        return true;
    }
}
