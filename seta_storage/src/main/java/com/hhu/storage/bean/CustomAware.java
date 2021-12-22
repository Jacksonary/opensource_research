package com.hhu.storage.bean;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author jacks
 * @date 2021/11/29
 */
@Component
public class CustomAware implements ApplicationContextAware {
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println(">> 调用 aware 接口方法");
    }
}
