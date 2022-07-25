package com.hhu.flink.job;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * wc 的流式处理（源源不断的数据流入）
 * 
 * @author jacks
 * @date 2022/7/23
 */
public class WordCountStream {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 1. 从文本流读取
//        String filePath = "E:\\IDEAProjects\\opensource_research\\flink\\src\\main\\resources\\wordCount.text";
//        DataStreamSource<String> streamSource = env.readTextFile(filePath);

        // 2. 从 socket 读取 | 在 linux 子系统模拟输入流 nc -lk 7777
        DataStream<String> streamSource = env.socketTextStream("localhost", 7777);

        DataStream<Tuple2<String, Integer>> re =
            streamSource.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
                private static final long serialVersionUID = -1193092210099767808L;

                @Override
                public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {
                    for (String w : s.split(" ")) {
                        Tuple2<String, Integer> tuple2 = new Tuple2<>(w, 1);
                        collector.collect(tuple2);
                    }
                }
            }).keyBy(0).sum(1);

        re.print();

        // 一个个的来，必须指定启动
        env.execute();
    }
}
