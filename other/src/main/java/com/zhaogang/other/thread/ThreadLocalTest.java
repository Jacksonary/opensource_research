package com.zhaogang.other.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.DiscardPolicy;
import java.util.concurrent.TimeUnit;

import jodd.util.concurrent.ThreadFactoryBuilder;

/**
 * ThreadLocal 可能存在共享变量的问题，不能瞎用
 * 
 * @author jacks
 * @date 2021/12/21
 */
public class ThreadLocalTest {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS, new LinkedBlockingQueue<>(100),
            ThreadFactoryBuilder.create().setNameFormat("test-pool-%d").get(), new DiscardPolicy());
        executor.allowCoreThreadTimeOut(true);

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            executor.execute(() -> {
                ThreadLocalHolder.get();
                if (ThreadLocalHolder.get() == null) {
                    ThreadLocalHolder.set("threadStr: " + finalI);
                }
                Thread currentThread = Thread.currentThread();
                System.out.println(currentThread.getName() + "_" + ThreadLocalHolder.get());
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

    }

    public static class ThreadLocalHolder {
        private static final ThreadLocal<String> str = new ThreadLocal<>();

        public static void set(String content) {
            str.set(content);
        }

        public static String get() {
            return str.get();
        }
    }
}
