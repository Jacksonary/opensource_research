package com.hhu.rocketmq.controller;

import com.hhu.rocketmq.consumer.HhuConsumer;
import com.hhu.rocketmq.producer.HhuProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author weiguo.liu
 * @date 2020/9/30
 * @description
 */
@RestController
public class HomeController {

    @Autowired
    private HhuProducer hhuProducer;
    @Autowired
    private HhuConsumer hhuConsumer;

    @GetMapping("/go")
    public String go() {
        return "go";
    }

    @PostMapping("/send")
    public void sendMessage() {
//        hhuProducer.send("test_topic", "Hello, it's a message produced by producer");
        hhuProducer.send("haha", "Hello, it's a message produced by producer");
    }

}
