package com.hhu.rocketmq.producer;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author weiguo.liu
 * @date 2020/10/9
 * @description 消息生产者
 */
@Component("hhuProducer")
public class HhuProducer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public void send(String topic, String message) {
        rocketMQTemplate.convertAndSend(topic, message);
        System.out.println("producer: send message [ " + message + " ]");
    }
}
