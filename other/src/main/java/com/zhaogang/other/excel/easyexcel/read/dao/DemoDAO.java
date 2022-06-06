package com.zhaogang.other.excel.easyexcel.read.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhaogang.other.excel.easyexcel.read.dto.DemoData;

/**
 * @author weiguo.liu
 * @date 2021/3/12
 * @description
 */
public class DemoDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(DemoDAO.class);

    public void save(List<DemoData> list) {
        LOGGER.info(">> DemoDAO save done -> list: {}", list);
    }
}
