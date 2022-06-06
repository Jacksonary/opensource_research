package com.hhu.bilibili.tree.num;

import java.util.Random;

/**
 *
 * @author jacks
 * @date 2021/12/9
 */
public class Squre {
    public static void main(String[] args) {
        float offset = (float)0.001;
        String template = "squre: %s, result: %s, actual: %s";

        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < 10; i++) {
            float v = random.nextInt(100);
            System.out.println(String.format(template, v, getItem(v, offset), Math.pow(getItem(v, offset), 2)));
        }
    }

    private static float getItem(float num, float offset) {
        float high = num;
        float low = 0;

        while (true) {
            // float mid = (low + high) / 2;
            // 避免溢出的问题
            float mid = low + (high - low) / 2;

            float re = mid * mid;
            // 大于期望值，降低上限
            if (re >= num) {
                if (re - num <= offset) {
                    return mid;
                }
                high = mid;
                continue;
            }

            // 小于期望值，提升上限
            if (num - re <= offset) {
                return mid;
            }

            low = mid;
        }
    }
}
