package com.zhaogang.other.lock.distribute;

import com.zhaogang.other.zk.curator.factory.ZKFactory;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author weiguo.liu
 * @date 2021/4/28
 * @description
 */
public class ZKLock implements Lock {
    private static final String DEFAULT_LOCK_PATH = "/lock";
    private static final Logger LOGGER = LoggerFactory.getLogger(ZKLock.class);
    private String waitNode;
    private CountDownLatch countDownLatch;

    /**
     * 在实例化时同时初始化LOCK目录
     */
    public ZKLock() {
        CuratorFramework curatorFramework = ZKFactory.get();
        try {
            Stat stat = curatorFramework.checkExists().forPath(DEFAULT_LOCK_PATH);
            // 存在无须初始化
            if (stat != null) {
                return;
            }
            curatorFramework.createContainers(DEFAULT_LOCK_PATH);
        } catch (Exception e) {
            LOGGER.warn(">> zk error -> check status failed");
            e.printStackTrace();
        }
    }

    @Override public void lock() {
        if (tryLock()) {
            return;
        }

        // 上锁失败
//        waitForLock(waitNode, 1);
    }

    private boolean waitForLock(String waitNode, int timeout) throws Exception {
        CuratorFramework curatorFramework = ZKFactory.get();
        Stat stat = curatorFramework.checkExists().forPath(waitNode);
        if (stat == null) {
            return true;
        }

        // 监听节点
        countDownLatch = new CountDownLatch(1);
        countDownLatch.await();
        countDownLatch = null;
        return true;
    }

    @Override public void lockInterruptibly() throws InterruptedException {

    }

    @Override public boolean tryLock() {
        CuratorFramework curatorFramework = ZKFactory.get();
        String path = DEFAULT_LOCK_PATH + "/" + "productId";
        try {
            // withMode 指定为临时顺序节点，withACL 指定权限为开放
            String resultPath =
                curatorFramework.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).withACL(Ids.OPEN_ACL_UNSAFE)
                    .forPath(path, "test".getBytes(StandardCharsets.UTF_8));

            // 查看当前节点是否为序号最小节点
            List<String> children = curatorFramework.getChildren().forPath(path);
            Collections.sort(children);
            String expectedPath = DEFAULT_LOCK_PATH + "/" + children.get(0);
            if (expectedPath.equals(resultPath)) {
                return true;
            }

            // 否则监听比自己小1的节点
            String before = "";
            for (int i = 0; i < children.size(); i++) {
                String childrenPath = DEFAULT_LOCK_PATH + "/" + children.get(i);
                if (childrenPath.equals(resultPath)) {
                    before = children.get(i - 1);
                    break;
                }
            }

            waitNode = before;
            // 记录当前节点前一个 znode
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override public void unlock() {

    }

    @Override public Condition newCondition() {
        return null;
    }
}
