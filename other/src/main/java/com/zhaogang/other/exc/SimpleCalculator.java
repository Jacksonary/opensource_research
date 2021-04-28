package com.zhaogang.other.exc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author weiguo.liu
 * @date 2021/1/15
 * @description
 */
public class SimpleCalculator {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleCalculator.class);
    // static int undefined = 1 / 0;

    static {
        int i = 5 / 0;
        System.out.println("static ...");
    }

    public SimpleCalculator() {
        System.out.println("constructor...");
    }

    public static String test() {
        return "test";
    }
}
