package com.hhu.bilibili.pattern;

/**
 * @author jacks
 * @date 2021/11/10
 * @description
 */
public class Singleton {

    private static final Object watcher = new Object();
    private static volatile Singleton instance;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }

        return instance;
    }
}
