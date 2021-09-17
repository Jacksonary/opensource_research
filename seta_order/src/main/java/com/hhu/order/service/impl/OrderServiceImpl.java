package com.hhu.order.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.zhaogang.dubbo.service.AccountService;
import com.zhaogang.dubbo.service.OrderService;
import com.zhaogang.dubbo.service.dto.Order;

/**
 * @author jacks
 * @date 2021/7/18
 * @description
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {
    @Resource
    private AccountService accountService;

    @Override
    public Boolean create(String userId, String commodityCode, int orderCount) {
        int orderMoney = calculate(commodityCode, orderCount);

        accountService.debit(userId, orderMoney);

        Order order = new Order();
        order.userId = userId;
        order.commodityCode = commodityCode;
        order.count = orderCount;
        order.money = orderMoney;

        // INSERT INTO orders ...
//        return orderDAO.insert(order);
        return true;
    }

    private int calculate(String commodityCode, int orderCount) {
        return StringUtils.isEmpty(commodityCode) ? orderCount : commodityCode.length() + orderCount;
    }

}
