package com.zhaogang.dubbo.consumer.service.impl;

import com.zhaogang.dubbo.consumer.service.ConsumerService;
import com.zhaogang.dubbo.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author weiguo.liu
 * @date 2020/9/30
 * @description
 */
@Service("consumerService")
public class ConsumerServiceImpl implements ConsumerService {

    @Autowired
    private ProviderService providerService;

    @Override
    public void test() {
        System.out.println(providerService.test());
    }
}