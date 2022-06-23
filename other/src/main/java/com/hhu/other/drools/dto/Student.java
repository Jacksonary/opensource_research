package com.hhu.other.drools.dto;

/**
 * @author weiguo.liu
 * @date 2021/4/1
 * @description
 */
public class Student {
    private Integer id;
    private String no;
    private String name;

    public Student(Integer id, String no, String name) {
        this.id = id;
        this.no = no;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
