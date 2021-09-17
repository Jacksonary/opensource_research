package com.hhu.storage.service.impl;

import com.zhaogang.dubbo.service.StorageService;
import org.springframework.stereotype.Service;

/**
 * @author jacks
 * @date 2021/7/18
 * @description
 */
@Service("storageService")
public class StorageServiceImpl implements StorageService {
    @Override
    public void deduct(String commodityCode, int count) {

    }
}
