package com.zhaogang.dubbo.service;

/**
 * @author jacks
 * @date 2021/7/18
 * @description
 */
public interface OrderService {
    /**
     * 创建订单
     */
    Boolean create(String userId, String commodityCode, int orderCount);
}
