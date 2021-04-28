package com.zhaogang.starter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author weiguo.liu
 * @date 2021/4/13
 * @description 自定义的配置文件类，自动读取前缀为 com.zhaogang 的配置
 */
@ConfigurationProperties(prefix = "com.zhaogang")
public class CustomProperties {
    /**
     * 这里的属性名一定要和这里的属性名保持一致，否则无法注入，即 com.zhaogang.config
     */
    private String config;

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }
}
