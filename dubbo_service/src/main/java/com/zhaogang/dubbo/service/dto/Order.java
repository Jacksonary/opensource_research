package com.zhaogang.dubbo.service.dto;

import java.io.Serializable;

/**
 * @author jacks
 * @date 2021/7/18
 * @description
 */
public class Order implements Serializable {
    private static final long serialVersionUID = -2964936245549163197L;
    public String userId;
    public String commodityCode;
    public Integer count;
    public Integer money;

}
