package com.zhaogang.other.lock.distribute;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhaogang.other.zk.curator.factory.ZKFactory;

/**
 * @author weiguo.liu
 * @date 2021/4/28
 * @description
 */
public class ZKLock implements Lock {
    private static final String DEFAULT_LOCK_PATH = "/lock";
    private static final Logger LOGGER = LoggerFactory.getLogger(ZKLock.class);

    /**
     * 在实例化时同时初始化LOCK目录
     */
    public ZKLock() {
        CuratorFramework curatorFramework = ZKFactory.get();
        try {
            Stat stat = curatorFramework.checkExists().forPath(DEFAULT_LOCK_PATH);
            // 存在无须初始化
            if (stat != null) {
                return;
            }
            curatorFramework.createContainers(DEFAULT_LOCK_PATH);
        } catch (Exception e) {
            LOGGER.warn(">> zk error -> check status failed");
            e.printStackTrace();
        }
    }

    @Override
    public void lock() {
        CuratorFramework curatorFramework = ZKFactory.get();

    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {

    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
