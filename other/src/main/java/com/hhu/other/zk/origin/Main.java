package com.hhu.other.zk.origin;

import com.hhu.other.zk.CommonWatcher;
import com.hhu.other.zk.TemplateConfig;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * @author weiguo.liu
 * @date 2021/3/12
 * @description
 */
public class Main {
    private static final String url = "10.0.72.76:2181";

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        // 创建连接,注意sessionTimeout（单位毫秒）不能设置太小。。不然一下子就关闭了，后面全都执行不下去。。
        // 第三个参数是Watcher类的对象，后文再说明
        ZooKeeper zooKeeper = new ZooKeeper(url, 5 * 60 * 1000, new CommonWatcher());

        // CustomerWatcher customerWatcher = new CustomerWatcher(zooKeeper);

        LOGGER.info("zk sessionId: " + zooKeeper.getSessionId());

        // 在根目录下创建连接
        /* 参数说明
        1.path 节点，要创建的节点类型
        2.data 节点数据，该节点存储的数据
        3.acl 权限控制，一般使用下面这个就可以了，没有权限控制
        4.CreateMode 节点类型，使用CreateMode下的枚举类就可以了
        返回值就是创建后的节点名称
         */

        // 持久znode是一种很有用的znode，可以通过持久znode保存一些应用数据，即使znode的创建者崩溃或者断开连接，数据也不会丢失,如/path 只能通过delete命令进行删除
        String result = createNode(zooKeeper, "/liuwg-test", null, CreateMode.PERSISTENT);

        createTemplateConfig(zooKeeper);

        // // 在root文件夹下面创建永久顺序子节点child，顺序自动编号持久化节点，这种节点会根据当前已存在的节点数自动加 1
        // result = createNode(zooKeeper, "/liuwg-test/child", "child".getBytes(), CreateMode.PERSISTENT_SEQUENTIAL);
        // LOGGER.info("添加child节点=" + result);

        // 创建临时节点,当创建临时节点的客户端崩溃或者关闭了与Zookeeper的连接或客户端session超时时，这个节点就会被删除
        // result = createNode(zooKeeper, "/tmp", "tmp".getBytes(), CreateMode.EPHEMERAL);
        // LOGGER.info("添加tmp节点=" + result);

        // // 创建临时顺序节点，临时自动编号节点
        // result = createNode(zooKeeper, "/tmp", "tmp".getBytes(), CreateMode.EPHEMERAL_SEQUENTIAL);
        // LOGGER.info("添加tmp顺序节点=" + result);

        // // 在调用exists、getData、getChildren方法，可以开启创建链接时定义的监视器。
        // zooKeeper.exists("/watcher", true);
        // // 也可以定义一个新的监视器
        // zooKeeper.exists("/watcher",
        // event -> LOGGER.info("exists自定义监视器" + event.getType().name() + "," + event.getPath()));

        new Thread(() -> {
            try {
                // // 删除Node所有版本
                // zooKeeper.delete("/tmp", -1);
                // LOGGER.info("tmp delete ...");
                // // 获取所有的子节点
                // List<String> children = zooKeeper.getChildren("/liuwg-test", false);
                // LOGGER.info("liuwg-test getChildren ...");
                // 获取节点数据
                // zooKeeper.getData("/liuwg-test/child", false, null);
                // LOGGER.info("liuwg-test/child getData ...");
                // 设置节点数据
                // zooKeeper.setData("/liuwg-test/child", "new child".getBytes(), -1);
                // LOGGER.info("liuwg-test/child setData ...");
                List<String> children = zooKeeper.getChildren("/liuwg-test/template", false);
                children.forEach(c -> {
                    byte[] strTc;
                    try {
                        strTc = zooKeeper.getData("/liuwg-test/template/" + c, false, null);
                        TemplateConfig templateConfig = JSON.parseObject(new String(strTc), TemplateConfig.class);
                        templateConfig.setModel(templateConfig.getModel() + " -> update time: " + LocalDateTime.now());

                        zooKeeper.setData("/liuwg-test/template/" + c, JSON.toJSONString(templateConfig).getBytes(),
                            -1);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        while (true) {

        }
        // 关闭连接
        // zooKeeper.close();
    }

    private static String createNode(ZooKeeper zooKeeper, String path, byte[] data, CreateMode createMode)
        throws KeeperException, InterruptedException {
        Stat exists = zooKeeper.exists(path, false);
        if (exists != null) {
            return "success";
        }

        // Node 节点不存在需要创建
        return zooKeeper.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, createMode);
    }

    private static void createTemplateConfig(ZooKeeper zooKeeper) throws Exception {
        String node = createNode(zooKeeper, "/liuwg-test/template", null, CreateMode.PERSISTENT);
        TemplateConfig tc = new TemplateConfig(1, "model1");
        String node1 = createNode(zooKeeper, "/liuwg-test/template/" + tc.getType(), JSON.toJSONString(tc).getBytes(),
            CreateMode.PERSISTENT);
    }

}
