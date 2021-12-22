package com.hhu.storage.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhaogang.dubbo.service.StorageService;

/**
 * @author jacks
 * @date 2021/11/29
 */
@RequestMapping("/test")
@RestController
public class TestController {

    @Resource
    private StorageService storageService;

    @GetMapping("/go")
    public void go() {
        storageService.deduct("code", 2);
    }
}
