package com.zhaogang.other.redis.redisson.dto;

import java.io.File;
import java.io.IOException;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @author weiguo.liu
 * @date 2021/4/25
 * @description
 */
public class InfoDTO {
    public static void main(String[] args) throws IOException {
        Config config =Config.fromYAML(new File("C:\\Users\\weiguo.liu\\Desktop\\乱七八糟的日志\\redisson2.yaml"));
        RedissonClient redisson = Redisson.create(config);
        System.out.println(redisson);
    }
}
