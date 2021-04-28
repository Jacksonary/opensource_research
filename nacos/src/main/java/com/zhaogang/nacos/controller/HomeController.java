package com.zhaogang.nacos.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author weiguo.liu
 * @date 2020/9/30
 * @description
 */
@RestController
public class HomeController {

    @NacosValue(value = "${zg.nacos.properties:defaultProperties}", autoRefreshed = true)
    private String properties;

    @GetMapping("/go")
    public String go() {
        System.out.println(properties);
        return "go22";
    }

    public static void main(String[] args) {
        System.out.println(System.getenv("Path"));
    }
}
