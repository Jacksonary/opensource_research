package com.zhaogang.rocketmq.controller;

import com.zhaogang.rocketmq.consumer.ZhaoGangConsumer;
import com.zhaogang.rocketmq.producer.ZhaoGangProducer;
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
    private ZhaoGangProducer zhaoGangProducer;
    @Autowired
    private ZhaoGangConsumer zhaoGangConsumer;

    @GetMapping("/go")
    public String go() {
        return "go";
    }

    @PostMapping("/send")
    public void sendMessage() {
//        zhaoGangProducer.send("test_topic", "Hello, it's a message produced by producer");
        zhaoGangProducer.send("haha", "Hello, it's a message produced by producer");
    }

}
