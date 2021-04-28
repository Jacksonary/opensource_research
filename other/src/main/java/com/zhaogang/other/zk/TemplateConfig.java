package com.zhaogang.other.zk;

/**
 * @author weiguo.liu
 * @date 2021/3/12
 * @description
 */
public class TemplateConfig {
    private Integer type;
    private String model;

    public TemplateConfig(Integer type, String model) {
        this.type = type;
        this.model = model;
    }

    @Override
    public String toString() {
        return "TemplateConfig{" + "type=" + type + ", model='" + model + '\'' + '}';
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
