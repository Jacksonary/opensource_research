package com.zhaogang.other.service.impl;

import com.zhaogang.other.service.Robot;

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
