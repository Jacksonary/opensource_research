package com.zhaogang.other.enums;

import java.util.Arrays;

/**
 * 基础枚举类方法的抽象
 * E 为枚举类型 | T 为 Code 码的类型
 * 
 * @author jacks
 * @date 2022/2/9
 */
public interface BaseCodeEnum {
    static <E extends Enum<E> & BaseCodeEnum, T> E parseByCode(Class<E> clazz, T code) {
        if (code == null) {
            return null;
        }

        return Arrays.stream(clazz.getEnumConstants()).filter(o -> code.equals(o.getCode())).findFirst().orElse(null);
    }

    <T> T getCode();
}
