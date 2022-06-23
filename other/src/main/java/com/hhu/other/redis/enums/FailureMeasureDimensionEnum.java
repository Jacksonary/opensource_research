package com.hhu.other.redis.enums;

import java.util.Arrays;

import lombok.Getter;

/**
 * @author jacks
 * @date 2022/4/18
 */
@Getter
public enum FailureMeasureDimensionEnum {
    /**
     * 几种 mtbf 指标的计算维度
     */
    IMEI((byte)1, "imei维度，即设备维度"),
    ROM((byte)2, "rom版本维度"),
    FLEET((byte)3, "车队维度"),
    ENTERPRISE((byte)4, "企业维度");

    Byte code;
    String desc;

    FailureMeasureDimensionEnum(Byte code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static FailureMeasureDimensionEnum parseByCode(Byte code) {
        if (code == null) {
            return null;
        }

        return Arrays.stream(FailureMeasureDimensionEnum.values()).filter(o -> o.getCode().equals(code)).findFirst()
            .orElse(null);
    }
}
