package com.hhu.storage.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author jacks
 * @date 2021/11/29
 */
@Component
public class CustomBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // 前置处理器
        System.out.println(">> Bean: + " + beanName + " 前置处理器");
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(">> Bean: + " + beanName + " 后置处理器");
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
