package com.hhu.nacos.controller;

// import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.nacos.api.config.annotation.NacosValue;

/**
 * @author weiguo.liu
 * @date 2020/9/30
 * @description
 */
@RestController
public class HomeController {

    // springboot 的集成不支持自动刷新，只能用 nacos 的注解
    @NacosValue(value = "${custom.str:defaultProperties}", autoRefreshed = true)
    private String properties;

    @GetMapping("/go")
    public String go() {
        return properties;
    }
}
