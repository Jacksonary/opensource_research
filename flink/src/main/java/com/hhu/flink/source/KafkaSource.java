package com.hhu.flink.source;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * 将 kafka 作为一个输入的数据源(很重要)
 * 
 * @author jacks
 * @date 2022/7/23
 */
public class KafkaSource {
    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
    }
}
