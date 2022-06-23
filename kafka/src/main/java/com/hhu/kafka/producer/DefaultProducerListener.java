package com.hhu.kafka.producer;

import java.io.Serializable;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.LoggingProducerListener;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.util.ObjectUtils;

/**
 * @formatter:off 
 * 可选 - 用于异步获取发送消息结果的 callback，不需要等待 Future 完成 
 * 默认是使用 {@link LoggingProducerListener}
 * 
 * @author jacks
 * @date 2022/2/25
 * @formatter:on 
 */
public class DefaultProducerListener implements ProducerListener<Integer, Serializable> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultProducerListener.class);

    private static final int MAX_CONTENT_LOGGED = 1000;

    @Override
    public void onSuccess(ProducerRecord<Integer, Serializable> record, RecordMetadata recordMetadata) {
        LOGGER.info(">> msg send success -> " + getBaseInfo(record, recordMetadata));
    }

    @Override
    public void onError(ProducerRecord<Integer, Serializable> record, RecordMetadata recordMetadata,
        Exception exception) {
        LOGGER.error(
            ">> msg send failed -> Exception thrown when sending a message with " + getBaseInfo(record, recordMetadata),
            exception);
    }

    private String getBaseInfo(ProducerRecord<Integer, Serializable> record, RecordMetadata recordMetadata) {
        StringBuilder baseInfo = new StringBuilder();
        baseInfo.append("key='").append(toDisplayString(ObjectUtils.nullSafeToString(record.key()), MAX_CONTENT_LOGGED))
            .append("'").append(" and payload='")
            .append(toDisplayString(ObjectUtils.nullSafeToString(record.value()), MAX_CONTENT_LOGGED)).append("'");

        baseInfo.append(" to topic ").append(record.topic());
        if (record.partition() != null) {
            baseInfo.append(" and partition ")
                .append(recordMetadata != null ? recordMetadata.partition() : record.partition());
        }

        return baseInfo.toString();
    }

    private String toDisplayString(String original, int maxCharacters) {
        if (original.length() <= maxCharacters) {
            return original;
        }
        return original.substring(0, maxCharacters) + "...";
    }
}
