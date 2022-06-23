package com.hhu.other.zk;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * @author weiguo.liu
 * @date 2021/3/12
 * @description
 */
public class CustomerWatcher implements Watcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerWatcher.class);
    private ZooKeeper zooKeeper;

    public CustomerWatcher(ZooKeeper zooKeeper) {
        this.zooKeeper = zooKeeper;
    }

    public CustomerWatcher() {}

    @Override
    public void process(WatchedEvent event) {
        if (event == null || event.getType() == null) {
            LOGGER.warn(">> illegal event -> event or type is null, event: {}", JSON.toJSONString(event));
            return;
        }

        LOGGER.info("CustomerWatcher: type:{} -> path: {}", event.getType().name(), event.getPath());

        switch (event.getType()) {
            case None:
                LOGGER.info("自定义监控 -> 触发none event");
                break;
            case NodeCreated:
                LOGGER.info("自定义监控 -> " + event.getPath() + " : 节点被创建");
                break;
            case NodeDeleted:
                LOGGER.info("自定义监控 -> " + event.getPath() + " : 节点被删除");
                break;
            case NodeDataChanged:
                LOGGER.info("自定义监控 -> " + event.getPath() + " : 节点内容被修改");
                // 进行数据同步
                LOGGER.info(">> 数据同步中... content:{}", getNewlyTemplate(event.getPath()));
                break;
            case NodeChildrenChanged:
                LOGGER.info("自定义监控 -> " + event.getPath() + " : 节点子节点列表发生改变");
                break;
//            case DataWatchRemoved:
//                LOGGER.info("自定义监控 -> " + event.getPath() + " : 数据监控移除");
//                break;
//            case ChildWatchRemoved:
//                LOGGER.info("自定义监控 -> " + event.getPath() + " : 子节点监控移除");
//                break;
//            case PersistentWatchRemoved:
//                LOGGER.info("自定义监控 -> " + event.getPath() + " : 持久化节点移除");
//                break;
            default:

        }
    }

    private TemplateConfig getNewlyTemplate(String path) {
        try {
            byte[] data = zooKeeper.getData(path, false, null);
            return JSON.parseObject(new String(data), TemplateConfig.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
