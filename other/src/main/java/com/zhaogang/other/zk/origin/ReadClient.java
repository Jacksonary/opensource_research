package com.zhaogang.other.zk.origin;

import com.zhaogang.other.zk.CommonWatcher;
import com.zhaogang.other.zk.CustomerWatcher;
import com.zhaogang.other.zk.TemplateConfig;
import java.util.List;

import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * @author weiguo.liu
 * @date 2021/3/15
 * @description
 */
public class ReadClient {
    private static final String url = "10.0.72.76:2181";

    private static final Logger LOGGER = LoggerFactory.getLogger(ReadClient.class);

    public static void main(String[] args) throws Exception {
        ZooKeeper zooKeeper = new ZooKeeper(url, 5 * 60 * 1000, new CommonWatcher());
        CustomerWatcher customerWatcher = new CustomerWatcher(zooKeeper);

        LOGGER.info("zk sessionId: " + zooKeeper.getSessionId());

        List<String> children = zooKeeper.getChildren("/liuwg-test/template", customerWatcher);

        children.forEach(c -> {
            try {
                byte[] data = zooKeeper.getData("/liuwg-test/template/" + c, customerWatcher, null);
                TemplateConfig templateConfig = JSON.parseObject(new String(data), TemplateConfig.class);
                LOGGER.info(">> type: {}, content:{}", templateConfig.getType(), templateConfig);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        while (true) {

        }
    }
}
