package com.zhaogang.dubbo.service;

/**
 * @author jacks
 * @date 2021/7/18
 * @description
 */
public interface AccountService {
    /**
     * 从用户账户中借出
     */
    void debit(String userId, int money);
}
