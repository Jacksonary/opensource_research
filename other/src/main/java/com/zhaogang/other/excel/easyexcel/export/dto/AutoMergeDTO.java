package com.zhaogang.other.excel.easyexcel.export.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author weiguo.liu
 * @date 2021/1/25
 * @description 自动合并策略DTO
 */
public class AutoMergeDTO implements Serializable {
    private static final long serialVersionUID = 5579871123165032454L;

    /**
     * 合并的起始行，从第几行开始合并行为
     */
    private int startRowIndex;

    /**
     * 合并规则，每列在什么情况进行向上合并 key - 列号（从0开始），即需要合并的列有哪些 value - 列号（从0开始），在该列上行记录中除了该列相同以外，还需要哪些列值相同的情况下才能合并
     *
     * 如：1 - [2,3]， 2 - [1,3] 在第1、2、3列的值和上行记录相同时合并第1列的值 在第1、2、3列的值和上行记录相同时合并第2列的值
     */
    private Map<Integer, List<Integer>> mergeCon;

    public int getStartRowIndex() {
        return startRowIndex;
    }

    public void setStartRowIndex(int startRowIndex) {
        this.startRowIndex = startRowIndex;
    }

    public Map<Integer, List<Integer>> getMergeCon() {
        return mergeCon;
    }

    public void setMergeCon(Map<Integer, List<Integer>> mergeCon) {
        this.mergeCon = mergeCon;
    }
}
