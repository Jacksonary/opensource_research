package com.hhu.other.redis.opt.service.impl;

import com.hhu.other.redis.opt.service.CacheService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author jacks
 * @date 2022/2/22
 */
@Service("cacheService")
public class CacheServiceImpl implements CacheService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CacheServiceImpl.class);

    /**
     * 默认 10 min 失效
     */
    private static final long DEFAULT_CACHE_EXPIRES = 10 * 60;
    private static final int MAX_BATCH_SIZE = 1000;

    @Value("${spring.application.name}")
    private String namespace;
    @Resource
    private RedisTemplate<String, String> redisStringTemplate;

    @Override
    public <T> T getObject(String key, Class<T> clazz) {
        if (StringUtils.isBlank(key) || clazz == null) {
            LOGGER.warn(">> illegal param -> key or clazz is null, key: {}, clazz: {}", key, JSON.toJSONString(clazz));
            return null;
        }
        return JSONObject.parseObject(redisStringTemplate.opsForValue().get(contactKey(key)), clazz);
    }

    @Override
    public void putObject(String key, Object value) {
        if (StringUtils.isBlank(key) || value == null) {
            LOGGER.warn(">> illegal param -> key or value is null, key: {}, value: {}", key, JSON.toJSONString(value));
            return;
        }
        putObject(key, value, DEFAULT_CACHE_EXPIRES);
    }

    @Override
    public void putObject(String key, Object value, long expires) {
        if (StringUtils.isBlank(key) || value == null) {
            LOGGER.warn(">> illegal param -> key or value is null, key: {}, value: {}", key, JSON.toJSONString(value));
            return;
        }
        redisStringTemplate.opsForValue().set(contactKey(key), JSONObject.toJSONString(value), expires,
            TimeUnit.SECONDS);
    }

    @Override
    public String get(String key) {
        if (StringUtils.isBlank(key)) {
            LOGGER.warn(">> illegal param -> key is null, key: {}", key);
            return null;
        }
        return redisStringTemplate.opsForValue().get(contactKey(key));
    }

    @Override
    public void put(String key, String value, long expires) {
        if (StringUtils.isBlank(key) || StringUtils.isBlank(value)) {
            LOGGER.warn(">> illegal param -> key or value is null, key: {}, value: {}", key, value);
            return;
        }

        redisStringTemplate.opsForValue().set(contactKey(key), value, expires, TimeUnit.SECONDS);
    }

    @Override
    public void put(String key, String value) {
        if (StringUtils.isBlank(key) || StringUtils.isBlank(value)) {
            LOGGER.warn(">> illegal param -> key or value is null, key: {}, value: {}", key, value);
            return;
        }
        put(key, value, DEFAULT_CACHE_EXPIRES);
    }

    @Override
    public void remove(String key) {
        if (StringUtils.isBlank(key)) {
            LOGGER.warn(">> illegal param -> key is null, key: {}", key);
            return;
        }
        redisStringTemplate.delete(contactKey(key));
    }

    @Override
    public void expire(String key, long timeout) {
        if (StringUtils.isBlank(key)) {
            LOGGER.warn(">> illegal param -> key is null, key: {}", key);
            return;
        }
        redisStringTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    @Override
    public Long increment(String key, long delta) {
        if (StringUtils.isBlank(key)) {
            LOGGER.warn(">> illegal param -> key is null, key: {}", key);
            return null;
        }

        return redisStringTemplate.boundValueOps(contactKey(key)).increment(delta);
    }

    @Override
    public String getIncrementValue(String key) {
        if (StringUtils.isBlank(key)) {
            LOGGER.warn(">> illegal param -> key is null, key: {}", key);
            return null;
        }
        return redisStringTemplate.boundValueOps(contactKey(key)).get();
    }

    @Override
    public Boolean hasKey(String key) {
        if (StringUtils.isBlank(key)) {
            LOGGER.warn(">> illegal param -> key is null, key: {}", key);
            return Boolean.FALSE;
        }
        return redisStringTemplate.hasKey(contactKey(key));
    }

    @Override
    public Long getExpire(String key) {
        if (StringUtils.isBlank(key)) {
            LOGGER.warn(">> illegal param -> key is null, key: {}", key);
            return 0L;
        }
        return redisStringTemplate.getExpire(contactKey(key));
    }

    @Override
    public <T> void leftPush(String key, T data) {
        if (StringUtils.isBlank(key) || data == null) {
            LOGGER.warn(">> illegal param -> key or data is null, key: {}, data: {}", key, JSON.toJSONString(data));
            return;
        }
        redisStringTemplate.opsForList().leftPush(contactKey(key), JSON.toJSONString(data));
    }

    @Override
    public <T> void batchLeftPush(String key, List<T> data) {
        if (StringUtils.isBlank(key) || CollectionUtils.isEmpty(data)) {
            LOGGER.warn(">> illegal param -> key or data is null, key: {}, data: {}", key, JSON.toJSONString(data));
            return;
        }

        if (data.size() > MAX_BATCH_SIZE) {
            LOGGER.warn(">> batchSize too big, max batch option's size is {}", MAX_BATCH_SIZE);
            return;
        }

        redisStringTemplate.opsForList().leftPushAll(key,
            data.stream().map(JSON::toJSONString).collect(Collectors.toList()));
    }

    @Override
    public <T> void rightPush(String key, T data) {
        if (StringUtils.isBlank(key) || data == null) {
            LOGGER.warn(">> illegal param -> key or data is null, key: {}, data: {}", key, JSON.toJSONString(data));
            return;
        }
        redisStringTemplate.opsForList().rightPush(contactKey(key), JSON.toJSONString(data));
    }

    @Override
    public <T> void rightBatchPush(String key, List<T> data) {
        if (StringUtils.isBlank(key) || CollectionUtils.isEmpty(data)) {
            LOGGER.warn(">> illegal param -> key or data is null, key: {}, data: {}", key, JSON.toJSONString(data));
            return;
        }

        if (data.size() > MAX_BATCH_SIZE) {
            LOGGER.warn(">> batchSize too big, max batch option's size is {}", MAX_BATCH_SIZE);
            return;
        }

        redisStringTemplate.opsForList().rightPushAll(contactKey(key), JSON.toJSONString(data));
    }

    @Override
    public Set<String> scan(String pattern, int count) {
        Set<String> keys = new HashSet();
        RedisConnection connection = this.redisStringTemplate.getConnectionFactory().getConnection();

        try {
            Cursor<byte[]>
                cursor = connection.scan(ScanOptions.scanOptions().match(this.contactKey(pattern)).count((long)count).build());
            Throwable var6 = null;

            try {
                while(cursor.hasNext()) {
                    keys.add((new String((byte[])cursor.next())).substring(this.namespace.length() + 1));
                }
            } catch (Throwable var16) {
                var6 = var16;
                throw var16;
            } finally {
                if (cursor != null) {
                    if (var6 != null) {
                        try {
                            cursor.close();
                        } catch (Throwable var15) {
                            var6.addSuppressed(var15);
                        }
                    } else {
                        cursor.close();
                    }
                }

            }

            return keys;
        } catch (Exception var18) {
            LOGGER.error(">> scan error", var18);
            return null;
        }
    }

    @Override
    public <T> List<T> multiGetObject(List<String> keys, Class<T> clazz) {
        if (CollectionUtils.isEmpty(keys) || clazz == null) {
            LOGGER.warn(">> illegal param -> keys or clazz is null, key: {}, clazz: {}", keys, clazz);
            return null;
        }

        List<String> multiGet = redisStringTemplate.opsForValue()
            .multiGet(keys.stream().map(this::contactKey).collect(Collectors.toList()));
        if (CollectionUtils.isEmpty(multiGet)) {
            return Collections.emptyList();
        }

        List<T> result = new ArrayList<>(keys.size());
        boolean isString = String.class.equals(clazz);
        for (String s : multiGet) {
            result.add(isString ? (T)s : JSON.parseObject(s, clazz));
        }
        return result;
    }

    private String contactKey(String key) {
        return namespace + ":" + key;
    }
}
