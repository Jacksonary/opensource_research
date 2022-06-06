package com.zhaogang.other.test.feishu;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jacks
 * @date 2022/3/16
 */
@Data
public class FSTextMessageBody extends AbstractFeiShuMessage implements Serializable {
    private static final long serialVersionUID = 2857507584803624842L;

    private TextContent content;

    /**
     * 普通文本消息
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TextContent {
        private String text;
    }
}
