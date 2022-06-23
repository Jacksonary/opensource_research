package com.hhu.other.enums;

/**
 * @author jacks
 * @date 2022/2/9
 */
public enum StatusEnum implements BaseCodeEnum {
    FAIL((byte)-1, "fail"), SUCCESS((byte)1, "success");

    Byte code;
    String desc;

    StatusEnum(Byte code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public Byte getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
