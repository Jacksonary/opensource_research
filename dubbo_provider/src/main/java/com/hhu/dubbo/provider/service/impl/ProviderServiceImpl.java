package com.hhu.dubbo.provider.service.impl;

import com.hhu.dubbo.service.ProviderService;
import org.springframework.stereotype.Service;

/**
 * @author weiguo.liu
 * @datetime 2020/9/29 15:03
 * @description
 */
@Service("providerService")
public class ProviderServiceImpl implements ProviderService {

    @Override
    public String test() {
        int i = 8/0;
        return "I'm dubbo remote service";
    }
}
