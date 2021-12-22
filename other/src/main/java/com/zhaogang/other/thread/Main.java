package com.zhaogang.other.thread;

import java.text.MessageFormat;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author weiguo.liu
 * @date 2021/4/1
 * @description 线程池的几个参数实验
 */
public class Main {

    private static ThreadPoolExecutor executorService = new ThreadPoolExecutor(5, 10, 1, TimeUnit.SECONDS,
        new LinkedBlockingQueue<>(5), new CustomThreadFactory(), new AbortPolicy());

    public static void main(String[] args) throws Exception {
        // executorService.allowCoreThreadTimeOut(true);
        for (int j = 0; j < 10; j++) {
            executorService.execute(() -> {
                System.out.println(Thread.currentThread().getId() + ": " + Thread.currentThread().getName() + " -> "
                    + System.currentTimeMillis() + getThreadInfo());
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        // LocalDateTime now = LocalDateTime.now();
        // while (LocalDateTime.now().getMinute() - now.getMinute() < 5) {
        // printThreadPoolInfo();
        // TimeUnit.SECONDS.sleep(1);
        // }

        // TimeUnit.SECONDS.sleep(10);
        System.out.println("============= done ==================");
        for (int i = 0; i < 30; i++) {
            System.out.println(getThreadInfo());
            TimeUnit.SECONDS.sleep(1);
        }
    }

    private static void printThreadPoolInfo() {
        System.out
            .println(MessageFormat.format(">> poolSize: {0}, activeCount: {1}, completedTaskCount: {2}, queueSize: {3}",
                executorService.getPoolSize(), executorService.getActiveCount(),
                executorService.getCompletedTaskCount(), executorService.getQueue().size()));
    }

    private static String getThreadInfo() {
        return " active: " + executorService.getActiveCount() + ", completed: "
            + executorService.getCompletedTaskCount() + ", core: " + executorService.getCorePoolSize()
            + ", terminating: " + executorService.isTerminating();
    }

    static class CustomThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        CustomThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = "pool-" + poolNumber.getAndIncrement() + "-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }

}
