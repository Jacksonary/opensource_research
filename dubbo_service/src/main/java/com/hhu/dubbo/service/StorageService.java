package com.hhu.dubbo.service;

/**
 * @author jacks
 * @date 2021/7/18
 * @description
 */
public interface StorageService {
    /**
     * 扣除存储数量
     */
    void deduct(String commodityCode, int count);
}
