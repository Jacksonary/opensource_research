package com.hhu.bilibili.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author jacks
 * @date 2021/7/3
 * @description
 */
public class ScannerUtils {

    private static final String BYTE = "java.lang.Byte";
    private static final String SHORT = "java.lang.Short";
    private static final String BOOLEAN = "java.lang.Boolean";
    private static final String INTEGER = "java.lang.Integer";
    private static final String FLOAT = "java.lang.Float";
    private static final String LONG = "java.lang.Long";
    private static final String DOUBLE = "java.lang.Double";

    private static final String STRING = "java.lang.String";

    private static final Logger LOGGER = LoggerFactory.getLogger(ScannerUtils.class);

    /**
     * 从输入流获取一个指定类型的数组
     */
    public static <T> T[] getArr(Class<T> clazz) {
        List<Object> list = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        // 获取数组
        boolean flag = false;
        do {
            System.out.print(">> 请输入数组（逗号分割）: ");

            String nextLine = scanner.nextLine();
            if (StringUtils.isBlank(nextLine)) {
                System.out.println(">> 输入数组不能为空！");
                continue;
            }

            String[] split = nextLine.split(",");
            for (int i = 0; i < split.length; i++) {
                try {
                    T item = pare2BasicType(split[i], clazz);
                    list.add(item);
                } catch (Exception e) {
                    System.out.println(">> 输入元素含非指定类型元素！");
                    list.clear();
                    break;
                }
                if (i == split.length - 1) {
                    flag = true;
                }
            }
        } while (!flag);

        System.out.println(">> 数组为：" + list);
        // 泛型数组的初始化：Array.newInstance(clazz, list.size())
        return list.toArray((T[])Array.newInstance(clazz, list.size()));
    }

    public static <T> T getSignValue(Class<T> clazz) {
        boolean flag = false;

        Scanner scanner = new Scanner(System.in);
        // 获取窗口大小
        do {
            System.out.print(">> 请输入元素: ");
            try {
                return pare2BasicType(scanner.nextLine(), clazz);
            } catch (Exception e) {
                System.out.println(">> 输入有误！");
                continue;
            }
        } while (!flag);

        return null;
    }

    /**
     * 将 String 类型转为对应的基础类型
     */
    private static <T> T pare2BasicType(String object, Class<T> clazz) {
        if (clazz == null || object == null) {
            return null;
        }

        switch (clazz.getName()) {
            case BYTE:
                return (T)Byte.valueOf(object);
            case SHORT:
                return (T)Short.valueOf(object);
            case BOOLEAN:
                return (T)Boolean.valueOf(object);
            case INTEGER:
                return (T)Integer.valueOf(object);
            case FLOAT:
                return (T)Float.valueOf(object);
            case LONG:
                return (T)Long.valueOf(object);
            case DOUBLE:
                return (T)Double.valueOf(object);
            case STRING:
                return (T)object;
            default:
                LOGGER.warn(">> illegal object, not basic type, value: {}", JSON.toJSONString(object));
                return null;
        }
    }

}
