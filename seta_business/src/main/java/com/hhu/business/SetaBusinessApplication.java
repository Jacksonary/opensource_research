package com.hhu.business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@ImportResource("classpath:spring/*.xml")
@SpringBootApplication
public class SetaBusinessApplication {

    public static void main(String[] args) {
        SpringApplication.run(SetaBusinessApplication.class, args);
    }

}
