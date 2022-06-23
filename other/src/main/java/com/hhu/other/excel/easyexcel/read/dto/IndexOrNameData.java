package com.hhu.other.excel.easyexcel.read.dto;

import java.util.Date;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * @author weiguo.liu
 * @date 2021/3/12
 * @description
 */
public class IndexOrNameData {
    /**
     * 强制读取第三个 这里不建议 index 和 name 同时用，要么一个对象只用index，要么一个对象只用name去匹配
     */
    @ExcelProperty(index = 2)
    private Double doubleData;
    /**
     * 用名字去匹配，这里需要注意，如果名字重复，会导致只有一个字段读取到数据
     */
    @ExcelProperty("字符串标题")
    private String string;
    @ExcelProperty("日期标题")
    private Date date;

    @Override
    public String toString() {
        return "IndexOrNameData{" + "doubleData=" + doubleData + ", string='" + string + '\'' + ", date=" + date + '}';
    }

    public Double getDoubleData() {
        return doubleData;
    }

    public void setDoubleData(Double doubleData) {
        this.doubleData = doubleData;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
