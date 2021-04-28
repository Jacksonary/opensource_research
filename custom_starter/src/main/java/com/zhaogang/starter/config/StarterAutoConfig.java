package com.zhaogang.starter.config;

import javax.annotation.Resource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zhaogang.starter.services.CustomService;

/**
 * @author weiguo.liu
 * @date 2021/4/13
 * @description 自动装配的配置类
 */
@Configuration
@ConditionalOnClass(CustomService.class)
@EnableConfigurationProperties(CustomProperties.class)
public class StarterAutoConfig {
    @Resource
    private CustomProperties customProperties;

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "com.zhaogang", value = "enabled", havingValue = "true")
    CustomService customService() {
        return new CustomService(customProperties.getConfig());
    }
}
