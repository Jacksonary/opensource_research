package com.zhaogang.other.proxy;

/**
 * @author jacks
 * @date 2021/11/23
 */
public class MyInterfaceImpl implements MyInterface {
    @Override
    public String sayHello() {
        System.out.println("hello, I's am interfaceImpl");
        return "test";
    }
}
