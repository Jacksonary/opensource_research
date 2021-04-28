package com.zhaogang.dubbo.provider.service.impl;

import com.zhaogang.dubbo.service.ProviderService;
import org.apache.dubbo.common.extension.Activate;
import org.springframework.stereotype.Service;

/**
 * @author weiguo.liu
 * @datetime 2020/9/29 15:03
 * @description
 */
@Activate
@Service("providerService")
public class ProviderServiceImpl implements ProviderService {

    @Override
    public String test() {
        return "I'm dubbo remote service";
    }
}
