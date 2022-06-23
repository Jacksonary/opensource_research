package com.hhu.kafka.consumer;

import com.hhu.kafka.constant.TopicConstant;
import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

/**
 * kafka consumer 的消费过程
 * @author jacks
 * @date 2022/2/28
 */
@Component
public class NameConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(NameConsumer.class);

    // @KafkaListener(topics = {TopicConstant.NAME_TOPIC}, groupId = TopicConstant.BASE_CONSUMER_GROUP)
    // public void doConsume(Serializable msg) {
    // LOGGER.info(">> NameConsumer get msg -> msg: {}", JSON.toJSONString(msg));
    // NameMsgDTO msg1 = (NameMsgDTO)msg;
    // if (!(msg instanceof NameMsgDTO)) {
    // LOGGER.warn(">> not my msg");
    // return;
    // }
    // }

    // @KafkaListener(topics = {TopicConstant.NAME_TOPIC2}, groupId = TopicConstant.BASE_CONSUMER_GROUP,
    // containerFactory = "batchListenFactory")
    @KafkaListener(topics = {TopicConstant.NAME_TOPIC4}, groupId = TopicConstant.BASE_CONSUMER_GROUP,
        containerFactory = "manualSingleListenFactory")
    public void doConsume(Serializable msg, Acknowledgment acknowledgment) {
        LOGGER.info(">> NameConsumer get msg -> msg: {}", JSON.toJSONString(msg));
        acknowledgment.acknowledge();
//        throw new RuntimeException("消费异常");
    }
}
