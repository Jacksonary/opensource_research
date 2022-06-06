package com.zhaogang.other.excel.easyexcel.read.dto;

import java.util.Date;

/**
 * @author weiguo.liu
 * @date 2021/3/12
 * @description
 */
public class DemoData {
    private String string;
    private Date date;
    private Double doubleData;

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

    public Double getDoubleData() {
        return doubleData;
    }

    public void setDoubleData(Double doubleData) {
        this.doubleData = doubleData;
    }

    @Override
    public String toString() {
        return "DemoData{" + "string='" + string + '\'' + ", date=" + date + ", doubleData=" + doubleData + '}';
    }
}
