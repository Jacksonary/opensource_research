package com.hhu.other.zk.curator.factory;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author weiguo.liu
 * @date 2021/3/15
 * @description
 */
public class ZKFactory {
    public static final int MAX_RETRY = 3;
    public static final int BASE_SLEEP_TIME = 5000;
    /**
     * 生产环境：zookeeper://10.90.29.115:2181;10.90.30.49:2181;10.90.30.68:2181
     *
     * 集群连接地址需要是逗号分割的
     * url.replaceAll("zookeeper://", "").replaceAll("\\?backup=", ",").replaceAll(";", ",")
     */
    public static final String url = "10.0.72.76:2181";

    public static CuratorFramework get() {
        ExponentialBackoffRetry exponentialBackoffRetry = new ExponentialBackoffRetry(BASE_SLEEP_TIME, MAX_RETRY);
        CuratorFramework curatorFramework =
            CuratorFrameworkFactory.builder().connectString(url).retryPolicy(exponentialBackoffRetry).build();
        curatorFramework.start();

        return curatorFramework;
    }

}
