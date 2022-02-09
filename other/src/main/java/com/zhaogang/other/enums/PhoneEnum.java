package com.zhaogang.other.enums;

/**
 * @author jacks
 * @date 2022/2/9
 */
public enum PhoneEnum implements BaseCodeEnum, BaseNameEnum {
    GOOGLE((byte)1, "pixel", "google's son"), SONY((byte)2, "xperia", "sony's son");

    Byte code;
    String name;
    String desc;

    PhoneEnum(Byte code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }

    @Override
    public Byte getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }
}
