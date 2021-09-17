package com.hhu.business.service.impl;

import com.google.common.collect.Lists;
import io.seata.spring.annotation.GlobalTransactional;
import java.util.ArrayList;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hhu.business.service.BusinessService;
import com.zhaogang.dubbo.service.OrderService;
import com.zhaogang.dubbo.service.StorageService;

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
