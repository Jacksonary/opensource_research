package com.zhaogang.dubbo.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * @author weiguo.liu
 * @datetime 2020/9/29 13:47
 * @description
 */
@ImportResource("classpath:META-INF/spring/*.xml")
@SpringBootApplication
public class DubboProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboProviderApplication.class, args);
    }
}
