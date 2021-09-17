package com.hhu.business.service;

/**
 * @author jacks
 * @date 2021/7/18
 * @description
 */
public interface BusinessService {
    void purchase(String userId, String commodityCode, int orderCount);
}
