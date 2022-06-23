package com.hhu.other.redis.transaction;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

/**
 * @author jacks
 * @date 2022/1/14
 */
@Service("multiServiceImpl")
public class MultiServiceImpl implements MultiService {
    @Resource
    private RedisTemplate<String, String> stringRedisTemplate;

    /**
     * @formatter:off 
     * 关于事务的API
     *
     * MULTI 开启事务
     * EXEC 执行任务队列里所有命令，并结束事务
     * DISCARD 放弃事务，清空任务队列，全部不执行，并UNWATCH
     * WATCH key [key1] MULTI执行之前，指定监控某key，如果key发生修改，放弃整个事务执行 UNWATCH 手动取消监控
     * @formatter:on 
     */
    @Override
    public void multiOpt() {
        // stringRedisTemplate.setEnableTransactionSupport(true);
        // stringRedisTemplate.multi();
        // stringRedisTemplate.opsForValue().set("aws_test:count1", "v1");
        // stringRedisTemplate.opsForValue().set("aws_test:count2", "v2");
        // stringRedisTemplate.opsForValue().set("aws_test:count3", "v3");
        // // stringRedisTemplate.exec();
        // stringRedisTemplate.discard();

        // 这里操作和原生的不太一样
        stringRedisTemplate.execute(new SessionCallback<Object>() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                redisOperations.multi();
                redisOperations.opsForValue().set("aws_test:count1", "v1");
                redisOperations.opsForValue().set("aws_test:count2", "v2");
                redisOperations.opsForValue().set("aws_test:count3", "v3");
                // return redisOperations.exec();
                redisOperations.discard();
                return null;
            }
        });
    }
}
