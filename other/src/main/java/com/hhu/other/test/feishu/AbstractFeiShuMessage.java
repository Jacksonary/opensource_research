package com.hhu.other.test.feishu;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

/**
 * @author jacks
 * @date 2022/3/22
 */
@Data
public abstract class AbstractFeiShuMessage {
    @JSONField(name = "msg_type")
    private String msgType;
    private String timestamp;
    private String sign;
}
