package com.hhu.other.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 需要实现 InvocationHandler 接口
 * @author jacks
 * @date 2021/11/23
 */
public class DynamicProxy implements InvocationHandler {
    private Object target;

    public DynamicProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(">> I'm JDK dynamic proxy! start");
        Object invoke = method.invoke(target, args);
        System.out.println(">> I'm JDK dynamic proxy! end");
        return invoke;
    }
}
