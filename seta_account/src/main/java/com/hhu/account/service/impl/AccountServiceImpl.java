package com.hhu.account.service.impl;

import org.springframework.stereotype.Service;

import com.zhaogang.dubbo.service.AccountService;

/**
 * @author jacks
 * @date 2021/7/18
 * @description
 */
@Service("accountService")
public class AccountServiceImpl implements AccountService {
    @Override
    public void debit(String userId, int money) {
        System.out.println(">> account service -> debit, userId: " + userId + ", money: " + money);
    }
}
