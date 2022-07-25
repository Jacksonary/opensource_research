package com.hhu.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jacks
 * @date 2022/6/24
 */
@RestController
@RequestMapping("")
public class TestController {

    @GetMapping("/fallback")
    public String fallback() {
        return "fallback page";
    }
}
