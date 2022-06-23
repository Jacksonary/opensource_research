package com.hhu.other.zk;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * @author weiguo.liu
 * @date 2021/3/15
 * @description
 */
public class CommonWatcher implements Watcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonWatcher.class);

    @Override
    public void process(WatchedEvent event) {
        if (event == null || event.getType() == null) {
            LOGGER.warn(">> illegal event -> event or type is null, event: {}", JSON.toJSONString(event));
            return;
        }

        LOGGER.info("CommonWatcher: type:{} -> path: {}", event.getType().name(), event.getPath());

        switch (event.getType()) {
            case None:
                LOGGER.info("全局监控 -> 触发none event");
                break;
            case NodeCreated:
                LOGGER.info("全局监控 -> " + event.getPath() + " : 节点被创建");
                break;
            case NodeDeleted:
                LOGGER.info("全局监控 -> " + event.getPath() + " : 节点被删除");
                break;
            case NodeDataChanged:
                LOGGER.info("全局监控 -> " + event.getPath() + " : 节点内容被修改");
                break;
            case NodeChildrenChanged:
                LOGGER.info("全局监控 -> " + event.getPath() + " : 节点子节点列表发生改变");
                break;
//            case DataWatchRemoved:
//                LOGGER.info("全局监控 -> " + event.getPath() + " : 数据监控移除");
//                break;
//            case ChildWatchRemoved:
//                LOGGER.info("全局监控 -> " + event.getPath() + " : 子节点监控移除");
//                break;
//            case PersistentWatchRemoved:
//                LOGGER.info("全局监控 -> " + event.getPath() + " : 持久化节点移除");
//                break;
            default:

        }
    }
}
