package com.hhu.storage.service.impl;

import org.springframework.stereotype.Service;

import com.hhu.storage.service.TestService;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jacks
 * @date 2021/11/29
 */
@Service("testService")
public class TestServiceImpl implements TestService {
    @Override
    public void test() {

    }

    @Transactional
    public void trx() {

    }

    public synchronized void lock() {

    }
}
