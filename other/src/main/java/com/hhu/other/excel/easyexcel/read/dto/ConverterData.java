package com.hhu.other.excel.easyexcel.read.dto;

import com.hhu.other.excel.easyexcel.read.converter.DateConverter;
import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.CellData;

/**
 * @author weiguo.liu
 * @date 2021/3/12
 * @description
 */
public class ConverterData {

    @ExcelProperty(value = "日期", converter = DateConverter.class)
    private Date date;
    @ExcelProperty("布尔")
    private Boolean booleanData;
    @ExcelProperty("大数")
    private BigDecimal bigDecimal;
    @ExcelProperty("长整型")
    private long longData;
    @ExcelProperty("整型")
    private Integer integerData;
    @ExcelProperty("短整型")
    private Short shortData;
    @ExcelProperty("字节型")
    private Byte byteData;
    @ExcelProperty("双精度浮点型")
    private double doubleData;
    @ExcelProperty("浮点型")
    private Float floatData;
    @ExcelProperty("字符串")
    private String string;
    @ExcelProperty("自定义")
    private CellData cellData;

    @Override
    public String toString() {
        return "ConverterData{" + "date=" + date + ", booleanData=" + booleanData + ", bigDecimal=" + bigDecimal
            + ", longData=" + longData + ", integerData=" + integerData + ", shortData=" + shortData + ", byteData="
            + byteData + ", doubleData=" + doubleData + ", floatData=" + floatData + ", string='" + string + '\''
            + ", cellData=" + cellData + '}';
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getBooleanData() {
        return booleanData;
    }

    public void setBooleanData(Boolean booleanData) {
        this.booleanData = booleanData;
    }

    public BigDecimal getBigDecimal() {
        return bigDecimal;
    }

    public void setBigDecimal(BigDecimal bigDecimal) {
        this.bigDecimal = bigDecimal;
    }

    public long getLongData() {
        return longData;
    }

    public void setLongData(long longData) {
        this.longData = longData;
    }

    public Integer getIntegerData() {
        return integerData;
    }

    public void setIntegerData(Integer integerData) {
        this.integerData = integerData;
    }

    public Short getShortData() {
        return shortData;
    }

    public void setShortData(Short shortData) {
        this.shortData = shortData;
    }

    public Byte getByteData() {
        return byteData;
    }

    public void setByteData(Byte byteData) {
        this.byteData = byteData;
    }

    public double getDoubleData() {
        return doubleData;
    }

    public void setDoubleData(double doubleData) {
        this.doubleData = doubleData;
    }

    public Float getFloatData() {
        return floatData;
    }

    public void setFloatData(Float floatData) {
        this.floatData = floatData;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public CellData getCellData() {
        return cellData;
    }

    public void setCellData(CellData cellData) {
        this.cellData = cellData;
    }
}
