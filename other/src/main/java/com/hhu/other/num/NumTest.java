package com.hhu.other.num;

import java.math.BigDecimal;

/**
 * @author jacks
 * @date 2021/12/22
 */
public class NumTest {
    public static void main(String[] args) {
        float f = 0.9f;
        double d = 0.9d;
        BigDecimal bigDecimal = BigDecimal.valueOf(f);
        System.out.println(f / 1.0);
        System.out.println(d / 1.0);
    }
}
