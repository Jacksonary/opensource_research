package com.zhaogang.other.excel.easyexcel.export.model;

import com.alibaba.excel.annotation.ExcelProperty;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author weiguo.liu
 * @date 2020/12/31
 * @description excelModel 导出对象
 */
public class DemoModel {
    @ExcelProperty(value = "ID")
    private String id;
    @ExcelProperty(value = "姓名")
    private String name;
    @ExcelProperty(value = "年龄")
    private String age;
    // 最大 10 位，不存在科学计数法的问题
    @ExcelProperty(value = "Integer扩展")
    private Integer integerValue;
    // 超过 11 位时，变为科学计数法，且从15位开始四舍五入，16位以后变为0
    @ExcelProperty(value = "Long扩展")
    private Long longValue;
    @ExcelProperty(value = "Float扩展")
    private Float floatValue;
    @ExcelProperty(value = "Double扩展")
    private Double doubleValue;
    @ExcelProperty(value = "BigDecimal扩展")
    private BigDecimal bigDecimal;
    @ExcelProperty(value = "Date扩展")
    private Date time;

    public static void main(String[] args) {
        System.out.println(0.05+0.01);
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getIntegerValue() {
        return integerValue;
    }

    public void setIntegerValue(Integer integerValue) {
        this.integerValue = integerValue;
    }

    public Long getLongValue() {
        return longValue;
    }

    public void setLongValue(Long longValue) {
        this.longValue = longValue;
    }

    public Float getFloatValue() {
        return floatValue;
    }

    public void setFloatValue(Float floatValue) {
        this.floatValue = floatValue;
    }

    public Double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(Double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public BigDecimal getBigDecimal() {
        return bigDecimal;
    }

    public void setBigDecimal(BigDecimal bigDecimal) {
        this.bigDecimal = bigDecimal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
