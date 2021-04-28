package com.zhaogang.other.zk.curator.manager;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.data.Stat;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.zhaogang.other.zk.TemplateConfig;
import com.zhaogang.other.zk.curator.factory.CustomerWatcher;
import com.zhaogang.other.zk.curator.factory.ZKFactory;

/**
 * @author weiguo.liu
 * @date 2021/3/15
 * @description
 * 
 * @formatter:off 
 * 1. 创建约定的根节点
 * 2. 对已知的path节点作监听
 * 3. watcher 中对各个事件作监听，监听到内容后作2个事情：
 *  3.1 业务逻辑的处理；
 *  3.2 对对应的场景重新建立监听
 *  
 *  若出现类似于KeeperErrorCode = Unimplemented for /liuwg-test/template1/protostuff 异常，更换版本看看
 */
@Component
public class ZKManager {
    public void test() {
        CuratorFramework curatorFramework = ZKFactory.get();

        try {
            List<String> children = curatorFramework.getChildren().forPath("/liuwg-test/template");
            children.forEach(o -> {
                try {
                    byte[] bytes = curatorFramework.getData().forPath("/liuwg-test/template/" + o);
                    TemplateConfig templateConfig =
                        JSON.parseObject(new String(bytes, StandardCharsets.UTF_8), TemplateConfig.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void fillContainer() {
        // DB中获取数据
        List<TemplateConfig> list = new ArrayList<>(8);
        for (int i = 0; i < 8; i++) {
            list.add(new TemplateConfig(i, "template" + i));
        }

        CuratorFramework curatorFramework = ZKFactory.get();


        try {
            // 可以创建递归目录节点
            curatorFramework.createContainers("/base/node1/node2");

            // String base = curatorFramework.create().forPath("/liuwg-test/template1");
            for (int i = 0; i < list.size(); i++) {
                TemplateConfig config = list.get(i);
                String path = "/liuwg-test/template1/" + config.getType();
                Stat stat = curatorFramework.checkExists().forPath(path);
                if (stat != null) {
                    continue;
                }
                String result =
                    curatorFramework.create().forPath(path, JSON.toJSONString(config).getBytes(StandardCharsets.UTF_8));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据加观测
     * 
     * @return
     */
    public List<TemplateConfig> getData() {
        CuratorFramework curatorFramework = ZKFactory.get();

        List<TemplateConfig> data = new ArrayList<>();
        try {
            List<String> children = curatorFramework.getChildren().usingWatcher(new CustomerWatcher(curatorFramework))
                .forPath("/liuwg-test/template1");
            for (int i = 0; i < children.size(); i++) {
                byte[] bytes = curatorFramework.getData().usingWatcher(new CustomerWatcher(curatorFramework))
                    .forPath("/liuwg-test/template1/" + children.get(i));
                String jsonStr = new String(bytes, StandardCharsets.UTF_8);
                if (!jsonStr.startsWith("{")) {
                    continue;
                }
                data.add(JSON.parseObject(jsonStr, TemplateConfig.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    /**
     * 获取分布式锁
     * 
     * @param lockPath
     * @throws Exception
     */
    public void getDistributedLock(String lockPath) throws Exception {
        // InterProcessMutex lock = new InterProcessMutex(ZKFactory.get(), lockPath);
        // if (lock.acquire(30, TimeUnit.SECONDS)) {
        // try {
        // // do some work inside of the critical section here
        // } finally {
        // lock.release();
        // }
        // }
    }

}
