package com.hhu.other.util;

import java.lang.reflect.Field;

import org.apache.poi.ss.usermodel.Workbook;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.WriteContext;
import com.alibaba.excel.write.ExcelBuilderImpl;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;

/**
 * @author weiguo.liu
 * @date 2021/2/25
 * @description
 */
public class ExcelUtils {

    /**
     * **获取workbook** 因为EasyExcel这个库设计的原因 只能使用反射获取workbook
     *
     * @param writer
     * @return
     */
    private static Workbook getWorkbook(ExcelWriter writer) {
        try {
            Class<?> clazz1 = Class.forName("com.alibaba.excel.ExcelWriter");
            Field[] fs = clazz1.getDeclaredFields();
            for (Field field : fs) {
                // 要设置属性可达，不然会抛出IllegalAccessException异常
                field.setAccessible(true);
                if (!"excelBuilder".equals(field.getName())) {
                    continue;
                }

                ExcelBuilderImpl excelBuilder = (ExcelBuilderImpl)field.get(writer);
                Class<?> clazz2 = Class.forName("com.alibaba.excel.write.ExcelBuilderImpl");
                Field[] fs2 = clazz2.getDeclaredFields();
                for (Field field2 : fs2) {
                    field2.setAccessible(true);
                    if ("context".equals(field2.getName())) {
                        WriteContext context = (WriteContext)field2.get(excelBuilder);
                        return context.getWorkbook();
                    }
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

}
