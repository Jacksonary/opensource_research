package com.zhaogang.other.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 自定义锁
 * 
 * @author jacks
 * @date 2021/11/25
 */
public class CustomLock implements Lock {

    private CustomSynchronizer customSynchronizer;

    public CustomLock(CustomSynchronizer customSynchronizer) {
        this.customSynchronizer = customSynchronizer;
    }

    @Override
    public void lock() {
        customSynchronizer.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        customSynchronizer.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return customSynchronizer.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return customSynchronizer.tryAcquireNanos(1, TimeUnit.NANOSECONDS.convert(time, unit));
    }

    @Override
    public void unlock() {
        customSynchronizer.release(0);
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
