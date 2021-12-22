package com.zhaogang.other.thread;

import lombok.SneakyThrows;

/**
 * @author jacks
 * @date 2021/11/30
 */
public class MutilAdd implements Runnable {
    private static volatile int count = 0;

    @SneakyThrows
    @Override
    public synchronized void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + ": " + count++);
            this.notify();
            this.wait();
        }
    }
}
