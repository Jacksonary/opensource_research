package com.hhu.other.excel.easyexcel.read.dao;

import com.hhu.other.excel.easyexcel.read.dto.DemoData;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
