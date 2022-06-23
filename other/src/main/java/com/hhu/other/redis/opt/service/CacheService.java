package com.hhu.other.redis.opt.service;

import java.util.List;
import java.util.Set;

/**
 * @author jacks
 * @date 2022/2/22
 */
public interface CacheService {

    <T> T getObject(String key, Class<T> clazz);

    void putObject(String key, Object value);

    /**
     * @param expires
     *            单位：秒
     */
    void putObject(String key, Object value, long expires);

    String get(String key);

    /**
     * @param expires
     *            单位：秒
     */
    void put(String key, String value, long expires);

    /**
     * 设置，默认 10 min 有效期
     */
    void put(String key, String value);

    /**
     * 删除
     */
    void remove(String key);

    /**
     * 设置失效时间
     */
    void expire(String key, final long timeout);

    /**
     * key对应的 value 自增
     *
     * @param delta
     *            增加的阈值
     */
    Long increment(String key, long delta);

    /**
     * key对应的value自增
     */
    String getIncrementValue(String key);

    /**
     * 查询这个key是否存在
     */
    Boolean hasKey(String key);

    /**
     * 查询key的有效时间
     */
    Long getExpire(String key);

    /**
     * List 操作
     */
    <T> void leftPush(String key, T data);

    <T> void batchLeftPush(String key, List<T> data);

    <T> void rightPush(String key, T data);

    <T> void rightBatchPush(String key, List<T> data);

    Set<String> scan(String format, int i);

    <T> List<T> multiGetObject(List<String> var1, Class<T> var2);
}
