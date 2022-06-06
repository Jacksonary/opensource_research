package com.zhaogang.other.test.feishu;

import java.io.Serializable;

import lombok.Data;

/**
 * @author jacks
 * @date 2022/3/17
 */
@Data
public class FSTextMessageDTO implements Serializable {
    private static final long serialVersionUID = 1884307015997754956L;
    /**
     * 文本消息内容
     */
    private String msg;
    /**
     * 需要 @ 的 userId，所有人是 all
     */
    private String atTargetId;
    /**
     * 需要 @ 的 userName，可自定义
     */
    private String atTargetName;

    public FSTextMessageDTO() {}

    public FSTextMessageDTO(String msg) {
        this.msg = msg;
    }

    public FSTextMessageDTO(String msg, String atTargetId, String atTargetName) {
        this.msg = msg;
        this.atTargetId = atTargetId;
        this.atTargetName = atTargetName;
    }
}
