package com.zhaogang.other.zk.origin;

import com.zhaogang.other.zk.CommonWatcher;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author weiguo.liu
 * @date 2021/3/15
 * @description
 */
public class UpdateClient {
    private static final String url = "10.0.72.76:2181";
    private static final String BASE_NODE = "/liuwg-test/template1";

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateClient.class);

    public static void main(String[] args) throws Exception {
        ZooKeeper zooKeeper = new ZooKeeper(url, 5 * 60 * 1000, new CommonWatcher());
        // CustomerWatcher customerWatcher = new CustomerWatcher(zooKeeper);

        LOGGER.info("zk sessionId: " + zooKeeper.getSessionId());

        List<String> children = zooKeeper.getChildren(BASE_NODE, false);

        // 创建新节点
        zooKeeper.create(BASE_NODE + "/" + "112", "appnd son2".getBytes(StandardCharsets.UTF_8), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        // 更改ZNODE的数据内容
//        children.forEach(c -> {
//            try {
//                byte[] data = zooKeeper.getData(BASE_NODE + "/" + c, false, null);
//                TemplateConfig templateConfig = JSON.parseObject(new String(data), TemplateConfig.class);
//                templateConfig.setModel("updateClient22 -> " + LocalDateTime.now());
//
//                zooKeeper.setData(BASE_NODE + "/" + c,
//                    JSON.toJSONString(templateConfig).getBytes(StandardCharsets.UTF_8), -1);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });

        System.out.println("===============done====================");
    }
}
