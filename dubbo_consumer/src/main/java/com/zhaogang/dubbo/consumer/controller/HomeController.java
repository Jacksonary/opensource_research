package com.zhaogang.dubbo.consumer.controller;

import com.zhaogang.dubbo.consumer.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author weiguo.liu
 * @date 2020/9/30
 * @description
 */
@RestController
public class HomeController {

    @Autowired
    private ConsumerService consumerService;

    @GetMapping("/go")
    public String go() {
        consumerService.test();
        return "go";
    }
}
