package com.hhu.other.enums;

/**
 * @author jacks
 * @date 2022/2/9
 */
public enum TypeEnum implements BaseCodeEnum {
    A4(1, "a4"), X1(2, "x1");

    Integer code;
    String desc;

    TypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
