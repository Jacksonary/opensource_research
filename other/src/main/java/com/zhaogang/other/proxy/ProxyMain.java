package com.zhaogang.other.proxy;

import java.lang.reflect.Proxy;

import org.springframework.cglib.proxy.Enhancer;

/**
 * @author jacks
 * @date 2021/11/23
 */
public class ProxyMain {
    public static void main(String[] args) {
        // 1. 动态代理
        MyInterfaceImpl myIn = new MyInterfaceImpl();
        ClassLoader classLoader = myIn.getClass().getClassLoader();
        Class<?>[] interfaces = myIn.getClass().getInterfaces();
        DynamicProxy dynamicProxy = new DynamicProxy(myIn);
        MyInterface myInterface = (MyInterface)Proxy.newProxyInstance(classLoader, interfaces, dynamicProxy);
        myInterface.sayHello();

        System.out.println("==========================");

        // 2. CGLib代理
        Enhancer enhancer = new Enhancer();
        enhancer.setClassLoader(classLoader);
        enhancer.setSuperclass(myIn.getClass());
        enhancer.setCallback(new CGLibProxy());
        // 创建代理类
        MyInterface cgLibProxy = (MyInterface)enhancer.create();
        cgLibProxy.sayHello();
    }
}
