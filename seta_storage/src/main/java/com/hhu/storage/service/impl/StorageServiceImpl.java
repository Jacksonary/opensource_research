package com.hhu.storage.service.impl;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Service;

import com.zhaogang.dubbo.service.StorageService;

/**
 * @author jacks
 * @date 2021/7/18
 * @description
 */
@Service("storageService")
public class StorageServiceImpl implements StorageService {

    @PostConstruct
    public void init() {

    }

    @PreDestroy
    public void destroy() {

    }

    @Override
    public void deduct(String commodityCode, int count) {
        System.out.println(">> 扣减库存, code: " + commodityCode + ", count: " + count);
    }
}
