package com.hhu.other.test.feishu;

import lombok.Getter;

/**
 * @author jacks
 * @date 2022/3/22
 */
@Getter
public enum FeiShuMsgTypeEnum {
    /**
     * 几种消息类型
     */
    TEXT("text", "文本"),
    POST("post", "富文本"),
    IMAGE("image", "图片"),
    SHARE_CHAT("share_chat", "图片"),
    INTERACTIVE("interactive", "消息卡片");

    String code;
    String desc;

    FeiShuMsgTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
