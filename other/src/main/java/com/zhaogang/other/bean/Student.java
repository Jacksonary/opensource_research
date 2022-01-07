package com.zhaogang.other.bean;

import lombok.Data;

/**
 * @author weiguo.liu
 * @date 2020/10/10
 * @description
 */
@Data
public class Student implements Cloneable {

    private Integer id;
    private String name;
    private Integer age;
    private Clazz clazz;

    @Override
    public Student clone() {
        try {
            Student clone = (Student)super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
