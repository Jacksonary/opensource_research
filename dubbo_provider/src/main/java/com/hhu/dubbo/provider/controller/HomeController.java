package com.hhu.dubbo.provider.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author weiguo.liu
 * @datetime 2020/9/29 15:11
 * @description
 */
@RestController
public class HomeController {

    @GetMapping("/go")
    public String go() {
        return "go";
    }
}
