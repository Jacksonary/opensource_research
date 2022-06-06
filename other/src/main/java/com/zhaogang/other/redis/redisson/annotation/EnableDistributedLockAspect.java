package com.zhaogang.other.redis.redisson.annotation;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author jacks
 * @date 2022/2/22
 */
@Aspect
//@Component
public class EnableDistributedLockAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(EnableDistributedLockAspect.class);

    private static final Long WAIT_TIME = 2L;
    private static final String KEY_TEMPLATE = "%s:lock:%s";

    @Value("${spring.application.name}")
    private String namespace;
    @Resource
    private RedissonClient redissonClient;

    // 不写全路径可能不生效
    // @Around("@annotation(EnableDistributedLock)")
    @Around("@annotation(com.zhaogang.other.redis.redisson.annotation.EnableDistributedLock)")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature)joinPoint.getSignature()).getMethod();
        EnableDistributedLock enableDistributedLock = method.getAnnotation(EnableDistributedLock.class);

        String lockKey = String.format(KEY_TEMPLATE, namespace, enableDistributedLock.lock());
        RLock rLock = redissonClient.getLock(lockKey);
        boolean lock = rLock.tryLock(WAIT_TIME, TimeUnit.SECONDS);

        if (!lock) {
            LOGGER.warn(">> get lock failed, key: {}", lockKey);
            return null;
        }

        LOGGER.info(">> get lock successfully, key: {}", lockKey);
        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            throw e;
        } finally {
            rLock.unlock();
            LOGGER.info(">> lock released, key: {}", lockKey);
        }
    }

}
