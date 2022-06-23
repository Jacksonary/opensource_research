package com.hhu.other.unsafe;

import java.util.function.Consumer;

/**
 * @author weiguo.liu
 * @date 2021/5/11
 * @description
 */
public class Lambda {
    public static void main(String[] args) {
        Consumer<String> cs = System.out::println;
        cs.accept("test");
    }
}
