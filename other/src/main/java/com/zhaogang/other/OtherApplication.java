package com.zhaogang.other;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author weiguo.liu
 * @date 2020/10/10
 * @description
 */
@MapperScan(basePackages = {"com.zhaogang.other.dao.mapper"})
@EnableScheduling
@EnableFeignClients
@ServletComponentScan
@SpringBootApplication
public class OtherApplication {

    public static void main(String[] args) {
        SpringApplication.run(OtherApplication.class, args);
    }
}
