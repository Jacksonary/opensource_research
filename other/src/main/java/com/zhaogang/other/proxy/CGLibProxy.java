package com.zhaogang.other.proxy;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

/**
 * CGLib
 * 
 * @author jacks
 * @date 2021/11/26
 */
public class CGLibProxy implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        // 调用方法之前，我们可以添加自己的操作
        System.out.println("I'm CGLib proxy, start");
        Object object = methodProxy.invokeSuper(o, objects);
        // 调用方法之后，我们同样可以添加自己的操作
        System.out.println("I'm CGLib proxy, end");
        return object;
    }
}
