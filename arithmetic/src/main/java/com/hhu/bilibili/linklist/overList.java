package com.hhu.bilibili.linklist;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author jacks
 * @date 2021/10/26
 * @description 链表的假溢出
 */
public class overList {
    public static void main(String[] args) {
        Queue<Integer> list = new LinkedList<>();
        for (int i = 0; i < 4; i++) {
            list.add(i);
        }

        System.out.println(list);
    }
}
