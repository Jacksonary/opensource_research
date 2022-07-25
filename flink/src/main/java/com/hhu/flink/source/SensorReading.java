package com.hhu.flink.source;

import java.time.ZoneOffset;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 传感器读取数据类型
 * 
 * @author jacks
 * @date 2022/7/23
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class SensorReading {
    private String id;
    private Long timestamp;
    private Double temperature;
}
