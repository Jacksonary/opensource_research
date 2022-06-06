package com.zhaogang.other.test.feishu;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jacks
 * @date 2022/3/22
 */
@Data
public class FSPostMessageBody extends AbstractFeiShuMessage implements Serializable {
    private static final long serialVersionUID = 3583659529329255983L;
    private String content;

    /**
     * 富文本消息
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CommonPostContent implements Serializable {
        private static final long serialVersionUID = -3979508244552461403L;
        @JSONField(name = "zh_cn")
        private PostContent zhCn;
        @JSONField(name = "en_us")
        private PostContent enUs;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PostContent implements Serializable {
        private static final long serialVersionUID = -1214268782197805515L;
        private String title;
        private List<PostContentItemLine> content;
    }

    /**
     * 一行内的内容
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PostContentItemLine {
        /**
         * 这个 key 需要移除
         */
        private List<PostContentItem> postContentItemList;
    }

    @Data
    @NoArgsConstructor
    public static class PostContentItem implements Serializable {
        private static final long serialVersionUID = 2758130060434086477L;
        /**
         * html 里面的标签
         */
        private String tag;
        /**
         * 文本
         */
        private String text;
        /**
         * 超链接，a tag
         */
        private String href;

        /**
         * 图片内容
         */
        @JSONField(name = "image_key")
        private String imageKey;
        private Integer width;
        private Integer height;

        /**
         * @ 对象
         */
        @JSONField(name = "user_id")
        private String userId;
        @JSONField(name = "user_name")
        private String userName;

        public PostContentItem(String tag, String text) {
            this.tag = tag;
            this.text = text;
        }

        public PostContentItem(String tag, String text, String href) {
            this.tag = tag;
            this.text = text;
            this.href = href;
        }
    }
}
