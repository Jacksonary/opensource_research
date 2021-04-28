package com.zhaogang.other.main;

import com.alibaba.fastjson.JSON;
import com.zhaogang.other.bean.User;

/**
 * @author weiguo.liu
 * @date 2020/11/28
 * @description
 */
public class Main {

    public static void main(String[] args) {
        User user = new User();
        user.setId(1L);
        user.setName("name1");

        User user2 = new User();
        user2.setId(2L);
        user2.setName("name2");

        // swapObject(user, user2);
        swapName(user, user2);

        System.out.println(user);
        System.out.println(user2);

        System.out.println("success done");

        String s = JSON.toJSONString(null);
        System.out.println(s);
    }

    /**
     *
     * @param from
     * @param to
     */
    private static void swapObject(User from, User to) {
        User tmp = from;
        from = to;
        to = tmp;
        System.out.println(from);
        System.out.println(to);
    }

    private static void swapName(User from, User to) {
        String tmp = from.getName();
        from.setName(to.getName());
        to.setName(tmp);
        System.out.println(from);
        System.out.println(to);
    }

}
