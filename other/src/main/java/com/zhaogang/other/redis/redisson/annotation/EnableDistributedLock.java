package com.zhaogang.other.redis.redisson.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author jacks
 * @date 2022/2/22
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableDistributedLock {

    /**
     * @formatter:off 
     * 建议以业务含义的方式命名 如 risk_count_job
     * 注：同一应用内全局唯一，务必定义常量类来进行统一维护！！
     * @formatter:on 
     * @return lock的名字
     */
    String lock() default "default_lock";

}
