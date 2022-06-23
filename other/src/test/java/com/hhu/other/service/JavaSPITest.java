package com.hhu.other.service;

import java.util.ServiceLoader;
import org.junit.jupiter.api.Test;

/**
 * @author weiguo.liu
 * @date 2020/10/12
 * @description
 */
class JavaSPITest {

    @Test
    public void sayHello() throws Exception {
        ServiceLoader<Robot> serviceLoader = ServiceLoader.load(Robot.class);
        System.out.println("Java SPI");
        serviceLoader.forEach(Robot::sayHello);
    }
}