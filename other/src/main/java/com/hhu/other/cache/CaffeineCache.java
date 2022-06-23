package com.hhu.other.cache;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

/**
 * @author jacks
 * @date 2022/1/17
 */
public class CaffeineCache {
    public static void main(String[] args) throws InterruptedException {
        Cache<Integer, String> cache =
            Caffeine.newBuilder().expireAfterWrite(5, TimeUnit.SECONDS).maximumSize(3).build();

        new Thread(() -> {
            while (true) {
                ConcurrentMap<Integer, String> map = cache.asMap();
                System.out.println("============= cache =============");
                map.forEach((k, v) -> System.out.println(k + ": " + v));
                System.out.println("============== end ==============");
                System.out.println();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        for (int i = 0; i < 10; i++) {
            cache.put(i, "v" + i);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        TimeUnit.SECONDS.sleep(30);
    }
}
