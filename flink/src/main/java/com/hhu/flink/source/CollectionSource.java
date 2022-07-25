package com.hhu.flink.source;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author jacks
 * @date 2022/7/23
 */
public class CollectionSource {
    public static void main(String[] args) throws Exception {
        // 创建执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 从集合中读取数据
        DataStream<SensorReading> dataStream = env.fromCollection(getMockSensorReadingCollection());

        DataStream<Integer> streamSource = env.fromElements(1, 2, 3, 4);
        // 打印输出
        dataStream.print("sensorReading");
        streamSource.print("intReading");

        env.execute();
    }

    private static List<SensorReading> getMockSensorReadingCollection() {
        LocalDateTime now = LocalDateTime.now();

        return Arrays.asList(
            new SensorReading("sensor1", now.plusSeconds(-9).toInstant(ZoneOffset.of("+8")).toEpochMilli(), 3.1),
            new SensorReading("sensor6", now.plusSeconds(-8).toInstant(ZoneOffset.of("+8")).toEpochMilli(), 3.1),
            new SensorReading("sensor5", now.plusSeconds(-5).toInstant(ZoneOffset.of("+8")).toEpochMilli(), 3.1),
            new SensorReading("sensor3", now.plusSeconds(-1).toInstant(ZoneOffset.of("+8")).toEpochMilli(), 3.1));
    }
}
