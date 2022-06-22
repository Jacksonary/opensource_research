package com.zhaogang.other.lock;

import java.time.LocalDateTime;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author jacks
 * @date 2022/6/9
 */
public class LockTest {
    public static void main(String[] args) {

        ThreadPoolExecutor executor =
            new ThreadPoolExecutor(10, 10, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>(100));

        CustomLock customLock = new CustomLock(new CustomSynchronizer());

        for (int i = 0; i < 3; i++) {
            executor.execute(() -> {
                boolean lock = customLock.tryLock();
                System.out
                    .println(LocalDateTime.now() + " > " + Thread.currentThread().getName() + " get lock: " + lock);

                while (!lock) {
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    lock = customLock.tryLock();
                    System.out
                        .println(LocalDateTime.now() + " > " + Thread.currentThread().getName() + " get lock: " + lock);
                }

                System.out.println(LocalDateTime.now() + " > " + Thread.currentThread().getName() + " do process...");
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                customLock.unlock();
                System.out.println(LocalDateTime.now() + " > " + Thread.currentThread().getName() + " unlock");
            });
        }
    }
}
