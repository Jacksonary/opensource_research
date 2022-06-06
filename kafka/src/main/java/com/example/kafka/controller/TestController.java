package com.example.kafka.controller;

import com.example.kafka.dto.NameMsgDTO;
import java.io.Serializable;
import java.util.concurrent.ExecutionException;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.example.kafka.constant.TopicConstant;

/**
 * @author jacks
 * @date 2022/2/25
 */
@RestController
@RequestMapping("")
public class TestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);
    @Resource
    private KafkaTemplate<Integer, Serializable> kafkaTemplate;

    private static String getBigMsg(int byteSize) {
        byte[] bytes = new byte[byteSize];
        byte tByte = "test".getBytes()[0];
        for (int i = 0; i < byteSize; i++) {
            bytes[i] = tByte;
        }
        return new String(bytes);
    }

    /**
     * @formatter:on
     * 处理 callback 的方式1，直接在发送时添加 callback
     * template.send 返回的是一个 ListenableFuture，
     * 可以自定义东西来监听 callback ListenableFuture<SendResult>
     * @formatter:off
     */
    @GetMapping("/send_callback1")
    public void sendMsgWithCallback() {
        ListenableFuture<SendResult<Integer, Serializable>> future =
            kafkaTemplate.send(TopicConstant.NAME_TOPIC, 11, new NameMsgDTO(11, "name11"));
        future.addCallback(new ListenableFutureCallback<SendResult<Integer, Serializable>>() {
            @Override
            public void onSuccess(SendResult<Integer, Serializable> result) {
                LOGGER.info(">> send success, result: {}", JSON.toJSONString(result));
            }

            /** onFailure 方法抛出来的异常可以转为 KafkaProducerException
             */
            @Override
            public void onFailure(Throwable ex) {
                LOGGER.error(">> send failed, e: {}", JSON.toJSONString(ex));
            }
        });
    }

    /**
     * 处理 callback 的方式2，通过添加自定的监听器自己处理，发送仅仅是发送（比较赶紧），推荐
     */
    @GetMapping("/send_callback2")
    public void sendMsg()throws ExecutionException, InterruptedException {
//        ListenableFuture<SendResult<Integer, Serializable>> future =
//            kafkaTemplate.send(TopicConstant.NAME_TOPIC,  null, new NameMsgDTO(33, "getBigMsg(0)"));

//        ListenableFuture<SendResult<Integer, Serializable>> future =
//            kafkaTemplate.send(TopicConstant.NAME_TOPIC2, new NameMsgDTO(33, "getBigMsg(0)"));
//        SendResult<Integer,Serializable> sendResult = future.get();
//        System.out.println(JSON.toJSONString(sendResult));

        for(int i = 0; i < 1; i++) {
//            kafkaTemplate.send(TopicConstant.NAME_TOPIC2, 2, new NameMsgDTO(177+i, "consumer error"));
//            kafkaTemplate.send(TopicConstant.NAME_TOPIC3, 3, new NameMsgDTO(177+i, "consumer error"));
            kafkaTemplate.send(TopicConstant.NAME_TOPIC4, 4, new NameMsgDTO(178+i, "44consumer error"));
        }
    }
}
