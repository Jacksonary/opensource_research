package com.hhu.ddd.api.model;

import lombok.Data;

/**
 * fluent validator:
 * 1. http://neoremind.com/2016/02/java%E7%9A%84%E4%B8%9A%E5%8A%A1%E9%80%BB%E8%BE%91%E9%AA%8C%E8%AF%81%E6%A1%86%E6%9E%B6fluent-validator/
 * 2. https://github.com/neoremind/fluent-validator
 *
 * @author jacks
 * @date 2022/6/6
 */
@Data
public class BDTestReq {
    private Integer id;
    private String name;
}
