package com.hhu.flink.source;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author jacks
 * @date 2022/7/23
 */
public class CollectionFileSource {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.setParallelism(1);
        DataStream<String> streamSource =
            env.readTextFile("E:\\IDEAProjects\\opensource_research\\flink\\src\\main\\resources\\sensor.text");

        streamSource.print();

        env.execute();
    }
}
