package com.zhaogang.other.enums;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

/**
 * 基础枚举类方法的抽象
 * E 为枚举类
 * 
 * @author jacks
 * @date 2022/2/9
 */
public interface BaseNameEnum {
    public static <E extends Enum<E> & BaseNameEnum> E parseByName(Class<E> clazz, String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }

        return Arrays.stream(clazz.getEnumConstants()).filter(o -> name.equals(o.getName())).findFirst().orElse(null);
    }

    String getName();
}
