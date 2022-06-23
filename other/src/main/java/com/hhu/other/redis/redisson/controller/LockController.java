package com.hhu.other.redis.redisson.controller;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author weiguo.liu
 * @date 2021/4/25
 * @description
 */
@RestController
@RequestMapping("/redisson")
public class LockController {
    private static final String DEFAULT_LOCK_KEY = "order_lock";
    /**
     * 设置生命周期为10分钟
     */
    private static final Long WAIT_TIME = 30L;
    private static final Long LEASE_TIME = 10 * 60L;
    private static final Logger LOGGER = LoggerFactory.getLogger(LockController.class);
    @Resource
    private RedissonClient redissonClient;

    @GetMapping("/lock")
    public void lock() throws InterruptedException {
        RLock lock = redissonClient.getLock(DEFAULT_LOCK_KEY);
        // boolean tryLock;
        // try {
        // tryLock = lock.tryLock(WAIT_TIME, LEASE_TIME, TimeUnit.SECONDS);
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // LOGGER.warn(">> try lock error");
        // return;
        // }
        //
        // if (tryLock) {
        // LOGGER.info(">> tryLock success, key: {}", DEFAULT_LOCK_KEY);
        // }

        // 不显式设置释放时间时间才会促发看门狗，看门狗会自动延迟锁的时间，默认每隔30秒检测一下
         boolean b = lock.tryLock(2, TimeUnit.SECONDS);
        if (b) {
            LOGGER.info(">> getLock success");
        } else {
            LOGGER.warn(">> getLock failed");
        }
        try {
            TimeUnit.SECONDS.sleep(60);
        } catch (InterruptedException e) {
            e.printStackTrace();
            LOGGER.warn(">> sleep failed");
        }

        // lock.unlock();
        // LOGGER.warn(">> lock was released");
        LOGGER.info("done");
    }
}
