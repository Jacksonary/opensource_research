package com.hhu.business.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hhu.business.service.BusinessService;
import com.hhu.dubbo.service.OrderService;
import com.hhu.dubbo.service.StorageService;

import io.seata.spring.annotation.GlobalTransactional;

/**
 * @author jacks
 * @date 2021/7/18
 * @description
 */
@Service("businessService")
public class BusinessServiceImpl implements BusinessService {
    @Resource
    private OrderService orderService;
    @Resource
    private StorageService storageService;

    @GlobalTransactional
    @Override
    public void purchase(String userId, String commodityCode, int orderCount) {
        storageService.deduct(commodityCode, orderCount);

        orderService.create(userId, commodityCode, orderCount);
    }

}
