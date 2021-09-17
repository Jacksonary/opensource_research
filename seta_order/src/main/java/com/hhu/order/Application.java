package com.hhu.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * @author jacks
 * @date 2021/7/18
 * @description
 */
@SpringBootApplication
@ImportResource("classpath:spring/*.xml")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
