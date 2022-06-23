package com.hhu.starter.services;

/**
 * @author weiguo.liu
 * @date 2021/4/13
 * @description 实际的业务操作类
 */
public class CustomService {
    private String config;

    public CustomService(String config) {
        this.config = config;
    }

    public String[] split(String flag) {
        return this.config.split(flag);
    }
}
