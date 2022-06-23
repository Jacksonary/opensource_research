package com.hhu.other.excel.easyexcel.export.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author weiguo.liu
 * @date 2020/12/31
 * @description excelModel 导出对象
 */
public class DemoModelWithInt {
    private Integer id;
    private String name;
    private Integer age;
    private BigDecimal price;
    private Date createTime;
    private String extra;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
