package com.zhaogang.netty.pojo;

/**
 * @author weiguo.liu
 * @date 2020/10/29
 * @description netty中传输的负责对象
 */
public class UnixTime {

    private final long value;

    public UnixTime() {
        this(System.currentTimeMillis() / 1000L + 2208988800L);
    }

    public UnixTime(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "UnixTime{" +
                "value=" + value +
                '}';
    }
}
