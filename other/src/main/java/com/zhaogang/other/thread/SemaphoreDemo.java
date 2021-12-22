package com.zhaogang.other.thread;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import lombok.SneakyThrows;

/**
 * @author jacks
 * @date 2021/12/2
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();
        ExecutorService executor =
            new ThreadPoolExecutor(8, 10, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<>(100), threadFactory);

        Semaphore semaphore = new Semaphore(4);
        for (int i = 0; i < 10; i++) {
            executor.execute(new SemaphoreRunnable(i, semaphore));
        }
    }

    private static class SemaphoreRunnable implements Runnable {

        private int value;
        private Semaphore semaphore;

        public SemaphoreRunnable(int value, Semaphore semaphore) {
            this.value = value;
            this.semaphore = semaphore;
        }

        @SneakyThrows
        @Override
        public void run() {
            semaphore.acquire();
            System.out.printf("current: %d, queueLen: %d, available: %d%n", value, semaphore.getQueueLength(),
                semaphore.availablePermits());
            TimeUnit.SECONDS.sleep(new Random(System.currentTimeMillis()).nextInt(10));
            semaphore.release();
        }
    }

}
