package com.hhu.flink.job;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

/**
 * 批处理 wc(数据已经全部到来叫批处理，主要针对离线数据)
 * 
 * @author jacks
 * @date 2022/7/23
 */
public class WordCountSet {
    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        String filePath = "E:\\IDEAProjects\\opensource_research\\flink\\src\\main\\resources\\wordCount.text";
        DataSource<String> dataSource = env.readTextFile(filePath);

        // 按照空格打散进行统计, 结果使用二元组进行统计（flink 中可以现成的二元组）
        DataSet<Tuple2<String, Integer>> dataSet =
            dataSource.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
                    private static final long serialVersionUID = 2433029191276133298L;

                    @Override public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {
                        // 按空格分词 遍历
                        for (String w : s.split(" ")) {
                            // 统计，每个词出现 1 次
                            Tuple2<String, Integer> re = new Tuple2<>(w, 1);
                            // 将结果输出到 collector 中
                            collector.collect(re);
                        }
                    }
                }).groupBy(0) // 按照第一个位置(即word)进行分组
                .sum(1);// 对第 2 个位置的元素（统计频次）进行累加

        dataSet.print();
    }
}
