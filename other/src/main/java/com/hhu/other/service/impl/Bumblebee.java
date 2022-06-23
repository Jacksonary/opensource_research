package com.hhu.other.service.impl;

import com.hhu.other.service.Robot;

/**
 * @author weiguo.liu
 * @date 2020/10/12
 * @description
 */
public class Bumblebee implements Robot {

    @Override
    public void sayHello() {
        System.out.println("Hello, I'm Bumblebee!");
    }
}
