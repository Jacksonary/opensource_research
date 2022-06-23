package com.hhu.other.zk.curator.factory;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.hhu.other.zk.TemplateConfig;

import lombok.SneakyThrows;

/**
 * @author weiguo.liu
 * @date 2021/3/12
 * @description
 */
public class CustomerWatcher implements Watcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerWatcher.class);

    private CuratorFramework curatorFramework;

    public CustomerWatcher(CuratorFramework curatorFramework) {
        this.curatorFramework = curatorFramework;
    }

    public CustomerWatcher() {}

    @SneakyThrows
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
                fsyncChangeTemplate(event.getPath());
                break;
            case NodeDeleted:
                LOGGER.info("自定义监控 -> " + event.getPath() + " : 节点被删除");
                fsyncDeleteTemplate(event.getPath());
                break;
            case NodeDataChanged:
                LOGGER.info("自定义监控 -> " + event.getPath() + " : 节点内容被修改");
                // 进行数据同步
                LOGGER.info(">> 数据同步中... content:{}", getNewlyTemplate(event.getPath()));
                fsyncChangeTemplate(event.getPath());
                break;
            case NodeChildrenChanged:
                List<String> newChildren = curatorFramework.getChildren()
                    .usingWatcher(new CustomerWatcher(curatorFramework)).forPath(event.getPath());
                LOGGER.info("自定义监控 -> path:{}, type:{}, sublist:{}", event.getPath(), "节点子节点列表发生改变", newChildren);
                fsyncListTemplate(event.getPath());
                break;
            default:

        }
    }

    private void fsyncListTemplate(String path) {
        CuratorFramework curatorFramework = ZKFactory.get();
        List<String> children;
        try {
            children = curatorFramework.getChildren().usingWatcher(new CustomerWatcher()).forPath(path);
        } catch (Exception e) {
            LOGGER.warn(">> zk error -> zk getChildren error, path: {}", path);
            e.printStackTrace();
            return;
        }
        if (CollectionUtils.isEmpty(children)) {
            return;
        }
        children.forEach(o -> {
            // 逻辑操作
//            if (ExcelImportTemplateHandler.excelTemplateModelMap.get(o) != null) {
//                return;
//            }
            fsyncChangeTemplate(path + "/" + o);
        });
    }

    private void fsyncChangeTemplate(String path) {
        TemplateConfig newlyTemplate = getNewlyTemplate(path);
        if (newlyTemplate == null) {
            LOGGER.warn(">> excelTemplateDO is null");
            return;
        }

//        Class<ExcelRow> importModelClass = ExcelImportTemplateHandler
//            .getImportModelClass(excelTemplateDO.getTemplateModel(), excelTemplateDO.getTemplateClassName());
//
//        ExcelImportTemplateHandler.excelTemplateModelMap.put(
//            ExcelImportTemplateHandler.generateKey(newlyTemplate.getDomainId(), excelTemplateDO.getTemplateType()),
//            importModelClass);

        LOGGER.info(">> excel import template has been updated, type: {}", newlyTemplate.getType());
    }

    private void fsyncDeleteTemplate(String path) {
        String key = path.substring(path.lastIndexOf("/") + 1);
        LOGGER.info(">> remove template: {}", key);
//        ExcelImportTemplateHandler.excelTemplateModelMap.remove(key);

        LOGGER.info(">> excel import template has been removed, path: {}", path);
    }

    private TemplateConfig getNewlyTemplate(String path) {
        try {
            // byte[] data = zooKeeper.getData(path, false, null);
            byte[] data = curatorFramework.getData().usingWatcher(new CustomerWatcher(curatorFramework)).forPath(path);
            return JSON.parseObject(new String(data), TemplateConfig.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
