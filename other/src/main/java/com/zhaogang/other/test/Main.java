package com.zhaogang.other.test;

import com.google.common.collect.ImmutableMap;

/**
 * @author jacks
 * @date 2021/12/30
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("abcdef".compareTo("ABCDEF"));
        ImmutableMap<Integer, Integer> map = ImmutableMap.of(0x1003, 0x9003);
        System.out.println(map.get(0x1003));
        System.out.println(0x9003);
    }
}
