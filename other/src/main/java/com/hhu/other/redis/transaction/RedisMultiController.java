package com.hhu.other.redis.transaction;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jacks
 * @date 2022/1/14
 */
@RestController
public class RedisMultiController {
    @Resource
    private MultiService multiService;

    @GetMapping("/redis/multi")
    public void multi() {
        multiService.multiOpt();
    }
}
